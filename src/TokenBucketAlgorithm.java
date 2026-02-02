import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Math.min;

// As we can gurentee thread safe access to a single map, the state and mutation must have in a single map only
// String userId, String API, Long lastAccessTime, Double TokenRemaining
public class TokenBucketAlgorithm extends DecisionAlgorithm{

    private ConcurrentHashMap<UserAPIPair, Bucket> tokenBucketMap;
    private AtomicLong timeSinceLastEviction = new AtomicLong(0);

    public TokenBucketAlgorithm(TokenBucketConfig t) {
        super(t);
    }

    // remove all user entries with last time < currTime - evictionTimeGap
    private void evictUsers(Long currentTimestamp, Integer evictionTimeGap) {
        tokenBucketMap.entrySet().removeIf(entry -> currentTimestamp - entry.getValue().lastAccessTime() >= evictionTimeGap);
    }

    public boolean allowRequest(UserRequest userReq) {

        UserAPIPair currentUserAPI = new UserAPIPair(userReq.userId(), userReq.API());
        TokenBucketConfig currentConfig = (TokenBucketConfig) super.getConfig(currentUserAPI);
        AtomicBoolean allow = new AtomicBoolean(false);

        // Make it a separate fn for readablity, and just add the call here
        long prevEvictionTime = timeSinceLastEviction.get();
        if (userReq.timeStamp() - prevEvictionTime >= currentConfig.getEvictionTimeInterval()) {
            if (timeSinceLastEviction.compareAndSet(prevEvictionTime, userReq.timeStamp())) {// CAS here to make sure the val is not updated anywhere else
                evictUsers(userReq.timeStamp(), currentConfig.getUserEvictionTimeGap());
            }
        }

        tokenBucketMap.compute(currentUserAPI, (key,oldValue) -> {
            double newTokenValue;
            // Called for first time
            if (oldValue == null){
                newTokenValue = currentConfig.getMaxBucketSize();
            }
            else if(oldValue.lastAccessTime() > userReq.timeStamp()) {
                return oldValue; // if we get a prev time requset, straight reject as later API call allowed
            }
            else {
                double tokenIncrement = ((double)(currentConfig.getRefillRatePerSecond() * (userReq.timeStamp() - oldValue.lastAccessTime())))/1000;
                newTokenValue = min(currentConfig.getMaxBucketSize(), tokenIncrement+oldValue.TokenRemaining());
            }
            if (newTokenValue >= 1) {
                allow.set(true);
                return new Bucket(userReq.userId(), userReq.API(), userReq.timeStamp(), (double) newTokenValue-1);
            }
            return new Bucket(userReq.userId(), userReq.API(), userReq.timeStamp(), (double) newTokenValue);
        });
        return allow.get();
    }
}

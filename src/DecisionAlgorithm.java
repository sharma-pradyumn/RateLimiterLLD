import java.util.concurrent.ConcurrentHashMap;

public abstract class DecisionAlgorithm {

    // holds global, API level, and UserAPI level config
    private AlgoConfig globalConfig;
    private ConcurrentHashMap<String, AlgoConfig> APIConfigMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UserAPIPair, AlgoConfig> UserAPIConfigMap = new ConcurrentHashMap<>();

    public DecisionAlgorithm(AlgoConfig conf) {
        globalConfig = conf;
    }

    public AlgoConfig getGlobalConfig() {
        return globalConfig;
    }
    // TBD -> Might wanna fix exposing these maps directly, can add setters
// No need to expose directly
//    public ConcurrentHashMap<String, AlgoConfig> getAPIConfigMap() {
//        return APIConfigMap;
//    }
//
//    public ConcurrentHashMap<UserAPIPair, AlgoConfig> getUserAPIConfigMap() {
//        return UserAPIConfigMap;
//    }

    public AlgoConfig getConfig(UserAPIPair userAPI) {
        AlgoConfig result;
        result = UserAPIConfigMap.get(userAPI);
        if (result != null) {
            return result;
        }
        result = APIConfigMap.get(userAPI.API());
        if (result != null) {
            return result;
        }
        return globalConfig;
    }

    public AlgoConfig getConfig(UserRequest userReq) {
        return getConfig(new UserAPIPair(userReq.userId(), userReq.API()));
    }

    public void setAPIConfig(String API, AlgoConfig algoConfig) {
        this.APIConfigMap.put(API, algoConfig);
    }

    public void setUserAPIConfig(UserAPIPair userAPIPair, AlgoConfig algoConfig) {
        this.UserAPIConfigMap.put(userAPIPair, algoConfig);
    }

    // performs eviction checks, and decides if the current req should be allowed
    // TBD
    public abstract boolean allowRequest(UserRequest userReq);
}

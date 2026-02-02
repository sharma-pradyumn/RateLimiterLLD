public class TokenBucketConfig implements AlgoConfig{
    private Integer maxBucketSize;
    private Integer refillRatePerSecond;
    private Integer evictionTimeInterval = 10000; // run every 10 sec
    private Integer userEvictionTimeGap = 600000; // remove user if 10 min since last used

    public Integer getMaxBucketSize() {
        return maxBucketSize;
    }

    public Integer getRefillRatePerSecond() {
        return refillRatePerSecond;
    }

    public void setMaxBucketSize(Integer maxBucketSize) {
        if (maxBucketSize < 1){
            throw new IllegalArgumentException("Max Bucket Size must atleast be 1");
        }
        this.maxBucketSize = maxBucketSize;
    }

    public void setRefillRatePerSecond(Integer refillRatePerSecond) {
        this.refillRatePerSecond = refillRatePerSecond;
    }

    public Integer getEvictionTimeInterval() {
        return evictionTimeInterval;
    }

    public Integer getUserEvictionTimeGap() {
        return userEvictionTimeGap;
    }

    public void setEvictionTimeInterval(Integer evictionTimeInterval) {
        this.evictionTimeInterval = evictionTimeInterval;
    }

    public void setUserEvictionTimeGap(Integer userEvictionTimeGap) {
        this.userEvictionTimeGap = userEvictionTimeGap;
    }

    public TokenBucketConfig(Integer _maxBucket, Integer _refill, Integer _eviction, Integer _userEviction) {
        maxBucketSize = _maxBucket;
        refillRatePerSecond = _refill;
        evictionTimeInterval = _eviction;
        userEvictionTimeGap = _userEviction;
    }

    public TokenBucketConfig(Integer maxBucketSize, Integer refillRatePerSecond) {
        this.maxBucketSize = maxBucketSize;
        this.refillRatePerSecond = refillRatePerSecond;
        // evictionTimeInterval and userEvictionTimeGap remain as 10000 and 600000
    }
}

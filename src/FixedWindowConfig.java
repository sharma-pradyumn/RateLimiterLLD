public class FixedWindowConfig {
    private Integer windowSize;
    private Integer maxRequests;
    private Integer evictionTimeInterval;
    private Integer userEvictionTimeGap;

    public Integer getEvictionTimeInterval() {
        return evictionTimeInterval;
    }

    public Integer getUserEvictionTimeGap() {
        return userEvictionTimeGap;
    }

    public Integer getMaxRequests() {
        return maxRequests;
    }

    public Integer getWindowSize() {
        return windowSize;
    }

    public void setEvictionTimeInterval(Integer evictionTimeInterval) {
        this.evictionTimeInterval = evictionTimeInterval;
    }

    public void setUserEvictionTimeGap(Integer userEvictionTimeGap) {
        this.userEvictionTimeGap = userEvictionTimeGap;
    }

    public void setMaxRequests(Integer _maxRequests) {
        maxRequests = _maxRequests;
    }

    public void setWindowSize(Integer _windowSize) {
        windowSize = _windowSize;
    }

    public FixedWindowConfig(Integer _windowSize, Integer _maxReq, Integer _evictionTime, Integer _userEviction) {
        windowSize = _windowSize;
        maxRequests = _maxReq;
        evictionTimeInterval = _evictionTime;
        userEvictionTimeGap = _userEviction;
    }

}

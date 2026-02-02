public class RateLimiter {
    DecisionAlgorithm decisionAlgorithm;

    public RateLimiter(DecisionAlgoType algoType, AlgoConfig algoConfig) {
        if (algoType == DecisionAlgoType.FIXED_WINDOW) {
            //         TBD
        }
        else if (algoType == DecisionAlgoType.TOKEN_BUCKET){
            if (!(algoConfig instanceof TokenBucketConfig)) {
                throw new IllegalArgumentException("algoConfig must be of type TokenBucketConfig for tokenBucket ");
            }
            decisionAlgorithm = new TokenBucketAlgorithm((TokenBucketConfig) algoConfig);
        }
    }

    public boolean allowRequest(UserRequest userReq) {
        return decisionAlgorithm.allowRequest(userReq);
    }
}

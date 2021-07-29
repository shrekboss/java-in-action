package org.crayzer.design.design_mode_thingking.inaction.authentication;

import java.util.Map;

public class AuthToken {
    /**
     * 第一个细节：并不是所有出现的名词都被定义为类的属性，比如 URL、AppID、密码、时间戳这几个名词，我们把它作为了方法的参数。
     * 第二个细节：我们还需要挖掘一些没有出现在功能点描述中属性，比如 createTime，expireTimeInterval，它们用在 isExpired() 函数中，
     *                用来判定 token 是否过期。
     * 第三个细节：我们还给 AuthToken 类添加了一个功能点描述中没有提到的方法 getToken()。
     */
    private static final long DEFAULT_EXPIRED_TIME_INTERVAL = 1 * 60 * 1000;
    private String token;
    private long createTime;
    private long expiredTimeInterval = DEFAULT_EXPIRED_TIME_INTERVAL;

    public AuthToken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    public AuthToken(String token, long createTime, long expiredTimeInterval) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTimeInterval = expiredTimeInterval;
    }
    public static AuthToken ceate(String baseUrl, long createTime, Map<String, String> params) {
        //todo
        return null;
    }

    public static AuthToken generate(String originalUrl, String appId, String password, long timestamp) {
        return null;
    }

    public String getToken() {
        return token;
    }

    public boolean match(AuthToken authToken) {
        return true;
    }

    public boolean isExpired() {
        return false;
    }
}

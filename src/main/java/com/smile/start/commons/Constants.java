package com.smile.start.commons;

/**
 * @author Joseph
 * @version v1.0 2019/1/24 20:08, Constants.java
 * @since 1.8
 */
public final class Constants {
    private Constants() {
    }

    public final static String TOKEN_COOKIE_KEY       = "token";

    public final static String LOGIN_USER_SESSION_KEY = "loginUserSessionKey";

    /** 微信token */
    public final static String WECHAT_TOKEN_URL       = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={1}&corpsecret={2}";

    /** 微信消息 */
    public final static String WECHAT_MESSAGE_URL     = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={1}";

}

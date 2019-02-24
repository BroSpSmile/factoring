package com.smile.start.commons;

import com.smile.start.model.login.LoginUser;

/**
 * 用户登录处理类
 * @author Joseph
 * @version v1.0 2019/2/24 18:32, LoginHandler.java
 * @since 1.8
 */
public class LoginHandler {
    private static ThreadLocal<LoginUser> CACHE = new ThreadLocal<>();

    /**
     * 将用户信息保存在本地线程中
     * @param loginUser
     */
    public static void setLoginUser(LoginUser loginUser) {
        CACHE.set(loginUser);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static LoginUser getLoginUser() {
        return CACHE.get();
    }
}

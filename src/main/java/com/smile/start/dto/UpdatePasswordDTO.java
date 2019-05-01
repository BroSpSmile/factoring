package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/5/1 19:48, UpdatePasswordDTO.java
 * @since 1.8
 */
public class UpdatePasswordDTO implements Serializable {
    private static final long serialVersionUID = -4025886227339681905L;

    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

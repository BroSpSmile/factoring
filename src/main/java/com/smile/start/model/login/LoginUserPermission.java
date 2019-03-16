package com.smile.start.model.login;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户菜单信息
 * @author Joseph
 * @version v1.0 2019/2/24 18:43, LoginUserMenu.java
 * @since 1.8
 */
public class LoginUserPermission implements Serializable {
    private static final long serialVersionUID = 6162160183933313538L;

    /**
     * 权限唯一业务流水号
     */
    private String serialNo;

    /**
     * 权限编号
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限类型：1、菜单；2、按钮
     */
    private Integer permissionType;

    /**
     * 菜单访问地址
     */
    private String url;

    /**
     * 下级节点
     */
    private List<LoginUserPermission> childrens;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<LoginUserPermission> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<LoginUserPermission> childrens) {
        this.childrens = childrens;
    }

    @Override
    public String toString() {
        return "LoginUserPermission{" +
                "serialNo='" + serialNo + '\'' +
                ", permissionCode='" + permissionCode + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", permissionType=" + permissionType +
                ", url='" + url + '\'' +
                ", childrens=" + childrens +
                '}';
    }
}

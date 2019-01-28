package com.smile.start.model.auth;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/28 21:22, PermissionSetting.java
 * @since 1.8
 */
public class PermissionSetting implements Serializable {
    private static final long serialVersionUID = 7116088556098218033L;

    private List<String> checkedPermissionList;
    private String roleSerialNo;

    public List<String> getCheckedPermissionList() {
        return checkedPermissionList;
    }

    public void setCheckedPermissionList(List<String> checkedPermissionList) {
        this.checkedPermissionList = checkedPermissionList;
    }

    public String getRoleSerialNo() {
        return roleSerialNo;
    }

    public void setRoleSerialNo(String roleSerialNo) {
        this.roleSerialNo = roleSerialNo;
    }

    @Override
    public String toString() {
        return "PermissionSetting{" +
                "checkedPermissionList=" + checkedPermissionList +
                ", roleSerialNo='" + roleSerialNo + '\'' +
                '}';
    }
}

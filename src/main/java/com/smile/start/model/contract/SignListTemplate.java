package com.smile.start.model.contract;

import java.io.Serializable;

/**
 * 合同签署清单实体
 * @author Joseph
 * @version v1.0 2019/2/13 20:29, SignListTemplate.java
 * @since 1.8
 */
public class SignListTemplate implements Serializable {
    private static final long serialVersionUID = -7554198696100662951L;
    private Long id;
    private String serialNo;
    private String signListName;
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSignListName() {
        return signListName;
    }

    public void setSignListName(String signListName) {
        this.signListName = signListName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}

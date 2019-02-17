package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/2/13 20:46, SignListTemplateDTO.java
 * @since 1.8
 */
public class SignListTemplateDTO implements Serializable {
    private static final long serialVersionUID = -8391828679638793780L;
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

    @Override
    public String toString() {
        return "SignListTemplateDTO{" +
                "id=" + id +
                ", serialNo='" + serialNo + '\'' +
                ", signListName='" + signListName + '\'' +
                ", sort=" + sort +
                '}';
    }
}

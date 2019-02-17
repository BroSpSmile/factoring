package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/2/13 20:39, ContractSignListSearchDTO.java
 * @since 1.8
 */
public class SignListTemplateSearchDTO implements Serializable {
    private static final long serialVersionUID = 2228953529611857250L;

    private String signListName;

    public String getSignListName() {
        return signListName;
    }

    public void setSignListName(String signListName) {
        this.signListName = signListName;
    }

    @Override
    public String toString() {
        return "SignListTemplateSearchDTO{" +
                "signListName='" + signListName + '\'' +
                '}';
    }
}
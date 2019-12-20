package com.smile.start.model.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/2/13 20:39, ContractSignListSearchDTO.java
 * @since 1.8
 */
public class SignListTemplateSearchDTO implements Serializable {
    private static final long serialVersionUID = 2228953529611857250L;

    private String signListName;

    /**
     * 项目模式：1、有追索权模式；2、无追索权模式
     */
    private Integer projectMode;

    /**
     * 类别：1、债权人；2、债务人；3、内部决策；4、签署文件；5、出款依据；6、其他
     */
    private Integer category;

    public String getSignListName() {
        return signListName;
    }

    public void setSignListName(String signListName) {
        this.signListName = signListName;
    }

    public Integer getProjectMode() {
        return projectMode;
    }

    public void setProjectMode(Integer projectMode) {
        this.projectMode = projectMode;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SignListTemplateSearchDTO{" +
                "signListName='" + signListName + '\'' +
                ", projectMode=" + projectMode +
                ", category=" + category +
                '}';
    }
}

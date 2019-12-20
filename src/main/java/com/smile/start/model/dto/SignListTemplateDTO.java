package com.smile.start.model.dto;

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

    /**
     * 项目模式：1、有追索权模式；2、无追索权模式
     */
    private Integer projectMode;

    /**
     * 是否必须：1、必须；2、非必须
     */
    private Integer isRequired;

    /**
     * 类别：1、债权人；2、债务人；3、内部决策；4、签署文件；5、出款依据；6、其他
     */
    private Integer category;

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

    public Integer getProjectMode() {
        return projectMode;
    }

    public void setProjectMode(Integer projectMode) {
        this.projectMode = projectMode;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SignListTemplateDTO{" +
                "id=" + id +
                ", serialNo='" + serialNo + '\'' +
                ", signListName='" + signListName + '\'' +
                ", sort=" + sort +
                ", projectMode=" + projectMode +
                ", isRequired=" + isRequired +
                ", category=" + category +
                '}';
    }
}

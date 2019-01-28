package com.smile.start.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/27 16:26, Tree.java
 * @since 1.8
 */
public class Tree implements Serializable {
    private static final long serialVersionUID = 1762735147325896218L;

    /**
     * 标题
     */
    private String title;

    /**
     * 节点ID
     */
    private String serialNo;

    /**
     * 是否展开直子节点
     */
    private boolean expand = true;

    /**
     * 禁掉响应
     */
    private boolean disabled = false;

    /**
     * 禁掉 checkbox
     */
    private boolean disableCheckbox = false;

    /**
     * 是否选中子节点
     */
    private boolean selected = false;

    /**
     * 是否勾选(如果勾选，子节点也会全部勾选)
     */
    private boolean checked = false;

    private List<Tree> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisableCheckbox() {
        return disableCheckbox;
    }

    public void setDisableCheckbox(boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "title='" + title + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", expand=" + expand +
                ", disabled=" + disabled +
                ", disableCheckbox=" + disableCheckbox +
                ", selected=" + selected +
                ", checked=" + checked +
                ", children=" + children +
                '}';
    }
}

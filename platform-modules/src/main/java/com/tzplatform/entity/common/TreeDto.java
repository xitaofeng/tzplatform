package com.tzplatform.entity.common;

import java.io.Serializable;
import java.util.List;

/**
 * 树结构对象
 *
 * @author lejie
 */
public class TreeDto implements Serializable {
    private static final long serialVersionUID = -674719214907611768L;

    private String label;
    private String id;
    private List<TreeDto> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<TreeDto> children) {
        this.children = children;
    }
}

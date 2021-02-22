package com.design.pension_system.sys.domain;

import java.util.ArrayList;
import java.util.List;

public class Region {
    private Boolean isLeaf;
    private String value;
    private String label;
    private List<Region> children=new ArrayList<>();

    public Boolean isIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Region> getChildren() {
        return children;
    }

    public void setChildren(List<Region> children) {
        this.children = children;
    }
}

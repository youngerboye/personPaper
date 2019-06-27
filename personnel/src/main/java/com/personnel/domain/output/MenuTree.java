package com.personnel.domain.output;

import java.util.List;

public class MenuTree {
    private  String name;

    private  Integer key;

    private  Integer value;

    private List<MenuTree> children;

    public MenuTree(MenuOutput menuOutput) {
        this.name = menuOutput.getName();
        this.key = menuOutput.getId();
        this.value = menuOutput.getId();
        this.children = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<MenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }
}

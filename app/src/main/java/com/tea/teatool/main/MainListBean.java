package com.tea.teatool.main;

import java.io.Serializable;

/**
 * Created by jiangtea on 2019/6/28.
 */

public class MainListBean implements Serializable {

    private String itemName;
    private int functionTypes;
    private Class actvity;

    public MainListBean(String itemName, Class actvity) {
        this.itemName = itemName;
        this.actvity = actvity;
    }

    public MainListBean(String itemName, int functionTypes) {
        this.itemName = itemName;
        this.functionTypes = functionTypes;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Class getActvity() {
        return actvity;
    }

    public void setActvity(Class actvity) {
        this.actvity = actvity;
    }

    public int getFunctionTypes() {
        return functionTypes;
    }

    public void setFunctionTypes(int functionTypes) {
        this.functionTypes = functionTypes;
    }
}

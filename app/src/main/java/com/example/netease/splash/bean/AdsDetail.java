package com.example.netease.splash.bean;

import java.io.Serializable;
import java.util.List;

public class AdsDetail implements Serializable {
    private List<Material> materialList;

    private List<Action> actionList;











    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

    @Override
    public String toString() {
        return "AdsDetail{" +
                "materialList=" + materialList +
                ", actionList=" + actionList +
                '}';
    }
}

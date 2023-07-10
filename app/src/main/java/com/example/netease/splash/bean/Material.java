package com.example.netease.splash.bean;

import java.io.Serializable;
import java.util.List;

public class Material implements Serializable {
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "Material{" +
                "urls=" + urls +
                '}';
    }
}

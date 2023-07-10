package com.example.netease.splash.bean;

import java.io.Serializable;

public class Action implements Serializable {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Action{" +
                "url='" + url + '\'' +
                '}';
    }
}

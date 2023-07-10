package com.example.netease.splash.bean;

import java.io.Serializable;
import java.util.List;

public class Ads implements Serializable {
    private int next_req;
    private int result;
    private int rolls;
    private List<AdsDetail> ads;

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getRolls() {
        return rolls;
    }

    public void setRolls(int rolls) {
        this.rolls = rolls;
    }

    public List<AdsDetail> getAds() {
        return ads;
    }

    public void setAds(List<AdsDetail> ads) {
        this.ads = ads;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "next_req=" + next_req +
                ", result=" + result +
                ", rolls=" + rolls +
                ", ads=" + ads +
                '}';
    }
}

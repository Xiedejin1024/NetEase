package com.example.netease.news.bean;

public class Ads {
    private String subtitle;
    private String skipType;
    private String skipID;
    private String tag;
    private String title;
    private String imgsrc;
    private String url;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getSkipID() {
        return skipID;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "Ads{" +
                "subtitle='" + subtitle + '\'' +
                ", skipType='" + skipType + '\'' +
                ", skipID='" + skipID + '\'' +
                ", tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

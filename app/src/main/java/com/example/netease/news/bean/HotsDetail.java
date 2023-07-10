package com.example.netease.news.bean;

import java.util.List;

public class HotsDetail {
    private List<Ads> ads;
    private String title;
    private String imgsrc;
    private int replyCount;
    private String source;
    private String url;
    private String postid;

    public List<Ads> getAds() {
        return ads;
    }

    public void setAds(List<Ads> ads) {
        this.ads = ads;
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

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    @Override
    public String toString() {
        return "HotsDetail{" +
                "ads=" + ads +
                ", title='" + title + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", replyCount=" + replyCount +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", postid='" + postid + '\'' +
                '}';
    }
}

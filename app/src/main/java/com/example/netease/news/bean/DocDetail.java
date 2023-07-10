package com.example.netease.news.bean;

import java.util.List;

public class DocDetail {
    private String body;
    private String title;
    private String ptime;
    private List<DetailImage> img;
    private String source;
    private int replyCount;
    private String shareLink;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<DetailImage> getImg() {
        return img;
    }

    public void setImg(List<DetailImage> img) {
        this.img = img;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    @Override
    public String toString() {
        return "DocDetail{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", ptime='" + ptime + '\'' +
                ", img=" + img +
                ", source='" + source + '\'' +
                ", replyCount=" + replyCount +
                ", shareLink='" + shareLink + '\'' +
                '}';
    }
}

package com.example.netease.news.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FreedBacks {
    List<FreedBack> mFreedbacks;

    boolean isTitle = false;
    String title;

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        this.isTitle = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FreedBacks() {
        this.mFreedbacks = new ArrayList<>();
    }

    public List<FreedBack> getmFreedback() {
        return mFreedbacks;
    }

    public void setmFreedback(List<FreedBack> mFreedback) {
        this.mFreedbacks = mFreedback;
    }

    public void addFreedback(FreedBack freedback) {
        mFreedbacks.add(freedback);
    }

    public void sort() {
        Collections.sort(mFreedbacks, new FreedbackSort());
    }

    @Override
    public String toString() {
        return "HotPosts{" +
                "mFreedback=" + mFreedbacks +
                '}';
    }

    class FreedbackSort implements Comparator<FreedBack> {

        @Override
        public int compare(FreedBack f1, FreedBack f2) {
            return (f1.getIndex() > f2.getIndex()) ? 1 : -1;
        }
    }
}

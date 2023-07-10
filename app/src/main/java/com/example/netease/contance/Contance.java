package com.example.netease.contance;


public class Contance {
    public static final String SPLASHURL = "http://g1.163.com/madr?app=7A16FBB6&platform=android&category=STARTUP&location=1&timestamp=1462779408364&uid=OaBKRDb%2B9FBz%2FXnwAuMBWF38KIbARZdnRLDJ6Kkt9ZMAI3VEJ0RIR9SBSPvaUWjrFtfw1N%2BgxquT0B2pjMN5zsxz13RwOIZQqXxgjCY8cfS8XlZuu2bJj%2FoHqOuDmccGyNEtV%2FX%2FnBofofdcXyudJDmBnAUeMBtnIzHPha2wl%2FQnUPI4%2FNuAdXkYqX028puyLDhnigFtrX1oiC2F7UUuWhDLo0%2BE0gUyeyslVNqLqJCLQ0VeayQa%2BgbsGetk8JHQ";
    public static final String NEWSHEADLINEURL = "http://c.m.163.com/nc/article/headline/T1348647909107/%S-%E.html?";
    public static final String DOCIDURL = "http://c.m.163.com/nc/article/%D/full.html";
    public static final String FREEDBACKURL = "http://comment.api.163.com/api/json/post/list/new/hot/news3_bbs/%D/0/10/10/2/2";

    public static final String CHILDFILE = "netease";

    public static final String SPLASHNAME = "config";
    public static final String JSON_STR = "jsonstr";
    public static final String TIME_OUT = "time_out";
    public static final String LAST_TIME = "last_time";
    public static final String INDEX = "index";//记录首页显示的顺序，实现不同的广告效果


    public static String getRefreshURL(String net_url, int start, int end) {
        String url = net_url.replace("%S", String.valueOf(start));
        url = url.replace("%E", String.valueOf(end));
        return url;
    }


    public static String getDetailURL(String docid) {
        String url = DOCIDURL.replace("%D", docid);

        return url;
    }

    public static String getFreedbackURL(String docid) {
        String url = FREEDBACKURL.replace("%D", docid);

        return url;
    }


}

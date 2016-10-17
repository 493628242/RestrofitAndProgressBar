package com.wangjiyuan.restrofitandprogressbar.config;

/**
 * Created by DELL on 2016/10/17.
 */

public class UrlConfig {
    //    http://sns.maimaicha.com/api? apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&page=0&rows=15
    public static final String BASE_URL = "http://sns.maimaicha.com/";
    public static final String URL_JPG = "http://joymepic.joyme.com/article/uploads/allimg/151210/1444152144-24.jpg";

    public static class Path {
        public static final String API = "api";
    }

    public static class Param {
        public static final String API_KEY = "apikey";
        public static final String FORMAT = "format";
        public static final String METHOD = "method";
        public static final String PAGE = "page";
        public static final String ROWS = "rows";
    }

    public static class DefaultValue {
        public static final String API_KEY = "b4f4ee31a8b9acc866ef2afb754c33e6";
        public static final String FORMAT = "json";
        public static final String METHOD = "news.getHeadlines";
        public static final String PAGE = "0";
        public static final String ROWS = "15";
    }
}

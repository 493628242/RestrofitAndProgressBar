package com.wangjiyuan.restrofitandprogressbar;

import com.wangjiyuan.restrofitandprogressbar.bean.InfoBean;
import com.wangjiyuan.restrofitandprogressbar.config.UrlConfig;

import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by DELL on 2016/10/17.
 */
//http://joymepic.joyme.com/article/uploads/allimg/151210/1444152144-24.jpg
public interface MyRetroftApi {
    @GET(UrlConfig.Path.API)
    Call<InfoBean> getBean(@QueryMap() Map<String, String> map);

    @Streaming
    @GET
    Call<ResponseBody> getJPG(@Url() String url);


}

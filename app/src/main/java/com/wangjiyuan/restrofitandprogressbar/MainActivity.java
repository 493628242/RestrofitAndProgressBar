package com.wangjiyuan.restrofitandprogressbar;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.wangjiyuan.restrofitandprogressbar.bean.InfoBean;
import com.wangjiyuan.restrofitandprogressbar.config.UrlConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Gson gson;
    private Retrofit retrofit;
    private MyRetroftApi myRetroftApi;
    private ProgressBar progressBar;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setProgress(msg.arg1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.start);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        gson = new Gson();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(UrlConfig.BASE_URL)
                .build();
        myRetroftApi = retrofit.create(MyRetroftApi.class);
        /*final Map<String, String> map = new ArrayMap<>();
        map.put(UrlConfig.Param.API_KEY, UrlConfig.DefaultValue.API_KEY);
        map.put(UrlConfig.Param.FORMAT, UrlConfig.DefaultValue.FORMAT);
        map.put(UrlConfig.Param.METHOD, UrlConfig.DefaultValue.METHOD);
        map.put(UrlConfig.Param.PAGE, UrlConfig.DefaultValue.PAGE);
        map.put(UrlConfig.Param.ROWS, UrlConfig.DefaultValue.ROWS);*/


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Call<ResponseBody> call = myRetroftApi.getJPG(UrlConfig.URL_JPG);


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/aaa.jpg");
                                try {
                                    file.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                ResponseBody body = response.body();
                                final InputStream inputStream = body.byteStream();
                                final long count = body.contentLength();
                                long nowcount = 0;

                                try {
                                    FileOutputStream outputStream = new FileOutputStream(file);
                                    byte[] data = new byte[1024];
                                    int len;
                                    while ((len = inputStream.read(data)) != -1) {
                                        outputStream.write(data, 0, len);
                                        nowcount += len;
                                        Message message = handler.obtainMessage();
                                        message.arg1 = (int) (nowcount * 100 / count);
                                        handler.sendMessage(message);
                                        outputStream.flush();
                                    }
                                    inputStream.close();
                                    outputStream.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                    Log.e("exception", "文件未找到");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.e("exception", "io异常");
                                }
                            }
                        }).start();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });
    }

    private void getBean(Map<String, String> map) {
        Call<InfoBean> bean = myRetroftApi.getBean(map);
        bean.enqueue(new Callback<InfoBean>() {
            @Override
            public void onResponse(Call<InfoBean> call, Response<InfoBean> response) {
                InfoBean infoBean = response.body();
                String s = infoBean.getData().get(0).getTitle();
                Log.e("title", s);
            }

            @Override
            public void onFailure(Call<InfoBean> call, Throwable t) {

            }
        });
    }
}

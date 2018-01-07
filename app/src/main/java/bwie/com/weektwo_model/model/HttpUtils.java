package bwie.com.weektwo_model.model;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by 崔 on 2017/12/19.
 */

public class HttpUtils {
    private static volatile HttpUtils instance;
    private Handler handler=new Handler();
    //单例模式
    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public void get(String url, Map<String,String> map, final CallBack callback, final Class c){
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        // 如果存在？
        if (sb.indexOf("?") != -1) {
            // 如果？不在最后一位
            if (sb.indexOf("?") != sb.length() - 1) {
                sb.append("&");
            }
        } else {
            sb.append("?");
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (sb.indexOf("&") != -1) {
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        Log.i(TAG, "get url: " + sb);
        //1:创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2:创建Request对象
        final Request request = new Request.Builder()
                .get()
                .url(sb.toString())
                .build();
        //3:创建Call对象
        Call call = okHttpClient.newCall(request);
        //4:请求网络
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailed(e);
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                //拿到数据解析
                final Object o = new Gson().fromJson(result, c);
                //当前是在子线程,回到主线程中
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //回调
                        callback.onSuccess(o);
                    }
                });
            }
        });
    }
    //post请求
    public void post(String url, Map<String,String> map, final CallBack callBack, final Class c){
        //1:创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2:提供post请求需要的body对象
        FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String,String> entry:map.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        FormBody body = builder.build();
        //3:创建Request对象
        final Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        //4:创建Call对象
        Call call = okHttpClient.newCall(request);
        //5:请求网络
        call.enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }
            //请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                //拿到数据解析
                final Object o = new Gson().fromJson(result, c);
                //当前是在子线程,回到主线程中
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //回调
                        callBack.onSuccess(o);
                    }
                });
            }
        });
    }
}

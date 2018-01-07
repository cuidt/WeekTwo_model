package bwie.com.weektwo_model.util;

import java.util.Map;

import bwie.com.weektwo_model.bean.CartBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 崔冬涛
 * 2018/1/6
 */

public class HttpMethods {

    private final ApiService service;

    private HttpMethods(){
        service = getService();
    }
    private static class HttpMethodsHolder{
        public static final HttpMethods INSTANCE=new HttpMethods();
    }
    public static HttpMethods getInstance(){
        return HttpMethodsHolder.INSTANCE;
    }

    private ApiService getService(){
        Retrofit retrofit = getBuilder().build();
        ApiService service = retrofit.create(ApiService.class);
        return service;
    }

    private Retrofit.Builder getBuilder(){
        OkHttpClient.Builder clentBuilder=new OkHttpClient.Builder();
        OkHttpClient okHttpClient = clentBuilder.build();
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.baseUrl(HttpStaticApi.BASEUTL);
        return builder;
    }


    public void postShopCart(Map<String,String> map, Consumer<CartBean> consumer, Consumer<Throwable> throwable ){
        Observable<CartBean> observable = service.postShopCart(map);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer,throwable);
    }

}

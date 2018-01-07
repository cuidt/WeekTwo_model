package bwie.com.weektwo_model.util;

import java.util.Map;

import bwie.com.weektwo_model.bean.CartBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 崔冬涛
 * 2018/1/6
 */

public interface ApiService {

    @POST(HttpStaticApi.GETCARTS)
    Observable<CartBean> postShopCart(@QueryMap Map<String, String> map);
   // String  AddShop = "http://120.27.23.105/";


}

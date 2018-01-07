package bwie.com.weektwo_model.model;

import java.util.HashMap;
import java.util.Map;

import bwie.com.weektwo_model.bean.CartBean;
import bwie.com.weektwo_model.util.HttpMethods;
import io.reactivex.functions.Consumer;

/**
 * 王斌
 * 2018/1/6
 */

public class ShopCartModel implements IShopCartModel {
    @Override
    public void posrShopCart(int uid, final ShopCartLisenter lisenter) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        map.put("android", "source");
        HttpMethods.getInstance().postShopCart(map, new Consumer<CartBean>() {
            @Override
            public void accept(CartBean bean) throws Exception {
                lisenter.onSuccess(bean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                lisenter.onDefault(throwable);
            }
        });
    }
}

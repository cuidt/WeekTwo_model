package bwie.com.weektwo_model.model;


import bwie.com.weektwo_model.bean.CartBean;

/**
 * 王斌
 * 2018/1/6
 */

public interface IShopCartModel {
    void posrShopCart(int uid, ShopCartLisenter lisenter);
     interface ShopCartLisenter{
        void onSuccess(CartBean bean);
        void onDefault(Throwable throwable);
    }
}

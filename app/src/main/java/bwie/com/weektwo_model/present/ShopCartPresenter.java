package bwie.com.weektwo_model.present;

import android.util.Log;

import java.lang.ref.WeakReference;

import bwie.com.weektwo_model.bean.CartBean;
import bwie.com.weektwo_model.model.IShopCartModel;
import bwie.com.weektwo_model.model.ShopCartModel;
import bwie.com.weektwo_model.view.IShopCartView;

/**
 * 王斌
 * 2018/1/6
 */

public class ShopCartPresenter {
    private WeakReference<IShopCartView> view;
    private IShopCartModel model;

    public ShopCartPresenter(IShopCartView view) {
        model = new ShopCartModel();
        this.view = new WeakReference<IShopCartView>(view);
    }

    public void shopCart() {
        int uid = view.get().getUid();
        model.posrShopCart(uid, new IShopCartModel.ShopCartLisenter() {
            @Override
            public void onSuccess(CartBean bean) {
                Log.i("CartBean", bean.getMsg());
                if ("0".equals(bean.getCode())){
                    view.get().showShopCart(bean);
                };
            }

            @Override
            public void onDefault(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public void onDestroy() {
        if (view != null) {
            view.clear();
        }
    }
}

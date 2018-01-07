package bwie.com.weektwo_model.present;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.weektwo_model.bean.goodBean;
import bwie.com.weektwo_model.bean.goodsBean;
import bwie.com.weektwo_model.bean.listBean;
import bwie.com.weektwo_model.model.CallBack;
import bwie.com.weektwo_model.model.HttpUtils;
import bwie.com.weektwo_model.view.iView;


/**
 * Created by 崔冬涛 on 2018/01/06.
 */

public class Presenter {
    private Context context;
    private iView iv;
    private List<goodBean> list = new ArrayList<>();
    private List<List<goodsBean>> lists = new ArrayList<>();
    public Presenter(Context context, iView iv){
        this.context=context;
        this.iv=iv;
    }

    public void get(){
        Map<String,String> map = new HashMap<>();
        map.put("uid","100");
        map.put("source","android");
        HttpUtils.getInstance().get("http://120.27.23.105/product/getCarts", map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                listBean bean = (listBean) o;
                List<listBean.DataBean> data = bean.getData();
                for (int i = 0; i < data.size(); i++) {
                    list.add(new goodBean(data.get(i).getSellerName(),false));
                    List<listBean.DataBean.ListBean> beanList = data.get(i).getList();
                    List<goodsBean> ls=new ArrayList<>();
                    for (int j = 0; j < beanList.size(); j++) {
                        String images = beanList.get(j).getImages();
                        String[] split = images.split("!");
                        ls.add(new goodsBean(beanList.get(j).getPrice(),beanList.get(j).getTitle(),split[0],1,false,false));
                    }
                    lists.add(ls);
                }
                iv.success(list,lists);
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(context,e.getMessage()+"",Toast.LENGTH_SHORT).show();
            }
        }, listBean.class);
    }
}

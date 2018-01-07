package bwie.com.weektwo_model.view;

import java.util.List;

import bwie.com.weektwo_model.bean.goodBean;
import bwie.com.weektwo_model.bean.goodsBean;


/**
 * Created by 崔 on 2017/12/19.
 */

public interface iView {
    void success(List<goodBean> list, List<List<goodsBean>> lists);
    void failed(Exception e);
}

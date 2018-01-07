package bwie.com.weektwo_model.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import bwie.com.weektwo_model.MainActivity;
import bwie.com.weektwo_model.R;
import bwie.com.weektwo_model.bean.goodBean;
import bwie.com.weektwo_model.bean.goodsBean;
import bwie.com.weektwo_model.util.AddDeleteView;


/**
 * Created by 崔 on 2017/12/19.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<goodBean> list;
    private List<List<goodsBean>> lists;

    public ExpandableAdapter(Context context, List<goodBean> list, List<List<goodsBean>> lists) {
        this.context = context;
        this.list = list;
        this.lists = lists;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return lists.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i).getSellerName();
    }

    @Override
    public Object getChild(int i, int i1) {
        return lists.get(i).get(i1).getTitle();
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        //获取子布局
        view=View.inflate(context, R.layout.ex_group_item,null);
        //获取id值
        final CheckBox groupCb= view.findViewById(R.id.group_checkbox);
        TextView shopName= view.findViewById(R.id.shop_name);
        //赋值
        shopName.setText(list.get(i).getSellerName());
        groupCb.setChecked(list.get(i).isGroupCb());
        //复选按钮
        groupCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选中状态
                boolean checked = groupCb.isChecked();
                list.get(i).setGroupCb(checked);
                //获取上下文
                MainActivity main = (MainActivity)context;
                //增强for
                for (goodBean i:list) {
                    //一级复选框
                    boolean iGroupCb = i.isGroupCb();
                    //判断一级复选框没有选中状态
                    if(!iGroupCb){
                        //全选也没有选中状态
                        main.allCheckbox.setChecked(false);
                        break;
                    }else{//全选选中状态
                        main.allCheckbox.setChecked(true);
                    }
                }
                //拿到子级长度
                int size = lists.get(i).size();
                //如果父级全部选中
                if(checked){
                    for (int j = 0; j <size; j++) {
                        lists.get(i).get(j).setChildCb(true);
                    }
                }else{
                    for (int j = 0; j <size; j++) {
                        lists.get(i).get(j).setChildCb(false);
                    }
                }
                notifyDataSetChanged();
                main.changesum(lists);
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View v, ViewGroup viewGroup) {
        //加载视图
        v=View.inflate(context, R.layout.ex_child_item ,null);
        //获取id值
        final CheckBox childCb = v.findViewById(R.id.child_checkbox);
        TextView shopTitle= v.findViewById(R.id.shop_title);
        TextView shopPrice= v.findViewById(R.id.shop_price);
        /*ImageView shopImg=v.findViewById(R.id.shop_img);*/
        SimpleDraweeView shopImg = v.findViewById(R.id.shop_img);
        final AddDeleteView adv = v.findViewById(R.id.adv);
        Button shop_delete=v.findViewById(R.id.shop_delete);
        //添加值
        childCb.setChecked(lists.get(i).get(i1).isChildCb());
        /*Glide.with(context).load(lists.get(i).get(i1).getImages()).into(shopImg);*/
        Uri uri = Uri.parse(lists.get(i).get(i1).getImages());
        shopImg.setImageURI(uri);
        shopTitle.setText(lists.get(i).get(i1).getTitle());
        shopPrice.setText(lists.get(i).get(i1).getPrice()+"");
        adv.setNumber(lists.get(i).get(i1).getNum());
        final MainActivity main= (MainActivity) context;
        //控制删除按钮的显隐
        if(lists.get(i).get(i1).isBtn()){
            shop_delete.setVisibility(View.VISIBLE);
        }else{
            shop_delete.setVisibility(View.INVISIBLE);
        }
        //删除按钮监听
        shop_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = lists.get(i).size();
                if (size == 1) {
                    lists.remove(i);
                    list.remove(i);
                } else {
                    lists.get(i).remove(i1);
                }
                //点击删除后隐藏所有删除按钮
                for (List<goodsBean> i1 : lists) {
                    for (int r = 0; r < i1.size(); r++) {
                        i1.get(r).setBtn(false);
                    }
                }
                notifyDataSetChanged();
                main.changesum(lists);
            }
        });
        //加减器逻辑
        adv.setOnAddDelClickListener(new AddDeleteView.OnAddDelClickListener() {
            @Override
            public void onAddClick(View v) {
                int number = adv.getNumber();
                number++;
                adv.setNumber(number);
                lists.get(i).get(i1).setNum(number);
                main.changesum(lists);
            }
            @Override
            public void onDelClick(View v) {
                int number = adv.getNumber();
                number--;
                adv.setNumber(number);
                lists.get(i).get(i1).setNum(number);
                main.changesum(lists);
            }
        });
        //二级组的复选框监听
        childCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag=false;
                boolean cchecked = childCb.isChecked();
                lists.get(i).get(i1).setChildCb(cchecked);
                MainActivity main= (MainActivity) context;
                //二级复选框
                for (List<goodsBean> i1:lists){
                    for(int r=0;r<i1.size();r++) {
                        boolean childCb1 = i1.get(r).isChildCb();
                        //判断二级复选框没有选中状态
                        if(!childCb1){
                            main.allCheckbox.setChecked(false);  //全选没有选中状态
                            list.get(i).setGroupCb(false);//一级复选框也没有选中
                            flag=true;
                            break;
                        }else{
                            main.allCheckbox.setChecked(true);//全选选中状态
                            list.get(i).setGroupCb(true);//一级复选框选中
                        }
                    }
                    if(flag){
                        break;
                    }
                }
                //拿到子级的长度
                int size = lists.get(i).size();
                //遍历子级
                for(int x=0;x<size;x++) {
                    //拿到子级复选框的状态
                    boolean childCb1 = lists.get(i).get(x).isChildCb();
                    if(!childCb1){//没有选中
                        list.get(i).setGroupCb(false);//父级就没有选中
                        break;
                    }else{//否则
                        list.get(i).setGroupCb(true);//父级就选中
                    }
                }
                notifyDataSetChanged();
                main.changesum(lists);
            }
        });
        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

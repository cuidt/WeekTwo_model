package bwie.com.weektwo_model.bean;

/**
 * Created by 崔 on 2017/12/19.
 */

public class goodBean {
    private String sellerName;
    private boolean groupCb;
    public goodBean(String sellerName, boolean groupCb) {
        this.sellerName = sellerName;
        this.groupCb = groupCb;
    }
    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public boolean isGroupCb() {
        return groupCb;
    }

    public void setGroupCb(boolean groupCb) {
        this.groupCb = groupCb;
    }

    @Override
    public String toString() {
        return "goodBean{" +
                "sellerName='" + sellerName + '\'' +
                ", groupCb=" + groupCb +
                '}';
    }
}

package bwie.com.weektwo_model.bean;

/**
 * Created by 崔 on 2017/12/19.
 */

public class goodsBean {
    private double price;
    private String title;
    private String images;
    private int num;
    private boolean childCb;
    private boolean btn;

    public goodsBean(double price, String title, String images, int num, boolean childCb, boolean btn) {
        this.price = price;
        this.title = title;
        this.images = images;
        this.num = num;
        this.childCb = childCb;
        this.btn = btn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isChildCb() {
        return childCb;
    }

    public void setChildCb(boolean childCb) {
        this.childCb = childCb;
    }

    public boolean isBtn() {
        return btn;
    }

    public void setBtn(boolean btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "goodsBean{" +
                "price=" + price +
                ", title='" + title + '\'' +
                ", images='" + images + '\'' +
                ", num=" + num +
                ", childCb=" + childCb +
                ", btn=" + btn +
                '}';
    }
}

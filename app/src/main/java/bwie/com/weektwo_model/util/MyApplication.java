package bwie.com.weektwo_model.util;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 作者： 崔冬涛
 * 时间： 2018/1/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}

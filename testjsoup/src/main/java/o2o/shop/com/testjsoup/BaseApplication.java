package o2o.shop.com.testjsoup;

import android.app.Application;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class BaseApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        CrashHandler.init(getApplicationContext()); // 在Application里面设置我们的异常处理器为UncaughtExceptionHandler处理器
    }
}

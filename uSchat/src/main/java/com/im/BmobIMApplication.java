//package com.im;
//
//import android.app.Application;
//import android.content.Context;
//
//import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
//import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//
//import cn.bmob.newim.BmobIM;
//
///**
// * @author :smile
// * @project:BmobIMApplication
// * @date :2016-01-13-10:19
// */
//public class BmobIMApplication extends Application {
//
//    private static BmobIMApplication INSTANCE;
//
//    public static BmobIMApplication INSTANCE() {
//        return INSTANCE;
//    }
//
//    private void setInstance(BmobIMApplication app) {
//        setBmobIMApplication(app);
//    }
//
//    private static void setBmobIMApplication(BmobIMApplication a) {
//        BmobIMApplication.INSTANCE = a;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        setInstance(this);
//        //初始化
//        //im初始化
//        BmobIM.init(this);
//        //uil初始化
//        initImageLoader(this);
//        //注册消息接收器
//        BmobIM.registerDefaultMessageHandler(new MessageHandler(this));
//    }
//
//    public static void initImageLoader(Context context) {
//        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
//        config.threadPoolSize(3);
//        config.memoryCache(new WeakMemoryCache());
//        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();
//        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
//        config.tasksProcessingOrder(QueueProcessingType.LIFO);
////        config.writeDebugLogs(); // Remove for release app
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config.build());
//    }
//
//}

package com.xiangyue.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.im.MessageHandler;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiangyue.act.R;
import com.xiangyue.core.BitmapLoader;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.service.LocationService;
import com.yixia.camera.demo.service.AssertService;
import com.yixia.weibo.sdk.VCamera;
import com.yixia.weibo.sdk.util.DeviceUtils;

import org.lasque.tusdk.core.TuSdkApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import c.b.BP;
import cn.bmob.newim.BmobIM;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobInstallation;

public class BaseApplication extends TuSdkApplication {
    private static BaseApplication instance;
    private String photoorheadIcon = "1";
    private static long unreadallmessage = 0;
    public LocationService locationService;
    public Vibrator mVibrator;
    private static String apkurl;
    public static String[] TABLE_NAMES = null;
    public static String[] TABLE_GAMES = null;
    public static final String META_DATA_APP_KEY = "app_key";

    public static String getApkurl() {
        return apkurl;
    }

    public static void setApkurl(String apkurl) {
        BaseApplication.apkurl = apkurl;
    }

    public String getPhotoorheadIcon() {
        return photoorheadIcon;
    }

    public void setPhotoorheadIcon(String photoorheadIcon) {
        this.photoorheadIcon = photoorheadIcon;
    }

    private BitmapLoader mBitmapLoader;

    public static BaseApplication getInstance() {
        return instance;
    }

    public static void setInstance(BaseApplication instance) {
        BaseApplication.instance = instance;
    }

    public BitmapLoader getBitmapLoader() {
        return mBitmapLoader;
    }

    // private FinalHttp mFinalHttp;
    public static Context applicationContext;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";
    // public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();
    private Map<String, Object> mSyncDatas = new HashMap<String, Object>();
    public static Handler mainHandler;
    /**
     * 当前正在排队下载的app 集合
     * <p/>
     * 0 表示等待 1 表示 下载中 2 表示暂停
     */
    public static Map<String, Integer> downAppMap = new HashMap<String, Integer>();

    /** 存储当前可见下载appb ViewHolder */
    // public static Map<String, ViewHolder> upDataMap = new HashMap<String,
    // ViewHolder>();
    /**
     * 存储下载器对象
     */
    @SuppressWarnings("rawtypes")
    // public static Map<String, HttpHandler> downLoadHander = new
    // HashMap<String, HttpHandler>();;
    /**
     * 项目根目录名称
     */
    public static final String PATH = "A_ShowPlay";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        isDownload = false;
        this.mainHandler = new Handler();

        // 初始化事件分发器
        // EventDispatcher.init(getBaseContext(), this);
        mBitmapLoader = BitmapLoader.create(this);
        BusProvider.getInstance().register(this);
        // mFinalHttp = new FinalHttp(getRequestHeaders());
        applicationContext = this;

        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
//        Bmob.initialize(this, "c634df131878317d98c44b7c912198e0");//采用配置方式初始化sdk
        // 初始化SDK (请前往 http://tusdk.com 获取您的APP 开发秘钥)
        this.initPreLoader(this.getApplicationContext(), "2543a0e962ece10e-00-7fymn1");
        BmobInstallation.getCurrentInstallation(instance).save();
        BmobPush.startWork(this);
        BP.init("c634df131878317d98c44b7c912198e0");//初始化支付接口
        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new MessageHandler(this));
        }
//        initImageLoader(this);
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
        BmobConfig config = new BmobConfig.Builder(this).setApplicationId("c634df131878317d98c44b7c912198e0").setConnectTimeout(20).setUploadBlockSize(1024 * 1024).setFileExpiration(5500).build();
        Bmob.initialize(config);
        wxApi = WXAPIFactory.createWXAPI(this, "wxf40057862bc537e5");
        wxApi.registerApp("wxf40057862bc537e5");
//        Tencent.createInstance("tencent222222", this.getApplicationContext());
        //TABLE_NAMES
        TABLE_NAMES = new String[]{getString(R.string.app_name), getString(R.string.hotchat), getString(R.string.circle), getString(R.string.me)};
//        RealmProvider.getInstance().init(this);
//        initCanvers();
    }

    private void initCanvers() {
        // 设置拍摄视频缓存路径
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/Camera/VCameraDemo/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/", "/sdcard-ext/") + "/Camera/VCameraDemo/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/Camera/VCameraDemo/");
        }
        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄SDK，必须
        VCamera.initialize(this);
        //解压assert里面的文件
        startService(new Intent(this, AssertService.class));
    }

    private IWXAPI wxApi;

    public IWXAPI getWxApi() {
        return wxApi;
    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean IsSDCardExist() {
        String status = Environment.getExternalStorageState();
        boolean isSDCardExist = status.equals(Environment.MEDIA_MOUNTED);
        // 如果存在则获取SDCard目录
        if (isSDCardExist) {
            return true;
        }
        return false;
    }

    public static long getUnreadallmessage() {
        return unreadallmessage;
    }

    public static void setUnreadallmessage(long unreadallmessage) {
        BaseApplication.unreadallmessage = unreadallmessage;
    }

    // 判断网络是否可用
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    private boolean isDownload;


    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }
}

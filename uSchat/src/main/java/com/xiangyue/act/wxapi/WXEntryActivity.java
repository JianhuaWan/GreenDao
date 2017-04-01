package com.xiangyue.act.wxapi;

///** 微信客户端回调activity示例 */
//public class WXEntryActivity extends WechatHandlerActivity {
//
//	/**
//	 * 处理微信发出的向第三方应用请求app message
//	 * <p>
//	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
//	 * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
//	 * 做点其他的事情，包括根本不打开任何页面
//	 */
//	public void onGetMessageFromWXReq(WXMediaMessage msg) {
//		Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
//		startActivity(iLaunchMyself);
//	}
//
//	/**
//	 * 处理微信向第三方应用发起的消息
//	 * <p>
//	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
//	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
//	 * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
//	 * 回调。
//	 * <p>
//	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
//	 */
//	public void onShowMessageFromWXReq(WXMediaMessage msg) {
//		if (msg != null && msg.mediaObject != null
//				&& (msg.mediaObject instanceof WXAppExtendObject)) {
//			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
//			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
//		}
//	}
//	
//	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		Date dt = new Date();
//		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		String nowTime = df.format(dt);
//		StatService.onResume(this, this.getClass().getName() + "|start|" + nowTime, true);
//	}
//	
//	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		Date dt = new Date();
//		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		String nowTime = df.format(dt);
//		// 页面统计
//		StatService.onPause(this, this.getClass().getName() + "|end|" + nowTime, true);
//	}
//
//}


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口  
    public IWXAPI api;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, "wxf40057862bc537e5", false);
        api.handleIntent(getIntent(), this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    /**
     * 微信分享的返回方法
     */
    @Override
    public void onResp(BaseResp resp) {
//        LogManager.show(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:"  
//                + resp.errStr, 1);  
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消
                Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //分享拒绝
                Toast.makeText(getApplicationContext(), "拒绝", Toast.LENGTH_SHORT).show();
                break;
            default:
                //分享取消
                Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
                break;
        }
        this.finish();
    }

}



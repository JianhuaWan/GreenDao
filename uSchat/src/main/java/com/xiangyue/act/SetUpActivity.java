package com.xiangyue.act;

/**
 * 设置界面
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.im.model.UserModel;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.updateversion;
import com.xiangyue.util.SysApplication;
import com.xiangyue.view.ActionSheetDialog;
import com.xiangyue.view.ActionSheetDialog.OnSheetItemClickListener;
import com.xiangyue.view.ActionSheetDialog.SheetItemColor;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class SetUpActivity extends BaseActivity implements OnClickListener {
    private static String url;
    private Button btn_unregist;
    private MicroRecruitSettings settings;
    private LinearLayout linearmakeshift, linear_clear, li_changepwd, linear_share, linear_suggestion,
            linear_checkversion, li_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.hsitup_main);
        setUp();
        initView();
    }

    public void setUp() {
        settings = new MicroRecruitSettings(SetUpActivity.this);
    }

    public void back(View view) {
        finish();
    }

    public void initView() {
        btn_unregist = (Button) findViewById(R.id.btn_unregist);
        btn_unregist.setOnClickListener(this);
        linearmakeshift = (LinearLayout) findViewById(R.id.linearmakeshift);
        linearmakeshift.setOnClickListener(this);
        linear_clear = (LinearLayout) findViewById(R.id.linear_clear);
        linear_clear.setOnClickListener(this);
        li_changepwd = (LinearLayout) findViewById(R.id.li_changepwd);
        li_changepwd.setOnClickListener(this);
        linear_share = (LinearLayout) findViewById(R.id.linear_share);
        linear_share.setOnClickListener(this);
        linear_suggestion = (LinearLayout) findViewById(R.id.linear_suggestion);
        linear_suggestion.setOnClickListener(this);
        linear_checkversion = (LinearLayout) findViewById(R.id.linear_checkversion);
        linear_checkversion.setOnClickListener(this);
        li_about = (LinearLayout) findViewById(R.id.li_about);
        li_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_unregist:
                new ActionSheetDialog(SetUpActivity.this).builder().setCancelable(false).setTitle(("您确定要退出登录吗?"))
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("确定", SheetItemColor.Blue, new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                settings.isFirstLogin.setValue(true);
                                UserModel.getInstance().logout();
                                //可断开连接
                                //清空缓存objectId
                                settings.phone.setValue("");
                                settings.HEADICON.setValue("");
                                settings.OBJECT_ID.setValue("");
                                settings.OBJECT_IDBYCOIN.setValue("");
                                settings.OBJECT_IDBYLAUD.setValue("");
                                settings.OBJECT_IDBYCIRCLEBG.setValue("");
                                settings.PWD.setValue("");
                                settings.UPDATE_TIPS.setValue("");
                                settings.isFirstLogin.setValue(true);
                                BmobIM.getInstance().disConnect();
//                                MainActivity.getInstance().finish();
                                Intent intent = new Intent(SetUpActivity.this, StartActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                finish();
                            }
                        }).show();
                break;
            case R.id.linearmakeshift:
                //dialog
//                Intent intent1 = new Intent();
//                intent1.setClass(SetUpActivity.this, SetMoneyActivity.class);
//                startActivity(intent1);
//                showPopwindow();

                //进入选择界面
                Intent intent1 = new Intent();
                intent1.setClass(SetUpActivity.this, ChioseMoneyActivity.class);
                startActivity(intent1);
                break;
            case R.id.linear_clear:
                // 清理缓存
                break;
            case R.id.li_changepwd:
                // 修改密码
                intent.setClass(SetUpActivity.this, CGPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_share:
                // 分享好友包含短信分享
                showPopwindowbyshare();
                break;
            case R.id.linear_suggestion:
                // 意见反馈
                intent.setClass(SetUpActivity.this, SuggestionActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_checkversion:
                // 版本检查
//                UmengUpdateAgent.update(SetUpActivity.this);
//                UmengUpdateAgent.setUpdateOnlyWifi(false);
                checkUpdate();
                break;
            case R.id.li_about:
                // 关于
                intent.setClass(SetUpActivity.this, AboutActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private PopupWindow window;

    private void showPopwindowbyshare() {
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_share_activity1, null);
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //        设置弹出窗体需要软键盘，
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        再设置模式，和Activity的一样，覆盖，调整大小。
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout ll_viewLayout = (LinearLayout) view.findViewById(R.id.ll_botton);
        View view2 = (View) view.findViewById(R.id.nothing_view);
        view2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                window.dismiss();

            }
        });

        TextView tv_wechat = (TextView) ll_viewLayout.findViewById(R.id.tv_wechat);
        tv_wechat.setOnClickListener(new View.OnClickListener() {

                                         @Override
                                         public void onClick(View v) {
                                             window.dismiss();
                                             wechatShare(SetUpActivity.this, "0", "http://jianyuan.bmob.cn/", "捡缘", getString(R.string.tips), null);
                                         }
                                     }

        );
        TextView tv_wechatfriend = (TextView) ll_viewLayout.findViewById(R.id.tv_wechatfriend);
        tv_wechatfriend.setOnClickListener(new View.OnClickListener()

                                           {

                                               @Override
                                               public void onClick(View v) {
                                                   window.dismiss();
//                                                   Toast.makeText(SetUpActivity.this, "朋友圈", Toast.LENGTH_LONG).show();
                                                   wechatShare(SetUpActivity.this, "1", "http://jianyuan.bmob.cn/", "捡缘", getString(R.string.tips), null);
                                               }
                                           }
        );
        TextView tv_qq = (TextView) ll_viewLayout.findViewById(R.id.tv_qq);
        tv_qq.setOnClickListener(new View.OnClickListener()

                                 {

                                     @Override
                                     public void onClick(View v) {
                                         window.dismiss();
                                         Toast.makeText(SetUpActivity.this, "QQ", Toast.LENGTH_LONG).show();
                                     }
                                 }

        );
        TextView tv_qzone = (TextView) ll_viewLayout.findViewById(R.id.tv_qzone);
        tv_qzone.setOnClickListener(new View.OnClickListener()

                                    {

                                        @Override
                                        public void onClick(View v) {
                                            window.dismiss();
                                            Toast.makeText(SetUpActivity.this, "空间", Toast.LENGTH_LONG).show();
                                        }
                                    }

        );
        TextView tv_sina = (TextView) ll_viewLayout.findViewById(R.id.tv_sina);
        tv_sina.setOnClickListener(new View.OnClickListener()

                                   {

                                       @Override
                                       public void onClick(View v) {
                                           window.dismiss();
                                           Toast.makeText(SetUpActivity.this, "微博", Toast.LENGTH_LONG).show();
                                       }
                                   }

        );
        TextView tv_more = (TextView) ll_viewLayout.findViewById(R.id.tv_more);
        tv_more.setOnClickListener(new View.OnClickListener()

                                   {

                                       @Override
                                       public void onClick(View v) {
                                           window.dismiss();
                                           Intent intent = new Intent();
                                           intent.setAction(Intent.ACTION_SEND);
                                           intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.tips));
                                           intent.setType("text/plain");
                                           startActivity(intent);
//                                           Toast.makeText(SetUpActivity.this, "更多", Toast.LENGTH_LONG).show();
                                       }
                                   }

        );
        TextView tv_share_back = (TextView) ll_viewLayout.findViewById(R.id.share_back);
        tv_share_back.setOnClickListener(new View.OnClickListener()

                                         {

                                             @Override
                                             public void onClick(View v) {
                                                 // TODO Auto-generated method stub
                                                 window.dismiss();
                                             }
                                         }

        );


        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        window.showAtLocation(SetUpActivity.this.
                findViewById(R.id.linearmakeshift), Gravity

                .BOTTOM, 0, 0);

        window.setOnDismissListener(new PopupWindow.OnDismissListener()

                                    {

                                        @Override
                                        public void onDismiss() {
                                        }
                                    }

        );
    }


    public static void wechatShare(Context context, String type, String titleUrl, String title, String shareContent,
                                   String imgurl) {
        boolean flag = BaseApplication.getInstance().getWxApi().isWXAppInstalled();
        if (!flag) {
            Looper.prepare();
            Toast.makeText(context, "没有检测到微信客户端", Toast.LENGTH_SHORT).show();
            Looper.loop();
            return;
        }

        url = titleUrl;
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = shareContent;
        Bitmap thumb = null;
        if (null == imgurl || imgurl.equals("")) {
            thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        } else {
            BufferedInputStream bais;
            try {
                URL imageurl = new URL(imgurl);
                bais = new BufferedInputStream(imageurl.openStream());
                thumb = BitmapFactory.decodeStream(bais);
                int rawHeight = thumb.getHeight();
                int rawWidth = thumb.getWidth();
                int newHeight = 200;
                int newWidth = 200;
                float widthScale = ((float) newWidth) / rawWidth;
                float heightScale = ((float) newHeight) / rawHeight;
                Matrix matrix = new Matrix();
                matrix.postScale(widthScale, heightScale);
                thumb = Bitmap.createBitmap(thumb, 0, 0, rawWidth, rawHeight, matrix, true);
            } catch (Exception e) {
                e.printStackTrace();
                thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            }
        }
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if ("0".equals(type)) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if ("1".equals(type)) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        BaseApplication.getInstance().getWxApi().sendReq(req);
        if (null != thumb && !thumb.isRecycled()) {
            thumb.recycle();
        }
    }

    private void checkUpdate() {
        //获取版本
        BmobQuery<updateversion> updateversionBmobQuery = new BmobQuery<>();
        updateversionBmobQuery.findObjects(SetUpActivity.this, new FindListener<updateversion>() {
            @Override
            public void onSuccess(List<updateversion> list) {
                int servletversion = Integer.parseInt(list.get(0).getVersionname().replace(".", ""));
                int currentversion = Integer.parseInt(getVersion().replace(".", ""));
                if (servletversion > currentversion) {
                    //有更新
                    Intent intent = new Intent();
                    intent.putExtra("versionname", list.get(0).getVersionname());
                    intent.putExtra("versioncode", list.get(0).getVersioncode());
                    intent.putExtra("updateinfo", list.get(0).getUpdateinfo());
                    intent.putExtra("apkurl", list.get(0).getApk().getFileUrl(SetUpActivity.this));
                    intent.setClass(SetUpActivity.this, UpdateActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SetUpActivity.this, "暂无更新", Toast.LENGTH_LONG).show();
                }
                Log.e("update", getVersion());
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}

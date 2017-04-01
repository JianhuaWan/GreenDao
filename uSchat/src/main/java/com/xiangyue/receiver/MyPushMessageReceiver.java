package com.xiangyue.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.xiangyue.act.R;
import com.xiangyue.act.SystemMainActivity;

import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.push.PushConstants;

/**
 * Created by wWX321637 on 2016/5/18.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {

            if (BmobNotificationManager.getInstance(context).isShowNotification()) {//如果需要显示通知栏，SDK提供以下两种显示方式：
                final Intent pendingIntent = new Intent(context, SystemMainActivity.class);
                pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
                BmobNotificationManager.getInstance(context).showNotification(bitmap,
                        "系统消息", intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING).substring(10, intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING).length() - 2), "您有一条系统消息", pendingIntent);
                //1、多个用户的多条消息合并成一条通知：有XX个联系人发来了XX条消息
//                        BmobNotificationManager.getInstance(context).showNotification(event, pendingIntent);
            }
            Log.e("bmob", "收到的消息" + intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
        }

    }
}

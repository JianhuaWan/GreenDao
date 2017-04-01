package com.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.Toast;

import com.im.bean.AddFriendMessage;
import com.im.bean.AgreeAddFriendMessage;
import com.im.bean.NewFriend;
import com.im.bean.NewFriendManager;
import com.im.bean.User;
import com.im.event.RefreshEvent;
import com.im.model.UserModel;
import com.im.model.i.UpdateCacheListener;
import com.im.ui.MainActivity;
import com.xiangyue.act.R;
import com.xiangyue.base.BaseApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import de.greenrobot.event.EventBus;

/**
 * 消息接收器
 *
 * @author smile
 * @project MessageHandler
 * @date 2016-03-08-17:37
 */
public class MessageHandler extends BmobIMMessageHandler {

    private Context context;
    String photourl = null;

    public MessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessageReceive(final MessageEvent event) {
        //当接收到服务器发来的消息时，此方法被调用
        excuteMessage(event);
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
        Map<String, List<MessageEvent>> map = event.getEventMap();
        //挨个检测下离线消息所属的用户的信息是否需要更新
        for (Map.Entry<String, List<MessageEvent>> entry : map.entrySet()) {
            List<MessageEvent> list = entry.getValue();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                excuteMessage(list.get(i));
            }
        }
    }

    /**
     * 处理消息
     *
     * @param event
     */
    private void excuteMessage(final MessageEvent event) {
        //检测用户信息是否需要更新
        UserModel.getInstance().updateUserInfo(event, new UpdateCacheListener() {
            @Override
            public void done(BmobException e) {
                final BmobIMMessage msg = event.getMessage();
                if (BmobIMMessageType.getMessageTypeValue(msg.getMsgType()) == 0) {//用户自定义的消息类型，其类型值均为0
                    processCustomMessage(msg, event.getFromUserInfo());
                } else {//SDK内部内部支持的消息类型
                    if (BmobNotificationManager.getInstance(context).isShowNotification()) {//如果需要显示通知栏，SDK提供以下两种显示方式：
                        BaseApplication.getInstance().setUnreadallmessage(event.getConversation().getUnreadCount());
                        final Intent pendingIntent = new Intent(context, MainActivity.class);
                        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //1、多个用户的多条消息合并成一条通知：有XX个联系人发来了XX条消息
//                        BmobNotificationManager.getInstance(context).showNotification(event, pendingIntent);


                        //2、自定义通知消息：始终只有一条通知，新消息覆盖旧消息
                        final BmobIMUserInfo info = event.getFromUserInfo();


                        BmobQuery<com.xiangyue.type.User> userBmobQuery = new BmobQuery<com.xiangyue.type.User>();
                        userBmobQuery.getObject(context, info.getUserId(), new GetListener<com.xiangyue.type.User>() {
                            @Override
                            public void onSuccess(final com.xiangyue.type.User user) {
                                photourl = user.getHeadImage().getFileUrl(context);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //这里可以是应用图标，也可以将聊天头像转成bitmap
                                        Bitmap thumb = null;
                                        BufferedInputStream bais;
                                        try {
                                            URL imageurl = new URL(photourl);
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
                                        } catch (Exception tt) {
                                            tt.printStackTrace();
                                            thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
                                        }
                                        String ticker = msg.getContent().replaceAll("\\[.{2,3}\\]", "[表情]");
                                        BmobNotificationManager.getInstance(context).showNotification(thumb,
                                                user.getNickName(), ticker, "您有一条新消息", pendingIntent);
                                    }
                                }).start();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                            }
                        });


                    } else {//直接发送消息事件
                        EventBus.getDefault().post(event);
                    }
                }
            }
        });
    }

    /**
     * 处理自定义消息类型
     *
     * @param msg
     */
    private void processCustomMessage(BmobIMMessage msg, BmobIMUserInfo info) {
        //自行处理自定义消息类型
        String type = msg.getMsgType();
        //发送页面刷新的广播
        EventBus.getDefault().post(new RefreshEvent());
        //处理消息
        if (type.equals("add")) {//接收到的添加好友的请求
            NewFriend friend = AddFriendMessage.convert(msg);
            //本地好友请求表做下校验，本地没有的才允许显示通知栏--有可能离线消息会有些重复
            long id = NewFriendManager.getInstance(context).insertOrUpdateNewFriend(friend);
            if (id > 0) {
                showAddNotify(friend);
            }
        } else if (type.equals("agree")) {//接收到的对方同意添加自己为好友,此时需要做的事情：1、添加对方为好友，2、显示通知
            AgreeAddFriendMessage agree = AgreeAddFriendMessage.convert(msg);
            addFriend(agree.getFromId());//添加消息的发送方为好友
            //这里应该也需要做下校验--来检测下是否已经同意过该好友请求，我这里省略了
            showAgreeNotify(info, agree);
        } else {
            Toast.makeText(context, "接收到的自定义消息：" + msg.getMsgType() + "," + msg.getContent() + "," + msg.getExtra(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示对方添加自己为好友的通知
     *
     * @param friend
     */
    private void showAddNotify(NewFriend friend) {
        Intent pendingIntent = new Intent(context, MainActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //这里可以是应用图标，也可以将聊天头像转成bitmap
        Bitmap largetIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        BmobNotificationManager.getInstance(context).showNotification(largetIcon,
                friend.getName(), friend.getMsg(), friend.getName() + "请求添加你为朋友", pendingIntent);
    }

    /**
     * 显示对方同意添加自己为好友的通知
     *
     * @param info
     * @param agree
     */
    private void showAgreeNotify(BmobIMUserInfo info, AgreeAddFriendMessage agree) {
        Intent pendingIntent = new Intent(context, MainActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bitmap largetIcon = getBitmapFromUrl(info.getAvatar());
        BmobNotificationManager.getInstance(context).showNotification(largetIcon,
                info.getName(), agree.getMsg(), agree.getMsg(), pendingIntent);
    }


    public static Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 100 * 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 100 * 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[100 * 1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    /**
     * 添加对方为自己的好友
     *
     * @param uid
     */
    private void addFriend(String uid) {
        User user = new User();
        user.setObjectId(uid);
        UserModel.getInstance().agreeAddFriend(user, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.i("bmob", "onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("bmob", "onFailure:" + s + "-" + i);
            }
        });
    }
}

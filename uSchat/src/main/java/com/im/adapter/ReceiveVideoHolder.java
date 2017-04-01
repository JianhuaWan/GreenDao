package com.im.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.type.User;
import com.xiangyue.view.CircleImageView;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMVideoMessage;
import cn.bmob.newim.core.BmobDownloadManager;
import cn.bmob.newim.listener.FileDownloadListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.GetListener;

/**
 * 接收到的文本类型
 */
public class ReceiveVideoHolder extends BaseViewHolder {

    @Bind(R.id.iv_avatar)
    protected CircleImageView iv_avatar;

    @Bind(R.id.tv_time)
    protected TextView tv_time;

    @Bind(R.id.iv_picture)
    protected ImageView iv_picture;
    @Bind(R.id.progress_load)
    protected ProgressBar progress_load;
    private Activity activity;
    public Context context;

    public ReceiveVideoHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {

        super(context, root, R.layout.item_chat_received_video, onRecyclerViewListener);
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    public void bindData(Object o) {
        BmobIMMessage msg = (BmobIMMessage) o;
        //用户信息的获取必须在buildFromDB之前，否则会报错'Entity is detached from DAO context'
//        final BmobIMUserInfo info = msg.getBmobIMUserInfo();
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.getObject(context, msg.getFromId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                ViewUtil.setPicture(context, user.getHeadImage().getFileUrl(context), R.drawable.default_head, iv_avatar, null);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        });
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(msg.getCreateTime());
        tv_time.setText(time);
        //显示特有属性
        final BmobIMVideoMessage message = BmobIMVideoMessage.buildFromDB(false, msg);
//        boolean isExists = BmobDownloadManager.isAudioExist(currentUid, message);
//        if (!isExists) {//若指定格式的录音文件不存在，则需要下载，因为其文件比较小，故放在此下载
        BmobDownloadManager downloadTask = new BmobDownloadManager(getContext(), msg, new FileDownloadListener() {

            @Override
            public void onStart() {
                progress_load.setVisibility(View.VISIBLE);
                iv_picture.setVisibility(View.GONE);//只有下载完成才显示播放的按钮
            }

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    progress_load.setVisibility(View.GONE);
                    iv_picture.setVisibility(View.VISIBLE);
                } else {
                    progress_load.setVisibility(View.GONE);
                    iv_picture.setVisibility(View.GONE);
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        downloadTask.execute(message.getContent());
        //显示图片
//        Picasso.with(context).load(R.drawable.video_download_btn_nor).placeholder(R.drawable.head).error(R.drawable.head).into(iv_picture);
//        ViewUtil.setPicture(context, message.getRemoteUrl(), R.drawable.ic_launcher, iv_picture, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String s, View view) {
//                progress_load.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                progress_load.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onLoadingCancelled(String s, View view) {
//                progress_load.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onLoadingFailed(String s, View view, FailReason failReason) {
//                progress_load.setVisibility(View.INVISIBLE);
//            }
//        });

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(activity, )
//                activity.startActivity(intent);
//                toast("点击" + info.getName() + "的头像");
//                Intent intent = new Intent();
//                intent.setClass(context, OtherInfoDetailActivity.class);
//                context.startActivity(intent);
            }
        });

        iv_picture.setOnClickListener(new NewVideoPlayClickListener(context, message, iv_picture));

        iv_picture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                if (onRecyclerViewListener != null) {
//                    onRecyclerViewListener.onItemLongClick(getAdapterPosition());
//                }
                return true;
            }
        });

    }

    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
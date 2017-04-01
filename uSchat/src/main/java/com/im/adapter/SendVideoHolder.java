package com.im.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.view.CircleImageView;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMSendStatus;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.bean.BmobIMVideoMessage;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;

/**
 * 发送的类型
 */
public class SendVideoHolder extends BaseViewHolder {

    @Bind(R.id.iv_avatar)
    protected CircleImageView iv_avatar;

    @Bind(R.id.iv_fail_resend)
    protected ImageView iv_fail_resend;

    @Bind(R.id.tv_time)
    protected TextView tv_time;

    @Bind(R.id.iv_picture)
    protected ImageView iv_picture;

    @Bind(R.id.tv_send_status)
    protected TextView tv_send_status;

    @Bind(R.id.progress_load)
    protected ProgressBar progress_load;
    BmobIMConversation c;
    private Activity activity;

    public SendVideoHolder(Context context, ViewGroup root, BmobIMConversation c, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_chat_sent_video, onRecyclerViewListener);
        this.c = c;
        this.activity = (Activity) context;
    }

    @Override
    public void bindData(Object o) {
        BmobIMMessage msg = (BmobIMMessage) o;
        //用户信息的获取必须在buildFromDB之前，否则会报错'Entity is detached from DAO context'
        final BmobIMUserInfo info = msg.getBmobIMUserInfo();
        MicroRecruitSettings settings = new MicroRecruitSettings(context);
        ViewUtil.setPicture(context, settings.HEADICON.getValue(), R.drawable.head, iv_avatar, null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(msg.getCreateTime());
        tv_time.setText(time);
        //
        final BmobIMVideoMessage message = BmobIMVideoMessage.buildFromDB(true, msg);
        int status = message.getSendStatus();
        if (status == BmobIMSendStatus.SENDFAILED.getStatus() || status == BmobIMSendStatus.UPLOADAILED.getStatus()) {
            iv_fail_resend.setVisibility(View.VISIBLE);
            progress_load.setVisibility(View.GONE);
            tv_send_status.setVisibility(View.INVISIBLE);
        } else if (status == BmobIMSendStatus.SENDING.getStatus()) {
            progress_load.setVisibility(View.VISIBLE);
            iv_fail_resend.setVisibility(View.GONE);
            tv_send_status.setVisibility(View.INVISIBLE);
        } else {
            tv_send_status.setVisibility(View.VISIBLE);
            tv_send_status.setText("已发送");
            iv_fail_resend.setVisibility(View.GONE);
            progress_load.setVisibility(View.GONE);
        }

        //发送的不是远程图片地址，则取本地地址
        ViewUtil.setPicture(context, "", R.drawable.ic_launcher, iv_picture, null);
//        Picasso.with(context).load(R.drawable.video_download_btn_nor).placeholder(R.drawable.head).error(R.drawable.head).into(iv_picture);
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toast("点击" + info.getName() + "的头像");
//                Intent intent = new Intent();
//                intent.setClass(context, OtherInfoDetailActivity.class);
//                context.startActivity(intent);
            }
        });
        iv_picture.setOnClickListener(new NewVideoPlayClickListener(context, message, iv_picture));
//            @Override
//            public void onClick(View v) {
//                toast("点击图片:" + (TextUtils.isEmpty(message.getRemoteUrl()) ? message.getLocalPath() : message.getRemoteUrl()) + "");
//                Intent intent = new Intent();
//                intent.putExtra("uri", message.getRemoteUrl());
//                intent.setClass(activity, VideoPlayActivity.class);
//                activity.startActivity(intent);


//            }
//        });

        iv_picture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                if (onRecyclerViewListener != null) {
//                    onRecyclerViewListener.onItemLongClick(getAdapterPosition());
//                }
                return true;
            }
        });

        //重发
        iv_fail_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.resendMessage(message, new MessageSendListener() {
                    @Override
                    public void onStart(BmobIMMessage msg) {
                        progress_load.setVisibility(View.VISIBLE);
                        iv_fail_resend.setVisibility(View.GONE);
                        tv_send_status.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void done(BmobIMMessage msg, BmobException e) {
                        if (e == null) {
                            tv_send_status.setVisibility(View.VISIBLE);
                            tv_send_status.setText("已发送");
                            iv_fail_resend.setVisibility(View.GONE);
                            progress_load.setVisibility(View.GONE);
                        } else {
                            iv_fail_resend.setVisibility(View.VISIBLE);
                            progress_load.setVisibility(View.GONE);
                            tv_send_status.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}

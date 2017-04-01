package com.im.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.util.SmileUtils;
import com.xiangyue.view.CircleImageView;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMSendStatus;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;

/**
 * 发送的文本类型
 */
public class SendTextHolder extends BaseViewHolder implements View.OnClickListener, View.OnLongClickListener {

    @Bind(R.id.iv_avatar)
    protected CircleImageView iv_avatar;

    @Bind(R.id.iv_fail_resend)
    protected ImageView iv_fail_resend;

    @Bind(R.id.tv_time)
    protected TextView tv_time;

    @Bind(R.id.tv_message)
    protected TextView tv_message;
    @Bind(R.id.tv_send_status)
    protected TextView tv_send_status;

    @Bind(R.id.progress_load)
    protected ProgressBar progress_load;

    BmobIMConversation c;
    private Context context;

    public SendTextHolder(Context context, ViewGroup root, BmobIMConversation c, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_chat_sent_message, listener);
        this.c = c;
        this.context = context;

    }

    @Override
    public void bindData(Object o) {
        final BmobIMMessage message = (BmobIMMessage) o;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final BmobIMUserInfo info = message.getBmobIMUserInfo();
        MicroRecruitSettings settings = new MicroRecruitSettings(context);
        ViewUtil.setPicture(context, settings.HEADICON.getValue(), R.drawable.default_head, iv_avatar, null);
        String time = dateFormat.format(message.getCreateTime());
        final String content = message.getContent();
        Spannable span = SmileUtils.getSmiledText(context, message.getContent());
        //设置内容
        tv_message.setText(span, TextView.BufferType.SPANNABLE);
        tv_time.setText(time);

        int status = message.getSendStatus();
        if (status == BmobIMSendStatus.SENDFAILED.getStatus()) {
            iv_fail_resend.setVisibility(View.VISIBLE);
            progress_load.setVisibility(View.GONE);
        } else if (status == BmobIMSendStatus.SENDING.getStatus()) {
            iv_fail_resend.setVisibility(View.GONE);
            progress_load.setVisibility(View.VISIBLE);
        } else {
            iv_fail_resend.setVisibility(View.GONE);
            progress_load.setVisibility(View.GONE);
        }

        tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toast("点击" + message.getContent());
//                if (onRecyclerViewListener != null) {
//                    onRecyclerViewListener.onItemClick(getAdapterPosition());
//                }
            }
        });

        tv_message.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                if (onRecyclerViewListener != null) {
//                    onRecyclerViewListener.onItemLongClick(getAdapterPosition());
//                }
//
                //长按复制
                toast("内容已复制");
                copy(message.getContent(), context);
                return true;
            }
        });

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toast("点击" + info.getName() + "的头像");
//                Intent intent = new Intent();
//                intent.setClass(context, OtherInfoDetailActivity.class);
//                context.startActivity(intent);
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

    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }
}

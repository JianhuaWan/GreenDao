package com.im.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.type.User;
import com.xiangyue.util.SmileUtils;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * 接收到的文本类型
 */
public class ReceiveTextHolder extends BaseViewHolder {

    @Bind(R.id.iv_avatar)
    protected ImageView iv_avatar;

    @Bind(R.id.tv_time)
    protected TextView tv_time;

    @Bind(R.id.tv_message)
    protected TextView tv_message;

    public ReceiveTextHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_chat_received_message, onRecyclerViewListener);
    }

    @OnClick({R.id.iv_avatar})
    public void onAvatarClick(View view) {

    }

    @Override
    public void bindData(Object o) {
        final BmobIMMessage message = (BmobIMMessage) o;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(message.getCreateTime());
        tv_time.setText(time);
//        final BmobIMUserInfo info = message.getBmobIMUserInfo();
        //没有保存头像,自己查询user表
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.getObject(context, message.getFromId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                ViewUtil.setPicture(context, user.getHeadImage().getFileUrl(context), R.drawable.default_head, iv_avatar, null);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        });
        String content = message.getContent();
        Spannable span = SmileUtils.getSmiledText(context, message.getContent());
        //设置内容
        tv_message.setText(span, TextView.BufferType.SPANNABLE);
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toast("点击" + info.getName() + "的头像");
//                Intent intent = new Intent();
//                intent.setClass(context, OtherInfoDetailActivity.class);
//                context.startActivity(intent);
            }
        });

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
                //长按复制
                toast("内容已复制");
                copy(message.getContent(), context);
                return true;
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
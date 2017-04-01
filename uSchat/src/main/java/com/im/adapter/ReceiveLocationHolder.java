package com.im.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.xiangyue.act.BaiduMapActivity;
import com.xiangyue.act.R;
import com.xiangyue.type.User;
import com.xiangyue.view.CircleImageView;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import cn.bmob.newim.bean.BmobIMLocationMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * 接收到的位置类型
 */
public class ReceiveLocationHolder extends BaseViewHolder {

    @Bind(R.id.iv_avatar)
    protected CircleImageView iv_avatar;

    @Bind(R.id.tv_time)
    protected TextView tv_time;

    @Bind(R.id.tv_location)
    protected TextView tv_location;
    private Activity activity;

    public ReceiveLocationHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_chat_received_location, onRecyclerViewListener);
        this.activity = (Activity) context;
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
        //
        final BmobIMLocationMessage message = BmobIMLocationMessage.buildFromDB(msg);
        tv_location.setText(message.getAddress());
        //
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("latitude", message.getLatitude());
                intent.putExtra("longitude", message.getLongitude());
                intent.putExtra("address", message.getAddress());
                intent.setClass(activity, BaiduMapActivity.class);
                activity.startActivity(intent);
            }
        });
        tv_location.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                if (onRecyclerViewListener != null) {
//                    onRecyclerViewListener.onItemLongClick(getAdapterPosition());
//                }
                return true;
            }
        });

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toast("点击" + info.getName() + "头像");
//                Intent intent = new Intent();
//                intent.setClass(context, OtherInfoDetailActivity.class);
//                context.startActivity(intent);
            }
        });
    }

    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
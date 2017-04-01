package com.im.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.TextView;

import com.im.bean.User;
import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.view.CircleImageView;

import butterknife.Bind;

public class SearchUserHolder extends BaseViewHolder {

    @Bind(R.id.avatar)
    public CircleImageView avatar;
    @Bind(R.id.name)
    public TextView name;
    @Bind(R.id.age)
    public TextView age;
    User user;

    public SearchUserHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_search_user, onRecyclerViewListener);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void bindData(Object o) {
        user = (User) o;
        ViewUtil.setPicture(context, user.getHeadImage().getFileUrl(context), R.drawable.head, avatar, null);
        name.setText(user.getNickName());
        age.setText(user.getAge());
        if (user.getSex().equals(getContext().getString(R.string.boy))) {
            age.setBackground(context.getResources().getDrawable(R.drawable.sex_man));
        } else {
            age.setBackground(context.getResources().getDrawable(R.drawable.sex_women));
        }

//        btn_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getHeadImage().getFileUrl(context));
//                EventBus.getDefault().post(new ChatEvent(info));
//
//
//            }
//        });
    }

}
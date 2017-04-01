package com.im.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.TimeUtil;
import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.type.User;
import com.xiangyue.view.CircleImageView;

import java.util.List;

import butterknife.Bind;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

public class ConversationHolder extends BaseViewHolder {

    @Bind(R.id.iv_recent_avatar)
    public CircleImageView iv_recent_avatar;
    @Bind(R.id.tv_recent_name)
    public TextView tv_recent_name;
    @Bind(R.id.tv_recent_msg)
    public TextView tv_recent_msg;
    @Bind(R.id.tv_recent_time)
    public TextView tv_recent_time;
    @Bind(R.id.tv_recent_unread)
    public TextView tv_recent_unread;

    public ConversationHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_conversation, onRecyclerViewListener);
    }

    @Override
    public void bindData(Object o) {
        final BmobIMConversation conversation = (BmobIMConversation) o;
        List<BmobIMMessage> msgs = conversation.getMessages();
        if (msgs != null && msgs.size() > 0) {
            BmobIMMessage lastMsg = msgs.get(0);
            String content = lastMsg.getContent();
            if (lastMsg.getMsgType().equals(BmobIMMessageType.TEXT.getType())) {
                String ticker = content.replaceAll("\\[.{2,3}\\]", "[表情]");
                tv_recent_msg.setText(ticker);
            } else if (lastMsg.getMsgType().equals(BmobIMMessageType.IMAGE.getType())) {
                tv_recent_msg.setText("[图片]");
            } else if (lastMsg.getMsgType().equals(BmobIMMessageType.VOICE.getType())) {
                tv_recent_msg.setText("[语音]");
            } else if (lastMsg.getMsgType().equals(BmobIMMessageType.LOCATION.getType())) {
                tv_recent_msg.setText("[位置]" + content);
            } else if (lastMsg.getMsgType().equals(BmobIMMessageType.VIDEO.getType())) {
                tv_recent_msg.setText("[视频]" + content);
            } else {//开发者自定义的消息类型，需要自行处理
                tv_recent_msg.setText("[未知]");
            }
            tv_recent_time.setText(TimeUtil.getChatTime(false, lastMsg.getCreateTime()));
        }
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.getObject(context, conversation.getConversationId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                conversation.setConversationTitle(user.getNickName());
                //会话图标
//        ViewUtil.setAvatar(context, conversation.getConversationIcon(), R.drawable.head, iv_recent_avatar);
                ViewUtil.setPicture(context, user.getHeadImage().getFileUrl(context), R.drawable.default_head, iv_recent_avatar, null);
                //会话标题
                tv_recent_name.setText(user.getNickName());
                //查询指定未读消息数
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        });

        long unread = BmobIM.getInstance().getUnReadCount(conversation.getConversationId());
        if (unread > 0) {
            tv_recent_unread.setVisibility(View.VISIBLE);
            tv_recent_unread.setText(String.valueOf(unread));
        } else {
            tv_recent_unread.setVisibility(View.GONE);
        }
    }

}
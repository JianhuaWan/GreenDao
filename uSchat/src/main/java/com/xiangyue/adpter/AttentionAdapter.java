package com.xiangyue.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.type.User;

import java.util.List;

public class AttentionAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<User> jsonBeans;

    public AttentionAdapter(Context context, List<User> jsonBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.jsonBeans = jsonBeans;
    }

    public void setData(List<User> info) {
        this.jsonBeans = info;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return jsonBeans.size();
    }

    @Override
    public User getItem(int position) {
        // TODO Auto-generated method stub
        return jsonBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.attention_item_main, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.myheadIcon = (ImageView) convertView.findViewById(R.id.myheadIcon);
            viewHolder.img_situp = (TextView) convertView.findViewById(R.id.img_situp);
            viewHolder.tv_signqm = (TextView) convertView.findViewById(R.id.tv_signqm);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(jsonBeans.get(position).getNickName());
//        Picasso.with(context).load(jsonBeans.get(position).getHeadImage().getFileUrl(context)).error(R.drawable.default_head).placeholder(R.drawable.default_head).into(viewHolder.myheadIcon);


        ViewUtil.setPicture(context, jsonBeans.get(position).getHeadImage().getFileUrl(context), R.drawable.default_head, viewHolder.myheadIcon, null);
        viewHolder.img_situp.setText(jsonBeans.get(position).getCreatedAt().substring(0, 10));
        if (jsonBeans.get(position).getSign() == null) {
            viewHolder.tv_signqm.setText("暂无签名");
        } else {
            viewHolder.tv_signqm.setText(jsonBeans.get(position).getSign());
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        ImageView myheadIcon;
        TextView img_situp;
        TextView tv_signqm;

    }

}

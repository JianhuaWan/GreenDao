package com.xiangyue.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.util.ViewUtil;
import com.xiangyue.act.OtherInfoDetailActivity;
import com.xiangyue.act.R;
import com.xiangyue.type.User;

import java.util.List;

/**
 * Created by wWX321637 on 2016/5/30.
 */
public class HeadAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<User> jsonBeans;

    public HeadAdapter(Context context, List<User> jsonBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.jsonBeans = jsonBeans;
    }

    public void setData(List<User> jsonBeans) {
        this.jsonBeans = jsonBeans;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.head_item, null);
            viewHolder.myheadIcon = (ImageView) convertView.findViewById(R.id.myheadIcon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.myheadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("userid", jsonBeans.get(position).getUsername());
                intent.putExtra("objectId", jsonBeans.get(position).getObjectId());
                intent.setClass(context, OtherInfoDetailActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.tv_name.setText(jsonBeans.get(position).getNickName());
        ViewUtil.setPicture(context, jsonBeans.get(position).getHeadImage().getFileUrl(context), R.drawable.default_head, viewHolder.myheadIcon, null);
        return convertView;
    }

    class ViewHolder {
        ImageView myheadIcon;
        TextView tv_name;

    }

}

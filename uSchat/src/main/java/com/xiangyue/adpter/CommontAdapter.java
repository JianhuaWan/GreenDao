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
import com.xiangyue.bean.CircleCommonBean;

import java.util.List;

public class CommontAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<CircleCommonBean> jsonBeans;

    public CommontAdapter(Context context, List<CircleCommonBean> jsonBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.jsonBeans = jsonBeans;
    }

    public void setData(List<CircleCommonBean> info) {
        this.jsonBeans = info;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return jsonBeans.size();
    }

    @Override
    public CircleCommonBean getItem(int position) {
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
            viewHolder.tv_signqm = (TextView) convertView.findViewById(R.id.tv_signqm);
            viewHolder.img_situp = (TextView) convertView.findViewById(R.id.img_situp);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(jsonBeans.get(position).getConmonpeoson());
        ViewUtil.setPicture(context, jsonBeans.get(position).getCommonhead(), R.drawable.default_head, viewHolder.myheadIcon, null);
        viewHolder.tv_signqm.setText(jsonBeans.get(position).getCommoncontent());
        viewHolder.img_situp.setText(jsonBeans.get(position).getCreatedAt().toString().substring(5));
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        ImageView myheadIcon;
        TextView tv_signqm;
        TextView img_situp;

    }

}

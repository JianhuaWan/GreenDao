package com.wanjianhua.stock.act.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.act.SiteDetailActivity;
import com.wanjianhua.stock.act.bean.SiteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<SiteInfo> jsonBeans = new ArrayList<>();

    public SiteAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<SiteInfo> jsonBeans) {
        this.jsonBeans = jsonBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return jsonBeans.size();
    }

    @Override
    public SiteInfo getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_site, null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_code = (TextView) convertView.findViewById(R.id.tv_code);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imgageview);
            viewHolder.rel_bottom = (RelativeLayout) convertView.findViewById(R.id.rel_bottom);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(jsonBeans.get(position).getName());
        viewHolder.tv_code.setText(context.getString(R.string.code) + ":" + jsonBeans.get(position).getCode());
        viewHolder.tv_price.setText(jsonBeans.get(position).getSingleprice() + "元");
//        viewHolder.rel_bottom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("info", jsonBeans.get(position));
//                intent.setClass(context, SiteDetailActivity.class);
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_name, tv_code, tv_price;
        ImageView imageView;
        RelativeLayout rel_bottom;

    }
}

package com.xiangyue.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangyue.act.R;

import java.util.List;

public class Pengadapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> jsonBeans;

    public Pengadapter(Context context, List<String> jsonBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.jsonBeans = jsonBeans;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return jsonBeans.size();
    }

    @Override
    public String getItem(int position) {
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
            convertView = inflater.inflate(R.layout.study_item_main, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(jsonBeans.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView tv_title;

    }

}

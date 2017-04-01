package com.xiangyue.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangyue.act.R;
import com.xiangyue.bean.systemtips;

import java.util.List;

public class SystemTipsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<systemtips> jsonBeans;

    public SystemTipsAdapter(Context context, List<systemtips> jsonBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.jsonBeans = jsonBeans;
    }

    public void setDate(Context context, List<systemtips> jsonB) {
        this.context = context;
        this.jsonBeans = jsonB;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return jsonBeans.size();
    }

    @Override
    public systemtips getItem(int position) {
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
            convertView = inflater.inflate(R.layout.system_item_main, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(jsonBeans.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        TextView tv_title;

    }

}

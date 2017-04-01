package com.xiangyue.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangyue.act.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 界面的适配器
 *
 * @author Administrator
 */
public class HshowtopAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<String> hshowlist = new ArrayList<String>();

    public HshowtopAdapter(Context context, List<String> hshowlist) {
        this.context = context;
        this.hshowlist = hshowlist;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<String> hshowlist) {
        this.hshowlist = hshowlist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return hshowlist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return hshowlist.get(position);
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
            convertView = inflater.inflate(R.layout.adpater_study, null);
            viewHolder.tv_test = (TextView) convertView.findViewById(R.id.tv_test);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_test.setText(hshowlist.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView tv_test;

    }
}

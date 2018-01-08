package com.wanjianhua.aooshop.act.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.bean.ShopStateInfo;
import com.wanjianhua.aooshop.act.bean.TicketInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class StateAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private List<ShopStateInfo> jsonBeans = new ArrayList<>();

    public StateAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<ShopStateInfo> jsonBeans)
    {
        this.jsonBeans = jsonBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return jsonBeans.size();
    }

    @Override
    public ShopStateInfo getItem(int position)
    {
        // TODO Auto-generated method stub
        return jsonBeans.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.state_item, null);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.tv_name.setText("");
        return convertView;
    }

    class ViewHolder
    {
        TextView tv_name, tv_code, tv_price;
    }
}

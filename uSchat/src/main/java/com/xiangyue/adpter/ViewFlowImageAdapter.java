package com.xiangyue.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.act.StudyDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewFlowImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mbanners = new ArrayList<String>();

    // private static final int[] ids = { R.drawable.one, R.drawable.two };

    public ViewFlowImageAdapter(Context context, List<String> banners) {
        mContext = context;
        mbanners = banners;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDate(List<String> banners) {
        mbanners = banners;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mbanners.size();
    }

    @Override
    public Object getItem(int position) {
        return mbanners.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.image_item, null);
        }

        // .setImageResource(mbanners.get(position).AdvertIcon);
        if (mbanners.size() != 0) {
//            Picasso.with(mContext).load(R.drawable.ic_launcher).placeholder(R.drawable.ic_launcher)
//                    .error(R.drawable.ic_launcher).into(((ImageView) convertView.findViewById(R.id.imgView)));
            ViewUtil.setPicture(mContext, "", R.drawable.default_head, ((ImageView) convertView.findViewById(R.id.imgView)), null);
        }


        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StudyDetailActivity.class);
                intent.putExtra("id", mbanners.get(position));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
}

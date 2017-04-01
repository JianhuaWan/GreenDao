package com.xiangyue.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xiangyue.act.R;
import com.xiangyue.image.ImageDisplayer;
import com.xiangyue.image.ImageItem;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.provider.ShowPhotoEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐showGirl列表图片
 *
 * @author Administrator
 */
public class WiritePhotoAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<ImageItem> list = new ArrayList<ImageItem>();

    public WiritePhotoAdapter(Context context, List<ImageItem> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void setData(List<ImageItem> listMoneyTypes) {
        this.list = listMoneyTypes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    public ImageItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int arg0, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.wiritephoto_item, null);
            viewHolder.img_del = (ImageView) convertView.findViewById(R.id.img_del);
            viewHolder.img_photo = (ImageView) convertView.findViewById(R.id.img_photo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Picasso.with(context).load(new File(list.get(arg0).getThumbnailPath())).placeholder(R.drawable.default_head).error(R.drawable.default_head).into(viewHolder.img_photo);
        ImageDisplayer.getInstance(context).displayBmp(viewHolder.img_photo, list.get(arg0).thumbnailPath, list.get(arg0).sourcePath);


        viewHolder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(arg0);
                notifyDataSetChanged();
                if (list.size() == 0) {
                    BusProvider.getInstance().post(new ShowPhotoEvent());
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView img_del;//
        private ImageView img_photo;//
    }

}

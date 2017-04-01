package com.xiangyue.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.bean.PhotoUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐showGirl列表图片
 *
 * @author Administrator
 */
public class MyPhotosAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<PhotoUrl> list = new ArrayList<PhotoUrl>();

    public MyPhotosAdapter(Context context, List<PhotoUrl> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void setData(List<PhotoUrl> listMoneyTypes) {
        this.list = listMoneyTypes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    public PhotoUrl getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.myphotolist_item, null);
            viewHolder.img_girls_photo = (ImageView) convertView.findViewById(R.id.img_girls_photo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //
        // Log.e("abc", "位置" + arg0 + "对象=" + pType.toString());
        // Resources res = context.getResources();
        // Bitmap bmp = BitmapFactory.decodeResource(res,
        // R.drawable.chat_photo);
        // loader.display(viewHolder.img_girls_photo, Urls.BASIC_URL +
        // pType.getHeadImage(), bmp);
//        Picasso.with(context).load(list.get(arg0).getShowurl()).error(R.drawable.chat_photo).placeholder(R.drawable.chat_photo)
//                .into(viewHolder.img_girls_photo);
        ViewUtil.setPicture(context, list.get(arg0).getShowurl(), R.drawable.default_head, viewHolder.img_girls_photo, null);
        int num = arg0 % 3;
        LayoutParams layoutParams;
        switch (num) {
            case 0:
                layoutParams = (LayoutParams) viewHolder.img_girls_photo.getLayoutParams();
                layoutParams.rightMargin = 1;
                break;
            case 1:
                layoutParams = (LayoutParams) viewHolder.img_girls_photo.getLayoutParams();
                layoutParams.rightMargin = 1;
                break;
            default:
                break;
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView img_girls_photo;
    }

}

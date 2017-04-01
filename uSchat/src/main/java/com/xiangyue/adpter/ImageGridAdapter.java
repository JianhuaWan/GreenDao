package com.xiangyue.adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xiangyue.act.R;
import com.xiangyue.image.ImageDisplayer;
import com.xiangyue.image.ImageItem;

import java.util.List;

public class ImageGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<ImageItem> mDataList;

    public ImageGridAdapter(Context context, List<ImageItem> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_image_list, null);
            mHolder = new ViewHolder();
            mHolder.mImageView = (ImageView) convertView.findViewById(R.id.iv_item_image);
            mHolder.mSelect = (ImageView) convertView.findViewById(R.id.selected_tag);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        final ImageItem item = mDataList.get(position);

        ImageDisplayer.getInstance(mContext).displayBmp(mHolder.mImageView, item.thumbnailPath, item.sourcePath);

        mHolder.mImageView.setColorFilter(null);
        if (item.isSelected) {
            mHolder.mSelect.setImageDrawable(mContext.getResources().getDrawable(
                    R.drawable.feedback_pictures_select_icon_selected));
            mHolder.mSelect.setVisibility(View.VISIBLE);
            mHolder.mImageView.setColorFilter(Color.parseColor("#77000000"));
        } else {
            mHolder.mSelect.setImageDrawable(null);
            mHolder.mSelect.setVisibility(View.GONE);
            mHolder.mImageView.setColorFilter(null);
        }

        return convertView;
    }

    static class ViewHolder {
        private ImageView mImageView;
        private ImageView mSelect;
    }

}

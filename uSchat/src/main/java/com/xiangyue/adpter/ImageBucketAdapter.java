package com.xiangyue.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiangyue.act.R;
import com.xiangyue.image.ImageBucket;
import com.xiangyue.image.ImageDisplayer;

import java.util.List;

public class ImageBucketAdapter extends BaseAdapter {
    private List<ImageBucket> mDataList;
    private Context mContext;

    public ImageBucketAdapter(Context context, List<ImageBucket> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return null == mDataList ? 0 : mDataList.size();
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
            convertView = View.inflate(mContext, R.layout.feedback_item_image_bucket, null);
            mHolder = new ViewHolder();
            mHolder.coverIv = (ImageView) convertView.findViewById(R.id.iv_cover);
            mHolder.titleTv = (TextView) convertView.findViewById(R.id.title);
            mHolder.countTv = (TextView) convertView.findViewById(R.id.count);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        final ImageBucket item = mDataList.get(position);

        if (item.imageList != null && item.imageList.size() > 0) {
            String thumbPath = item.imageList.get(0).thumbnailPath;
            String sourcePath = item.imageList.get(0).sourcePath;
            ImageDisplayer.getInstance(mContext).displayBmp(mHolder.coverIv, thumbPath, sourcePath);
        } else {
            mHolder.coverIv.setImageBitmap(null);
        }

        mHolder.titleTv.setText(item.bucketName);
        mHolder.countTv.setText(item.count + mContext.getResources().getString(R.string.sub_size));

        return convertView;
    }

    private class ViewHolder {
        ImageView coverIv;
        TextView titleTv;
        TextView countTv;
    }

}

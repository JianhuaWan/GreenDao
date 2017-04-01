package com.xiangyue.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.util.ViewUtil;
import com.xiangyue.act.R;
import com.xiangyue.type.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐showGirl列表图片
 *
 * @author Administrator
 */
public class MoneyPhotosAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<User> list = new ArrayList<User>();

    public MoneyPhotosAdapter(Context context, List<User> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void setData(List<User> listMoneyTypes) {
        this.list = listMoneyTypes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    public User getItem(int position) {
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
            convertView = inflater.inflate(R.layout.moneyphotolist_item, null);
            viewHolder.img_girls_photo = (ImageView) convertView.findViewById(R.id.img_girls_photo);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.showgirl_viptype = (ImageView) convertView.findViewById(R.id.showgirl_viptype);
            viewHolder.img_isonline_byshowgirl = (ImageView) convertView.findViewById(R.id.img_isonline_byshowgirl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //
        User pType = list.get(arg0);
        ViewUtil.setPicture(context, pType.getHeadImage().getFileUrl(context), R.drawable.default_head, viewHolder.img_girls_photo, null);
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
        viewHolder.tv_name.setText(pType.getNickName());
        return convertView;
    }

    private class ViewHolder {
        private ImageView img_girls_photo;
        private TextView tv_name;
        private ImageView showgirl_viptype;// 显示showGirl是否是会员
        private ImageView img_isonline_byshowgirl;// 显示是否在线
    }

}

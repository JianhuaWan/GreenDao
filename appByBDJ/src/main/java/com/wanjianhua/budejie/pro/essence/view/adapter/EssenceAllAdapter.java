package com.wanjianhua.budejie.pro.essence.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanjianhua.budejie.R;
import com.wanjianhua.budejie.bean.EssecneListBean;
import com.wanjianhua.budejie.pro.base.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by ying on 2016/6/15.
 */
public class EssenceAllAdapter extends RecyclerView.Adapter<EssenceAllAdapter.MyViewHolder> {
//public class EssenceAllAdapter extends BaseRecyclerAdapter<EssenceAllAdapter.MyViewHolder> {

    List<EssecneListBean.ListBean> list;
    private Context context;

    public EssenceAllAdapter(List<EssecneListBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void addData(List<EssecneListBean.ListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(List<EssecneListBean.ListBean> list) {
        this.list.clear();
        addData(list);
    }


//    @Override
//    public MyViewHolder getViewHolder(View view) {
//        return new MyViewHolder(view,false);
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_essence_video_layout, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EssecneListBean.ListBean listBean = list.get(position);
        if (listBean.getImage1() == null || listBean.getImage1().length() == 0) {
            holder.iv_video.setVisibility(View.GONE);
        } else {
            holder.iv_video.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(listBean.getImage1())
                    .into(holder.iv_video);
        }
        Glide.with(context)
                .load(listBean.getProfile_image())
                .transform(new GlideCircleTransform(context))
                .into(holder.iv_header);
        holder.tv_comment.setText(listBean.getComment());
        holder.tv_content.setText(listBean.getText());
        holder.tv_like.setText(listBean.getLove());
        holder.tv_forword.setText(listBean.getRepost());
        holder.tv_dislike.setText(listBean.getCai());
        holder.tv_name.setText(listBean.getName());
        holder.tv_time.setText(listBean.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_header;
        ImageView iv_video;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
        TextView tv_like;
        TextView tv_dislike;
        TextView tv_forword;
        TextView tv_comment;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_header = (ImageView) itemView.findViewById(R.id.iv_header);
            iv_video = (ImageView) itemView.findViewById(R.id.iv_video);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_like = (TextView) itemView.findViewById(R.id.tv_like);
            tv_dislike = (TextView) itemView.findViewById(R.id.tv_dislike);
            tv_forword = (TextView) itemView.findViewById(R.id.tv_forword);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
        }
    }
}

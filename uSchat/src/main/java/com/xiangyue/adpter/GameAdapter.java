package com.xiangyue.adpter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiangyue.act.R;
import com.xiangyue.bean.LOLInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjianhua on 2017/3/31.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.Viewholder> {
    private Activity mContext;
    private List<LOLInfo> lolInfos = new ArrayList<LOLInfo>();

    public GameAdapter(Activity context) {
        this.mContext = context;
    }

    public void setData(List<LOLInfo> infos) {
        this.lolInfos = infos;
        notifyDataSetChanged();
    }

    @Override
    public GameAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(GameAdapter.Viewholder holder, int position) {
        holder.content.setText(lolInfos.get(position).getContent());
        holder.time.setText(lolInfos.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return lolInfos.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        public TextView content;
        public ImageView photo;
        public TextView time;

        public Viewholder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}

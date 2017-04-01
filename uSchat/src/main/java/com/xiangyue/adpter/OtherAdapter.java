
package com.xiangyue.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangyue.act.R;

import java.util.List;

public class OtherAdapter extends BaseAdapter {
    private final Context context;
    public List<String> channelList;
    private TextView item_text;
    /**
     * �Ƿ�ɼ�
     */
    boolean isVisible = true;
    /**
     * Ҫɾ���position
     */
    public int remove_position = -1;

    public OtherAdapter(Context context, List<String> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    @Override
    public int getCount() {
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public String getItem(int position) {
        if (channelList != null && channelList.size() != 0) {
            return channelList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.channel_item, null);
        item_text = (TextView) view.findViewById(R.id.text_item);
//        ChannelItem channel = getItem(position);
        item_text.setText(channelList.get(position));
        if (!isVisible && (position == -1 + channelList.size())) {
            item_text.setText("");
        }
        if (remove_position == position) {
            item_text.setText("");
        }
        return view;
    }

    /**
     * ��ȡƵ���б�
     */
    public List<String> getChannnelLst() {
        return channelList;
    }

    /**
     * ���Ƶ���б�
     */
    public void addItem(String channel) {
        channelList.add(channel);
        notifyDataSetChanged();
    }

    /**
     * ����ɾ���position
     */
    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
        // notifyDataSetChanged();
    }

    /**
     * ɾ��Ƶ���б�
     */
    public void remove() {
        channelList.remove(remove_position);
        remove_position = -1;
        notifyDataSetChanged();
    }

    /**
     * ����Ƶ���б�
     */
    public void setListDate(List<String> list) {
        channelList = list;
    }

    /**
     * ��ȡ�Ƿ�ɼ�
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * �����Ƿ�ɼ�
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}

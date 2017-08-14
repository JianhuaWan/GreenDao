package com.wanjianhua.budejie.pro.essence.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wanjianhua.budejie.pro.essence.view.EssenceContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ying on 2016/6/13.
 */
public class EssenceAdapter extends FragmentStatePagerAdapter {

    public static final String TAB_TAG = "@y@";

    private List<EssenceContentFragment> list;
    private List<String> listTitle;
    public EssenceAdapter(FragmentManager fm,List<EssenceContentFragment> list,List<String> listTitle) {
        super(fm);
        this.listTitle = listTitle;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        String[] split = listTitle.get(position).split(TAB_TAG);
        EssenceContentFragment fragment = list.get(position);
        fragment.setTitle(split[0]);;
        fragment.setType(Integer.parseInt(split[1]));
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position).split(TAB_TAG)[0];
    }
}

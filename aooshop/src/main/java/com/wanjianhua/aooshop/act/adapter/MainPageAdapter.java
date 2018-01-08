package com.wanjianhua.aooshop.act.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wanjianhua.aooshop.act.fragment.OrderFragment;
import com.wanjianhua.aooshop.act.fragment.PreferredFragment;
import com.wanjianhua.aooshop.act.utils.IconPagerAdapter;
import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.base.BaseApplication;
import com.wanjianhua.aooshop.act.fragment.MineFragment;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class MainPageAdapter extends FragmentPagerAdapter implements IconPagerAdapter
{
    private static final String[] TABLE_NAMES = BaseApplication.TABLE_NAMES;
    private static final int[] TAB_ICONS = new int[]{R.drawable.selector_preferred_maintab,
            R.drawable.selector_order_maintab,
            R.drawable.selector_mine_maintab};

    public MainPageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0)
    {
        // TODO Auto-generated method stub
        Fragment fragment = null;
        switch(arg0)
        {
            case 0:
                fragment = new PreferredFragment();
                break;
            case 1:
                fragment = new OrderFragment();
                break;
            case 2:
                fragment = new MineFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return TABLE_NAMES.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        // TODO Auto-generated method stub
        return TABLE_NAMES[position];
    }

    @Override
    public int getIconResId(int index)
    {
        // TODO Auto-generated method stub
        return TAB_ICONS[index];
    }

}
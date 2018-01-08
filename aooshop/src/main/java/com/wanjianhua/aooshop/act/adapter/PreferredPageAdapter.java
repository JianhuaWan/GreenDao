package com.wanjianhua.aooshop.act.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.base.BaseApplication;
import com.wanjianhua.aooshop.act.fragment.MineFragment;
import com.wanjianhua.aooshop.act.fragment.OrderFragment;
import com.wanjianhua.aooshop.act.fragment.PreferredChildFragment;
import com.wanjianhua.aooshop.act.fragment.PreferredFragment;
import com.wanjianhua.aooshop.act.utils.IconPagerAdapter;
import com.wanjianhua.aooshop.act.utils.IconPagerAdapterCopy;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class PreferredPageAdapter extends FragmentPagerAdapter implements IconPagerAdapter
{
    private static final String[] TABLE_NAMES = BaseApplication.PREFERREDMAIN;
    private static final int[] TAB_ICONS = new int[]{R.drawable.selector_preferred_maintab,
            R.drawable.selector_order_maintab,
            R.drawable.selector_mine_maintab,
            R.drawable.selector_preferred_maintab,
            R.drawable.selector_order_maintab,
            R.drawable.selector_mine_maintab};

    public PreferredPageAdapter(FragmentManager fm)
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
                fragment = new PreferredChildFragment();
                break;
            case 1:
                fragment = new PreferredChildFragment();
                break;
            case 2:
                fragment = new PreferredChildFragment();
                break;
            case 3:
                fragment = new PreferredChildFragment();
                break;
            case 4:
                fragment = new PreferredChildFragment();
                break;
            case 5:
                fragment = new PreferredChildFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getIconResId(int index)
    {
        return TAB_ICONS[index];
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

}
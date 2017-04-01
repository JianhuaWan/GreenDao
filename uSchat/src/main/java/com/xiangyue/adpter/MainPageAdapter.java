package com.xiangyue.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiangyue.act.R;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.fragment.CircleFragment;
import com.xiangyue.fragment.HMeFragment;
import com.xiangyue.fragment.HchatFragment;
import com.xiangyue.fragment.HshowtopFragment;
import com.xiangyue.tabpage.IconPagerAdapter;

public class MainPageAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    private static final String[] TABLE_NAMES = BaseApplication.TABLE_NAMES;
    private static final int[] TAB_ICONS = new int[]{R.drawable.selector_oa_maintab,
            R.drawable.selector_setting_maintab, R.drawable.selector_crm_maintab, R.drawable.selector_user_maintab,};

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        Fragment fragment = null;
        switch (arg0) {
            case 0:
//                fragment = new MoneyFragment();
                fragment = new HshowtopFragment();
                break;
            case 1:
                fragment = new HchatFragment();
                break;
            case 2:
//                fragment = new StudyFragment();
                fragment = new CircleFragment();
                break;

            case 3:
                fragment = new HMeFragment();
                break;

            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TABLE_NAMES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        return TABLE_NAMES[position];
    }

    @Override
    public int getIconResId(int index) {
        // TODO Auto-generated method stub
        return TAB_ICONS[index];
    }

}

package com.xiangyue.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiangyue.act.R;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.fragment.CFFragment;
import com.xiangyue.fragment.DNFFragment;
import com.xiangyue.fragment.LOLFragment;
import com.xiangyue.fragment.LUSFragment;
import com.xiangyue.tabpage.IconPagerAdapter;

public class GamePageAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    private static final String[] TABLE_NAMES = BaseApplication.TABLE_GAMES;
//    private static final int[] TAB_ICONS = new int[]{R.drawable.selector_oa_maintab,
//            R.drawable.selector_setting_maintab, R.drawable.selector_crm_maintab, R.drawable.selector_user_maintab,};

    public GamePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        Fragment fragment = null;
        switch (arg0) {
            case 0:
                fragment = new LOLFragment();
                break;
            case 1:
                fragment = new LUSFragment();
                break;
            case 2:
                fragment = new CFFragment();
                break;

            case 3:
                fragment = new DNFFragment();
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
//        return TAB_ICONS[index];
        return 0;
    }

}

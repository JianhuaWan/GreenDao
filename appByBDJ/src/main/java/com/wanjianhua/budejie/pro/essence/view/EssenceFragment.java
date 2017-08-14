package com.wanjianhua.budejie.pro.essence.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wanjianhua.budejie.R;
import com.wanjianhua.budejie.pro.base.view.BaseFragment;
import com.wanjianhua.budejie.pro.essence.view.adapter.EssenceAdapter;
import com.wanjianhua.budejie.pro.essence.view.navigation.EssenceNavigation;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ying on 2016/6/12.
 */
public class EssenceFragment extends BaseFragment  {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<EssenceContentFragment> fragmentLists;

    @Override
    protected void initView(View view) {
        initToolBar(view);
        tabLayout = (TabLayout)  view.findViewById(R.id.tab_essence);
        viewPager = (ViewPager) view.findViewById(R.id.vp_essence);
        String[] titles = getResources().getStringArray(R.array.essence_video_tab);
        fragmentLists = new ArrayList<>();
        fragmentLists.add(new EssenceAllFragment());
        fragmentLists.add(new EssenceVideoFragment());
        fragmentLists.add(new EssenceSoundFragment());
        fragmentLists.add(new EssenceImageFragment());
        fragmentLists.add(new EssenceJokeFragment());
        fragmentLists.add(new EssenceJokeFragment());
        fragmentLists.add(new EssencePlotFragment());
        EssenceAdapter essenceAdapter = new EssenceAdapter(getFragmentManager(), fragmentLists, Arrays.asList(titles));
        viewPager.setAdapter(essenceAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }

    private void initToolBar(View view) {
        EssenceNavigation essenceNavigation = new EssenceNavigation(getContext());
        essenceNavigation.setTitleIcon(R.mipmap.main_essence_title)
                .setLeftIcon(R.drawable.main_essence_btn_selector)
                .setRightIcon(R.drawable.main_essence_btn_more_selector)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "左边被点击", Toast.LENGTH_SHORT).show();
                    }
                })
                .setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "右边被点击", Toast.LENGTH_SHORT).show();
                    }
                });
        essenceNavigation.createAndBind((ViewGroup) view);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_essence;
    }


}

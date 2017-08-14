package com.wanjianhua.budejie.pro.essence.view;

import android.util.Log;
import android.view.View;

import com.wanjianhua.budejie.R;

/**
 * Created by machenike on 2016/5/29.
 * 视频
 */
public class EssenceVideoFragment extends EssenceContentFragment {
    @Override
    protected void initView(View view) {
        Log.e("Content", getTitle() + "  type:" + getType());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_essence_video;
    }
}

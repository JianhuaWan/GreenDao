package com.xiangyue.act;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.im.util.ViewUtil;
import com.xiangyue.base.BaseActivity;

/**
 * Created by wWX321637 on 2016/5/12.
 */
public class QueryHeadActivity extends BaseActivity {
    private ViewPager viewpager;
    private ImagePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.queryhead_main);
        initView();
    }


    private String photos = null;

    public void initView() {
        photos = getIntent().getStringExtra("headIcon");
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new ImagePagerAdapter(photos));
        viewpager.setCurrentItem(getIntent().getIntExtra("pos", 0));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private class ImagePagerAdapter extends PagerAdapter {
        public String images;
        private LayoutInflater inflater;

        ImagePagerAdapter(String images) {
            this.images = images;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {

            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            // assert imageLayout != null;
            imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
//            Picasso.with(QueryHeadActivity.this).load(images).placeholder(R.drawable.medium_showgirl)
//                    .error(R.drawable.medium_showgirl).into(imageView);
            ViewUtil.setPicture(QueryHeadActivity.this, images, R.drawable.default_head, imageView, null);
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    private ImageView imageView;


}

package com.xiangyue.act;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.im.util.ViewUtil;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.image.ImageDisplayer;
import com.xiangyue.image.ImageItem;
import com.xiangyue.provider.BusProvider;

/**
 * Created by wWX321637 on 2016/5/11.
 */
public class SinglePhotoActivity extends BaseActivity {


    private ViewPager viewpager;
    private ImagePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        BusProvider.getInstance().register(this);
        setContentView(R.layout.singlephoto_main);
        initView();
    }

    public void back(View view) {
        finish();
    }

    private ImageItem photo;

    public void initView() {
        photo = (ImageItem) getIntent().getSerializableExtra("imageItem");
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new ImagePagerAdapter(photo));
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
        public ImageItem images;
        private LayoutInflater inflater;

        ImagePagerAdapter(ImageItem images) {
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
//            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
            //发送的不是远程图片地址，则取本地地址
            ImageDisplayer.getInstance(SinglePhotoActivity.this).displayBmp(imageView, images.thumbnailPath, images.sourcePath);
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

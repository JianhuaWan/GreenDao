package com.xiangyue.act;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.bean.PhotoUrl;
import com.xiangyue.bean.Photos;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.provider.SetPhotoBgRefreshEvent;
import com.xiangyue.view.ActionSheetDialog;
import com.xiangyue.weight.LoginDialog;

import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;

public class PhotoDetailActivity extends BaseActivity implements OnClickListener {
    private ViewPager viewpager;
    private ImagePagerAdapter adapter;
    private TextView tv_count;
    private TextView img_del_photo;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        BusProvider.getInstance().register(this);
        setContentView(R.layout.photo_del_main);
        initView();
    }

    public void back(View view) {
        setResult(RESULT_OK);
        finish();
    }

    private ArrayList<PhotoUrl> photos = new ArrayList<PhotoUrl>();
    private int pos, temppos;

    public void initView() {
        dialog = new LoginDialog(PhotoDetailActivity.this, "");
        dialog.setCanceledOnTouchOutside(false);
        img_del_photo = (TextView) findViewById(R.id.img_del_photo);
        img_del_photo.setOnClickListener(this);
        if (getIntent().getStringExtra("flag").equals("other")) {
            img_del_photo.setVisibility(View.GONE);
        }
        pos = getIntent().getIntExtra("pos", 0) + 1;
        temppos = getIntent().getIntExtra("pos", 0);
        photos = (ArrayList<PhotoUrl>) getIntent().getSerializableExtra("photos");
        tv_count = (TextView) findViewById(R.id.tv_count);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new ImagePagerAdapter(photos));
        viewpager.setCurrentItem(getIntent().getIntExtra("pos", 0));
        tv_count.setText("相册(" + pos + "/" + photos.size() + ")");
        viewpager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                int overarg = arg0 + 1;
                temppos = arg0;
                tv_count.setText("相册(" + overarg + "/" + photos.size() + ")");
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
        public ArrayList<PhotoUrl> images;
        private LayoutInflater inflater;

        ImagePagerAdapter(ArrayList<PhotoUrl> images) {
            this.images = images;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {

            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            // assert imageLayout != null;
            imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
//            Picasso.with(PhotoDetailActivity.this).load(images.get(position).getShowurl()).placeholder(R.drawable.medium_showgirl)
//                    .error(R.drawable.medium_showgirl).into(imageView);
            ViewUtil.setPicture(PhotoDetailActivity.this, images.get(position).getShowurl(), R.drawable.default_head, imageView, null);
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

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.img_del_photo:

                new ActionSheetDialog(PhotoDetailActivity.this).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("删除", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            public void onClick(int which) {
                                del();
                            }
                        })
//                        .addSheetItem("设为个人背景", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
//                    public void onClick(int which) {
//                        setBg();
//        }
//                })
                        .show();


                break;

            default:
                break;
        }

    }

    LoginDialog dialog;

    private void del() {
        // 删除
        dialog.show();
        BmobFile bmobFile = new BmobFile();
        bmobFile.setUrl(photos.get(temppos).getDelurl());
        bmobFile.delete(PhotoDetailActivity.this, new DeleteListener() {
            @Override
            public void onSuccess() {

                Photos bean = new Photos();
                bean.delete(PhotoDetailActivity.this, photos.get(temppos).getObjectId(), new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                        photos.remove(temppos);
                        viewpager.setAdapter(new ImagePagerAdapter(photos));
                        // photos.get(pos);删除的url
                        if (temppos == 1) {
                            tv_count.setText("相册(" + 1 + "/" + photos.size() + ")");
                            viewpager.setCurrentItem(0);
                            temppos = 0;
                        } else if (temppos == 0) {
                            tv_count.setText("相册(" + 0 + "/" + photos.size() + ")");
                            if (photos.size() > 0) {
                                viewpager.setCurrentItem(temppos);
                                tv_count.setText("相册(" + 1 + "/" + photos.size() + ")");
                            } else {
                                viewpager.setVisibility(View.GONE);
                                img_del_photo.setClickable(false);
                            }

                        } else {
                            viewpager.setCurrentItem(temppos);
                        }
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        dialog.dismiss();
                        Toast.makeText(PhotoDetailActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onFailure(int i, String s) {
                dialog.dismiss();
                Toast.makeText(PhotoDetailActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setBg() {
        Toast.makeText(PhotoDetailActivity.this, "successful", Toast.LENGTH_LONG).show();
        BusProvider.getInstance().post(new SetPhotoBgRefreshEvent(photos.get(temppos).getShowurl()));
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}

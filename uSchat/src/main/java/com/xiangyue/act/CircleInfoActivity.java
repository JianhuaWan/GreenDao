package com.xiangyue.act;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.im.util.ViewUtil;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;

import java.util.ArrayList;

/**
 * Created by wWX321637 on 2016/5/10.
 */
public class CircleInfoActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewpager;
    private ImagePagerAdapter adapter;
    private TextView tv_detial;
    private LinearLayout linear_bottom;
    private ImageView img_more;
    private TextView tv_title;
    private String objectId;
    private String username;
    private MicroRecruitSettings settings;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.circle_info);
        initTools();
        initView();
    }

    private void initTools() {
        settings = new MicroRecruitSettings(CircleInfoActivity.this);
    }


    public void back(View view) {
        finish();
    }

    private ArrayList<String> photos = new ArrayList<String>();
    private int pos, temppos;

    public void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getIntent().getStringExtra("title"));
        img_more = (ImageView) findViewById(R.id.img_more);
        img_more.setOnClickListener(this);
        linear_bottom = (LinearLayout) findViewById(R.id.linear_bottom);
        pos = getIntent().getIntExtra("pos", 0) + 1;
        temppos = getIntent().getIntExtra("pos", 0);
        photos = (ArrayList) getIntent().getStringArrayListExtra("circlebean");
        tv_detial = (TextView) findViewById(R.id.tv_detial);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new ImagePagerAdapter(photos));
        viewpager.setCurrentItem(getIntent().getIntExtra("pos", 0));
        tv_detial.setText("详情(" + pos + "/" + photos.size() + ")");
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                int overarg = arg0 + 1;
                temppos = arg0;
                tv_detial.setText("详情(" + overarg + "/" + photos.size() + ")");
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
        objectId = getIntent().getStringExtra("objectId");
        username = getIntent().getStringExtra("username");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_more:
//                new ActionSheetDialog(CircleInfoActivity.this).builder().setCancelable(false)
//                        .setCanceledOnTouchOutside(false)
//                        .addSheetItem("点赞", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
//                            public void onClick(int which) {
//                                //点赞
////                                Toast.makeText(CircleInfoActivity.this, "点赞", Toast.LENGTH_LONG).show();
//                                BmobQuery<CircleBean> circleBeanBmobQuery = new BmobQuery<CircleBean>();
//                                circleBeanBmobQuery.addWhereEqualTo("uuid", getIntent().getStringExtra("uuid"));
//                                circleBeanBmobQuery.findObjects(CircleInfoActivity.this, new FindListener<CircleBean>() {
//                                    @Override
//                                    public void onSuccess(List<CircleBean> list) {
//                                        if (list.get(0).getLaudpeoson() == null) {
//                                            List<String> laud = new ArrayList<String>();
//                                            laud.add(settings.phone.getValue());
//                                            CircleBean circleBean = new CircleBean();
//                                            circleBean.setLaudcount("1");
//                                            circleBean.setLaudpeoson(laud);
//                                            circleBean.update(CircleInfoActivity.this, getIntent().getStringExtra("cirObjectId"), new UpdateListener() {
//                                                @Override
//                                                public void onSuccess() {
//                                                    Toast.makeText(CircleInfoActivity.this, "点赞成功!", Toast.LENGTH_LONG).show();
//                                                }
//
//                                                @Override
//                                                public void onFailure(int i, String s) {
//                                                    Toast.makeText(CircleInfoActivity.this, s, Toast.LENGTH_LONG).show();
//                                                }
//                                            });
//
//                                        } else if (list.get(0).getLaudpeoson().contains(settings.phone.getValue())) {
//                                            Toast.makeText(CircleInfoActivity.this, "已赞", Toast.LENGTH_LONG).show();
//                                        } else {
//                                            List<String> laud = new ArrayList<String>();
//                                            laud = list.get(0).getLaudpeoson();
//                                            laud.add(settings.phone.getValue());
//                                            CircleBean circleBean = new CircleBean();
//                                            circleBean.setLaudcount(laud.size() + "");
//                                            circleBean.setPhotos(list.get(0).getPhotos());
//                                            circleBean.setLaudpeoson(laud);
//                                            circleBean.update(CircleInfoActivity.this, getIntent().getStringExtra("cirObjectId"), new UpdateListener() {
//                                                @Override
//                                                public void onSuccess() {
//                                                    Toast.makeText(CircleInfoActivity.this, "点赞成功!", Toast.LENGTH_LONG).show();
//                                                }
//
//                                                @Override
//                                                public void onFailure(int i, String s) {
//                                                    Toast.makeText(CircleInfoActivity.this, s, Toast.LENGTH_LONG).show();
//                                                }
//                                            });
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(int i, String s) {
//                                        Toast.makeText(CircleInfoActivity.this, s, Toast.LENGTH_LONG).show();
//                                    }
//                                });
//
//                            }
//                        }).addSheetItem("评论详情", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
//                    public void onClick(int which) {
//                        Intent intent = new Intent();
//                        intent.putExtra("uuid", getIntent().getStringExtra("uuid"));
//                        intent.setClass(CircleInfoActivity.this, CommontActivity.class);
//                        startActivity(intent);
//                    }
//                }).addSheetItem("ta的资料", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
//                    public void onClick(int which) {
//                        Intent intent = new Intent();
//                        intent.setClass(CircleInfoActivity.this, OtherInfoDetailActivity.class);
//                        intent.putExtra("userid", username);
//                        intent.putExtra("objectId", objectId);
//                        startActivity(intent);
//                    }
//                }).show();
                break;

            default:
                break;
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {
        public ArrayList<String> images;
        private LayoutInflater inflater;

        ImagePagerAdapter(ArrayList<String> images) {
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
//            Picasso.with(CircleInfoActivity.this).load(images.get(position)).placeholder(R.drawable.medium_showgirl)
//                    .error(R.drawable.medium_showgirl).into(imageView);
            ViewUtil.setPicture(CircleInfoActivity.this, images.get(position), R.drawable.default_head, imageView, null);
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
//    private int temp = 0;
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        if (temp == 0) {
//            linear_bottom.setVisibility(View.GONE);
//            temp = 1;
//        } else {
//            linear_bottom.setVisibility(View.GONE);
//            temp = 0;
//        }
//        return true;
//    }
}

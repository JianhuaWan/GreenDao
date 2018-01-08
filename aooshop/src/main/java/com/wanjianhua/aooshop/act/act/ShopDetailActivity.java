package com.wanjianhua.aooshop.act.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.base.BaseActivity;
import com.wanjianhua.aooshop.act.utils.GlideImageLoader;
import com.wanjianhua.aooshop.act.utils.popwindow.PopuwindowUtils;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 6005001713 on 2017/12/18.
 */

public class ShopDetailActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tv_shoptitle)
    TextView tvShoptitle;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.lienar_center)
    LinearLayout lienarCenter;
    @Bind(R.id.view_center)
    View viewCenter;
    @Bind(R.id.tv_buttom)
    TextView tvButtom;
    @Bind(R.id.linear_buttom)
    LinearLayout linearButtom;
    private List<String> images = new ArrayList<>();
    private TextView tv_nowbuy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        setListen();
    }

    private void initView()
    {
        tv_nowbuy = (TextView) findViewById(R.id.tv_nowbuy);
        tv_nowbuy.setOnClickListener(this);
    }

    private void setListen()
    {
    }

    private void initData()
    {
        images.add("https://www.bmob.cn/uploads/attached/img/20170720/4.jpg");
        images.add("https://www.bmob.cn/uploads/attached/img/20170831/1108.jpg");
        images.add("https://www.bmob.cn/uploads/attached/img/20170720/2.jpg");
        images.add("https://www.bmob.cn/uploads/attached/img/20170720/3.jpg");
        initBanner();
    }

    private void initBanner()
    {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    @OnClick(R.id.img_back)
    public void onViewClicked()
    {
        finish();
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.tv_nowbuy:
                PopuwindowUtils popuwindowUtils = new PopuwindowUtils(ShopDetailActivity.this, v, ShopDetailActivity.this);
                //showpopuwindow
//                Intent intent=new Intent();
//                intent.setClass(ShopDetailActivity.this,Ch)
                break;
            default:
                break;
        }
    }
}

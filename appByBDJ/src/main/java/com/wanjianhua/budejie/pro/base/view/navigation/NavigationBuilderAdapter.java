package com.wanjianhua.budejie.pro.base.view.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by machenike on 2016/5/29.
 */
public abstract class NavigationBuilderAdapter implements NavigationBuilder {
    private Context mContext;
    private String mTitle;
    private int mLeftIconRes;
    private int mRightIconRes;
    private int mTitleIconRes;

    private View contentView;

    private View.OnClickListener leftIconOnClickListener;
    private View.OnClickListener rightIconOnClickListener;
    private int lyoutId;

    public NavigationBuilderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public NavigationBuilder setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    @Override
    public NavigationBuilder setTitle(int title) {
        this.mTitle = getContext().getResources().getString(title);
        return this;
    }

    @Override
    public NavigationBuilder setTitleIcon(int iconRes) {
        this.mTitleIconRes = iconRes;
        return this;
    }

    @Override
    public NavigationBuilder setLeftIcon(int iconRes) {
        this.mLeftIconRes = iconRes;
        return this;
    }

    @Override
    public NavigationBuilder setRightIcon(int iconRes) {
        this.mRightIconRes = iconRes;
        return this;
    }

    @Override
    public NavigationBuilder setLeftIconOnClickListener(View.OnClickListener onClickListener) {
        this.leftIconOnClickListener = onClickListener;
        return this;
    }

    @Override
    public NavigationBuilder setRightIconOnClickListener(View.OnClickListener onClickListener) {
        this.rightIconOnClickListener = onClickListener;
        return this;
    }

    @Override
    public void createAndBind(ViewGroup parent) {
        contentView = LayoutInflater.from(getContext()).inflate(getLyoutId(),parent,false);
        ViewGroup parent1 = (ViewGroup) contentView.getParent();
        if(parent1!=null){
            parent1.removeView(contentView);
        }
        parent.addView(contentView,0);
        int leftId = getLeftId();
        if(leftId != 0){
            View view = findViewById(leftId);
            view.setOnClickListener(leftIconOnClickListener);
            if(view instanceof ImageView){
                ((ImageView) view).setImageResource(mLeftIconRes);
            }
        }
        int rightId = getRightId();
        if(rightId != 0){
            View view = findViewById(rightId);
            view.setOnClickListener(rightIconOnClickListener);
            if(view instanceof ImageView){
                ((ImageView) view).setImageResource(mRightIconRes);
            }
        }
        int titleId = getTitleId();
        if(titleId != 0){
            View view = findViewById(titleId);
            if(view instanceof ImageView){
                ((ImageView) view).setImageResource(mTitleIconRes);
            }
        }


    }
    public int getLeftId(){
        return 0;
    }
    public int getRightId(){
        return 0;
    }
    public int getTitleId(){
        return 0;
    }

    public abstract int getLyoutId() ;



    public View getContentView() {
        return contentView;
    }

    public View findViewById(int id){
        return getContentView().findViewById(id);
    }

    public String getTitle() {
        return mTitle;
    }

    public int getLeftIconRes() {
        return mLeftIconRes;
    }

    public int getRightIconRes() {
        return mRightIconRes;
    }

    public int getTitleIconRes() {
        return mTitleIconRes;
    }

    public View.OnClickListener getLeftIconOnClickListener() {
        return leftIconOnClickListener;
    }

    public View.OnClickListener getRightIconOnClickListener() {
        return rightIconOnClickListener;
    }
}

package com.wanjianhua.aooshop.act.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.base.BaseActivity;

/**
 * Created by 6005001713 on 2017/12/19.
 */

public class SuggestMainActivity extends BaseActivity implements View.OnClickListener
{

    private ImageView img_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest_main);
        initView();
    }

    private void initView()
    {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }
}

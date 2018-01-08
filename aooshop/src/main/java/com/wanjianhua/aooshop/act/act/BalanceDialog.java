package com.wanjianhua.aooshop.act.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjianhua.aooshop.act.base.BaseActivity;
import com.wanjianhua.aooshop.act.utils.RefreshBalance;
import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.utils.RxBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：wanjianhua on 2017/5/25 11:21
 * email：1243381493@qq.com
 */

public class BalanceDialog extends BaseActivity
{
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.rel_topbyme)
    RelativeLayout relTopbyme;
    @Bind(R.id.tv_balance_1)
    EditText tvBalance1;
    @Bind(R.id.tv_balance_2)
    EditText tvBalance2;
    @Bind(R.id.tv_balance_3)
    EditText tvBalance3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.tv_add})
    public void onViewClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_add:
                if(tvBalance1.getText().toString().isEmpty() || tvBalance2.getText().toString().isEmpty() || tvBalance3.getText().toString().isEmpty())
                {
                    Toast.makeText(BalanceDialog.this, getString(R.string.infonotwrite), Toast.LENGTH_LONG).show();
                    return;
                }
                RxBus.getInstance().post(new RefreshBalance(tvBalance1.getText().toString(), tvBalance2.getText().toString(), tvBalance3.getText().toString()));
                finish();
                break;
        }
    }
}

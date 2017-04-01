package com.xiangyue.act;

import com.xiangyue.base.BaseApplication;
import com.xiangyue.tusdk.CameraComponentSimple;
import com.xiangyue.tusdk.EditMultipleComponentSimple;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;
import org.lasque.tusdk.impl.activity.TuFragmentActivity;

/**
 * Created by wWX321637 on 2016/5/20.
 */
public class BlackActivity extends TuFragmentActivity {
    @Override
    protected void initActivity() {
        super.initActivity();
    }

    @Override
    protected void initView() {
        super.initView();
        TuSdk.messageHub().dismissRightNow();
        TuSdk.checkFilterManager(mFilterManagerDelegate);
        if (getIntent().getStringExtra("type").equals("1")) {
            showCameraComponent();
            finish();
        } else if (getIntent().getStringExtra("type").equals("2")) {
            showEditorComponent();
            finish();
        }
    }

    /**
     * 打开相机组件
     */
    private void showCameraComponent() {
        BaseApplication.getInstance().setPhotoorheadIcon("4");
        new CameraComponentSimple().showSimple(this);
    }

    /**
     * 打开多功能编辑组件
     */
    private void showEditorComponent() {
        BaseApplication.getInstance().setPhotoorheadIcon("4");
        new EditMultipleComponentSimple().showSimple(this);
    }


    /**
     * 滤镜管理器委托
     */
    private FilterManager.FilterManagerDelegate mFilterManagerDelegate = new FilterManager.FilterManagerDelegate() {
        @Override
        public void onFilterManagerInited(FilterManager manager) {
            // TuSdk.messageHub().showSuccess(MeAccountActivity.this,
            // R.string.lsq_inited);
            TuSdk.messageHub().dismissRightNow();// 去掉提示
        }
    };
}

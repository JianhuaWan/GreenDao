package com.wanjianhua.budejie.pro.essence.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.wanjianhua.budejie.bean.EssecneListBean;
import com.wanjianhua.budejie.http.utils.HttpTask;
import com.wanjianhua.budejie.pro.base.presenter.BasePresenter;
import com.wanjianhua.budejie.pro.essence.model.EssenceAllModel;
import com.wanjianhua.budejie.pro.essence.view.IEssenceAllView;

/**
 * Created by ying on 2016/6/15.
 */
public class EssenceAllPresenter extends BasePresenter<IEssenceAllView, EssenceAllModel> {

    private String maxTime;

    public EssenceAllPresenter(Context context) {
        super(context);

    }

    public void getAllEssence(int type, final boolean isRefresh) {
        if (isRefresh) {
            maxTime = "";
        }
        getView().showDialog();
        getModel().getAllEssence(maxTime, type, new HttpTask.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                if (isDestroy()) {
                    return;
                }
                Log.d(getClass().getSimpleName(), result);
                getView().hideDialog();

                if (TextUtils.isEmpty(result)) {
                    getView().error(new Exception("网络异常，稍后再试"));
                    return;
                }
                EssecneListBean bean = JSONObject.parseObject(result, EssecneListBean.class);
                maxTime = bean.getInfo().getMaxtime();
                getView().loadData(bean.getList(), isRefresh);
            }
        });
    }

    @Override
    public EssenceAllModel bindModel() {
        return new EssenceAllModel(getContext());
    }
}

package com.wanjianhua.budejie.pro.essence.model;

import android.content.Context;
import android.text.TextUtils;

import com.wanjianhua.budejie.http.impl.HttpRequestParam;
import com.wanjianhua.budejie.http.utils.HttpTask;
import com.wanjianhua.budejie.pro.base.model.BaseModel;

/**
 * Created by ying on 2016/6/15.
 */
public class EssenceAllModel extends BaseModel {
    private String getUrl() {
        return super.getServerUrl() + "/api/api_open.php";
    }

    public EssenceAllModel(Context context) {
        super(context);
    }

    public void getAllEssence(String maxtime, int type, HttpTask.OnHttpResultListener callback) {
        HttpRequestParam param = new HttpRequestParam();
        param.put("a", "list");
        param.put("c", "data");
        if (!TextUtils.isEmpty(maxtime)) {
            param.put("maxtime", maxtime);
        }
        if (type != -1) {
            param.put("type", type);
        }
        HttpTask.requestPost(getUrl(), param, callback);
    }
}

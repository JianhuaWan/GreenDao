/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiangyue.act;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.im.util.ViewUtil;
import com.xiangyue.base.BaseActivity;

import java.io.File;

/**
 * 下载显示大图
 */
public class ShowBigImage extends BaseActivity {

    private ProgressDialog pd;
    private ImageView image;
    private int default_res = R.drawable.default_image;
    private String localFilePath;
    private Bitmap bitmap;
    private boolean isDownloaded;
    private ProgressBar loadLocalPb;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_show_big_image);
        super.onCreate(savedInstanceState);

        image = (ImageView) findViewById(R.id.image);
        loadLocalPb = (ProgressBar) findViewById(R.id.pb_load_local);
        String uri = getIntent().getStringExtra("uri");
        String remotepath = getIntent().getExtras().getString("remotepath");
        if (TextUtils.isEmpty(remotepath)) {
//            Picasso.with(ShowBigImage.this).load(uri).error(R.drawable.default_image).placeholder(R.drawable.default_image).into(image);
            ViewUtil.setPicture(ShowBigImage.this, uri, R.drawable.default_head, image, null);
        } else {

//            Picasso.with(ShowBigImage.this).load(getDownFilePath(remotepath)).error(R.drawable.default_image).placeholder(R.drawable.default_image).into(image);
            ViewUtil.setPicture(ShowBigImage.this, remotepath, R.drawable.default_head, image, null);
        }
    }

    public static File getDownFilePath(String remotepath) {
        String dirName = "";
        // 获取SD卡
//
//        ;
//        dirName = Environment.getExternalStorageDirectory().getPath() + "/xiangyue/";
//        File f = new File(dirName);
//        if (!f.exists()) {
//            f.mkdir();
//        }
        File overpath = new File(remotepath.replace("file:////", ""));
        return overpath;
    }
}

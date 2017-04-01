package com.xiangyue.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiangyue.adpter.ImageBucketAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.image.ImageBucket;
import com.xiangyue.image.ImageFetcher;
import com.xiangyue.image.ImageItem;
import com.xiangyue.image.IntentConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wWX321637 on 2016/5/10.
 */
public class ImageChooseActivity extends BaseActivity {
    private ImageFetcher mHelper;
    private List<ImageItem> selectedImgs = new ArrayList<ImageItem>();
    private List<ImageBucket> mDataList = new ArrayList<ImageBucket>();
    private ListView mListView;
    private ImageBucketAdapter mAdapter;
    private int availableSize;
    private Context mContext;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_image_bucket_choose);
        mContext = this;
        mHelper = ImageFetcher.getInstance(mContext);
        initData();
        initView();
    }


    private void initData() {
        mDataList = mHelper.getImagesBucketList(false);
        Collections.sort(mDataList);
        availableSize = getIntent().getIntExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
                IntentConstants.MAX_IMAGE_SIZE);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new ImageBucketAdapter(this, mDataList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectOne(position);

                Intent intent = new Intent();
                intent.setClassName(mContext.getPackageName(), ImageChooseActivityOver.class.getName());
                intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) mDataList.get(position).imageList);
                intent.putExtra(IntentConstants.EXTRA_BUCKET_NAME, mDataList.get(position).bucketName);
                intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, availableSize);

                startActivityForResult(intent, 200);
            }
        });

    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && null != data) {
            selectedImgs = (List<ImageItem>) data.getSerializableExtra("selectedImgs");
            Intent intent = new Intent(ImageChooseActivity.this, WriteCircleActivity.class);
            intent.putExtra("selectedImgs", (Serializable) selectedImgs);
            ImageChooseActivity.this.setResult(255, intent);
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void selectOne(int position) {
        int size = mDataList.size();
        for (int i = 0; i != size; i++) {
            if (i == position)
                mDataList.get(i).selected = true;
            else {
                mDataList.get(i).selected = false;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void back(View view) {
        finish();
    }


}

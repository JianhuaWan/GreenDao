package com.xiangyue.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.adpter.ImageGridAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.image.ImageItem;
import com.xiangyue.image.IntentConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 图片选择
 */
public class ImageChooseActivityOver extends BaseActivity {
    private List<ImageItem> mDataList = new ArrayList<ImageItem>();
    private String mBucketName;
    private int availableSize;
    private GridView mGridView;
    private TextView mBucketNameTv;
    private ImageGridAdapter mAdapter;
    private Button mFinishBtn;
    private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();
    private List<ImageItem> selectedData = new ArrayList<ImageItem>();
    private Context context;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_image_choose);
        context = this;
        mDataList = (List<ImageItem>) getIntent().getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
        if (mDataList == null) {
            mDataList = new ArrayList<ImageItem>();
        }
        mBucketName = getIntent().getStringExtra(IntentConstants.EXTRA_BUCKET_NAME);

        if (TextUtils.isEmpty(mBucketName)) {
            mBucketName = context.getResources().getString(R.string.sub_selected);
        }
        availableSize = getIntent().getIntExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
                IntentConstants.MAX_IMAGE_SIZE);

        initView();
        initListener();
    }


    private void initView() {

        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImageGridAdapter(ImageChooseActivityOver.this, mDataList);
        mGridView.setAdapter(mAdapter);
        mFinishBtn = (Button) findViewById(R.id.finish_btn);

        mFinishBtn.setText(context.getResources().getString(R.string.sub_finish) + "("
                + selectedImgs.size() + "/" + availableSize + ")");
        mAdapter.notifyDataSetChanged();
    }

    protected static final String TAG = "PublishActivity";

    public void back(View view) {
        finish();
    }

    private void initListener() {
        mFinishBtn.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                Intent intent = new Intent(ImageChooseActivityOver.this, ImageChooseActivity.class);
                selectedData = (List<ImageItem>) (Serializable) new ArrayList<ImageItem>(selectedImgs.values());
                intent.putExtra("selectedImgs", (Serializable) selectedData);
                ImageChooseActivityOver.this.setResult(RESULT_OK, intent);
                finish();
            }
        });

        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageItem item = mDataList.get(position);
                if (item.isSelected) {
                    item.isSelected = false;
                    selectedImgs.remove(item.imageId);
                } else {
                    if (selectedImgs.size() >= availableSize) {
                        Toast.makeText(
                                ImageChooseActivityOver.this,
                                context.getResources().getString(R.string.sub_more_select)
                                        + availableSize
                                        + context.getResources()
                                        .getString(R.string.sub_pictures), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    item.isSelected = true;
                    selectedImgs.put(item.imageId, item);
                }

                mFinishBtn.setText(context.getResources().getString(R.string.sub_finish) + "("
                        + selectedImgs.size() + "/" + availableSize + ")");
                mAdapter.notifyDataSetChanged();
            }

        });


    }
}
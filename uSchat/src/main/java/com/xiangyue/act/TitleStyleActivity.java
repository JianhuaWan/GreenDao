package com.xiangyue.act;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.StyleBeanByStar;
import com.xiangyue.type.User;
import com.xiangyue.weight.LoginDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.UpdateListener;

/**
 * 标签选择
 *
 * @author Administrator
 */
public class TitleStyleActivity extends BaseActivity implements View.OnClickListener {
    private GridView style_gridv;
    private List<StyleBeanByStar> stylelist = new ArrayList<StyleBeanByStar>();
    private List<String> stylelistaddordel = new ArrayList<String>();
    private Context context;
    private LabelAdapter adapter;
    private TextView tv_save;
    private MicroRecruitSettings settings;


    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.title_style_main);
        context = this;
        settings = new MicroRecruitSettings(context);
        initViews();
        setUpData();
    }

    private String starintent;
    StyleBeanByStar StyleBeanByStar;

    private void setUpData() {

        starintent = getIntent().getStringExtra("star");
        //添加数据
        if (starintent.equals("摩羯座")) {
            StyleBeanByStar = new StyleBeanByStar("摩羯座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar StyleBeanByStar = new StyleBeanByStar("摩羯座", false);
            stylelist.add(StyleBeanByStar);
        }
        if (starintent.equals("水瓶座")) {
            StyleBeanByStar = new StyleBeanByStar("水瓶座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("水瓶座", false);
            stylelist.add(StyleBeanByStar);

        }
        if (starintent.equals("双鱼座")) {
            StyleBeanByStar = new StyleBeanByStar("双鱼座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("双鱼座", false);
            stylelist.add(StyleBeanByStar);

        }
        if (starintent.equals("白羊座")) {
            StyleBeanByStar = new StyleBeanByStar("白羊座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("白羊座", false);
            stylelist.add(StyleBeanByStar);

        }

        if (starintent.equals("金牛座")) {
            StyleBeanByStar = new StyleBeanByStar("金牛座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("金牛座", false);
            stylelist.add(StyleBeanByStar);

        }

        if (starintent.equals("双子座")) {
            StyleBeanByStar = new StyleBeanByStar("双子座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("双子座", false);
            stylelist.add(StyleBeanByStar);

        }
        if (starintent.equals("巨蟹座")) {
            StyleBeanByStar = new StyleBeanByStar("巨蟹座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("巨蟹座", false);
            stylelist.add(StyleBeanByStar);

        }
        if (starintent.equals("狮子座")) {
            StyleBeanByStar = new StyleBeanByStar("狮子座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("狮子座", false);
            stylelist.add(StyleBeanByStar);

        }
        if (starintent.equals("处女座")) {
            StyleBeanByStar = new StyleBeanByStar("处女座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("处女座", false);
            stylelist.add(StyleBeanByStar);

        }
        if (starintent.equals("天秤座")) {
            StyleBeanByStar = new StyleBeanByStar("天秤座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("天秤座", false);
            stylelist.add(StyleBeanByStar);

        }
        if (starintent.equals("天蝎座")) {
            StyleBeanByStar = new StyleBeanByStar("天蝎座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("天蝎座", false);
            stylelist.add(StyleBeanByStar);

        }
        if (starintent.equals("射手座")) {
            StyleBeanByStar = new StyleBeanByStar("射手座", true);
            stylelist.add(StyleBeanByStar);
        } else {
            StyleBeanByStar = new StyleBeanByStar("射手座", false);
            stylelist.add(StyleBeanByStar);

        }
        adapter = new LabelAdapter();
        style_gridv.setAdapter(adapter);
        style_gridv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkLabelHandler((TextView) view, stylelist.get(position).getName(), position);
            }
        });
    }

    public void checkLabelHandler(TextView checkedTextView, String labelindu, int position) {
        boolean checked = stylelist.get(position).ischeck();
        if (checked) {
            stylelist.get(position).setIscheck(false);
            checkedTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_bg));
            if (stylelistaddordel.size() > 0 && null != stylelistaddordel) {
                stylelistaddordel.remove(labelindu);
            }
        } else {
            stylelist.get(position).setIscheck(true);
            checkedTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_bg1));
            if (!stylelistaddordel.contains(labelindu)) {
                stylelistaddordel.add(labelindu);
            }
        }
    }

    private void initViews() {
        style_gridv = (GridView) findViewById(R.id.style_gridv);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        dialog = new LoginDialog(context, "");
        dialog.setCanceledOnTouchOutside(false);
    }

    public void back(View v) {
        finish();
    }

    LoginDialog dialog;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_save) {
            //提交
            if (stylelistaddordel.size() == 0) {
                Toast.makeText(TitleStyleActivity.this, "至少选择一项", Toast.LENGTH_LONG).show();
            } else if (stylelistaddordel.size() >= 2) {
                Toast.makeText(TitleStyleActivity.this, "只能选择一项", Toast.LENGTH_LONG).show();
            } else {
                //提交
                dialog.show();
                User user = new User();
                user.setStar(stylelistaddordel.get(0).toString());
                user.update(TitleStyleActivity.this, settings.OBJECT_ID.getValue().toString(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("star", stylelistaddordel.get(0).toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        dialog.dismiss();
                        Toast.makeText(TitleStyleActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                });

            }
        }
    }

    public class LabelAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stylelist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.selectlabel_item, null);

            AbsListView.LayoutParams params = new AbsListView.LayoutParams((int) (1.55 * (style_gridv.getHeight() / 8)),
                    style_gridv.getHeight() / 8);

            convertView.setLayoutParams(params);
            TextView label_item_ct = (TextView) convertView.findViewById(R.id.selectlabel_item);
            label_item_ct.setText(stylelist.get(position).getName());
            if (stylelist.get(position).ischeck()) {
                label_item_ct.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_bg1));
            } else {
                label_item_ct.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_bg));
            }

            return convertView;
        }

    }
}

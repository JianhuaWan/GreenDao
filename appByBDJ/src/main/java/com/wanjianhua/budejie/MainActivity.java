package com.wanjianhua.budejie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjianhua.budejie.pro.attention.AttentionFragment;
import com.wanjianhua.budejie.pro.essence.view.EssenceFragment;
import com.wanjianhua.budejie.pro.mine.view.MineFragment;
import com.wanjianhua.budejie.pro.newpost.view.NewpostFragment;
import com.wanjianhua.budejie.utils.Dip2px;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    private ArrayList<TabItem> tabItems;

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabData();
        initTabHost();
    }

    private void initTabData() {
        tabItems = new ArrayList<>();
        tabItems.add(new TabItem(R.drawable.main_bottom_essence_normal, R.drawable.main_bottom_essence_press,
                R.string.main_essence_text, EssenceFragment.class));
        tabItems.add(new TabItem(R.drawable.main_bottom_newpost_normal, R.drawable.main_bottom_newpost_press,
                R.string.main_newpost_text, NewpostFragment.class));
        tabItems.add(new TabItem(R.drawable.main_bottom_public_normal, R.drawable.main_bottom_public_press,
                0, null));
        tabItems.add(new TabItem(R.drawable.main_bottom_attention_normal, R.drawable.main_bottom_attention_press,
                R.string.main_attention_text, AttentionFragment.class));
        tabItems.add(new TabItem(R.drawable.main_bottom_mine_normal, R.drawable.main_bottom_mine_press,
                R.string.main_mine_text, MineFragment.class));
    }

    private void initTabHost() {
        MyFragmentTabHost fragmentTabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i < tabItems.size(); i++) {
            TabItem tabItem = tabItems.get(i);
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitleString())
                    .setIndicator(tabItem.getTabView());
            fragmentTabHost.addTab(tabSpec, tabItem.getFragmentClass(), tabItem.getBundle());
            fragmentTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundColor(getResources().getColor(R.color.main_bottom_bg));
            fragmentTabHost.setOnTabChangedListener(this);
        }
        tabItems.get(0).setChecked(true);

    }

    @Override
    public void onTabChanged(String tabId) {
        if (TextUtils.isEmpty(tabId)) {
            Log.e(TAG, "发布被点击了");
            Toast.makeText(MainActivity.this, "发布被点击了", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Log.e(TAG, tabId + "被点击了");
        }
        for (int i = 0; i < tabItems.size(); i++) {
            TabItem tabItem = tabItems.get(i);
            if (tabId.equals(tabItem.getTitleString())) {
                tabItem.setChecked(true);
            } else {
                tabItem.setChecked(false);
            }
        }
    }

    public class TabItem {
        private int imageNormal;
        private int imagePress;
        private int title;
        private String titleString;
        private View tabView;
        private ImageView imageView;
        private TextView textView;

        private Class<? extends Fragment> fragmentClass;
        private Bundle bundle;

        public TabItem(int imageNormal, int imagePress, int title, Class<? extends Fragment> fragmentClass) {
            this.imageNormal = imageNormal;
            this.title = title;
            this.imagePress = imagePress;
            this.fragmentClass = fragmentClass;
        }

        public Bundle getBundle() {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("title", getTitleString());
            return bundle;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        public String getTitleString() {
            if (title == 0) {
                return "";
            }
            if (TextUtils.isEmpty(titleString)) {
                titleString = getString(title);
            }
            return titleString;
        }

        private void setImageWH(float dpW, float dpH) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Dip2px.dip2px(getBaseContext(), dpW), Dip2px.dip2px(getBaseContext(), dpH));
            imageView.setLayoutParams(layoutParams);
        }

        public View getTabView() {
            if (tabView == null) {
                tabView = getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
                imageView = (ImageView) tabView.findViewById(R.id.iv_tab);
                textView = (TextView) tabView.findViewById(R.id.tv_tab);
            }
            imageView.setImageResource(imageNormal);
            if (title == 0) {
                textView.setVisibility(View.GONE);
                setImageWH(35, 35);
            } else {
                setImageWH(26, 26);
                textView.setVisibility(View.VISIBLE);
                textView.setText(getTitleString());
            }
            return tabView;
        }

        public void setChecked(boolean isCheck) {
            if (isCheck) {
                if (imageView != null) {
                    imageView.setImageResource(imagePress);
                }
                if (textView != null && title != 0) {
                    textView.setTextColor(getResources().getColor(R.color.main_bottom_text_press));
                }
            } else {
                if (imageView != null) {
                    imageView.setImageResource(imageNormal);
                }
                if (textView != null && title != 0) {
                    textView.setTextColor(getResources().getColor(R.color.main_bottom_text_normal));
                }
            }
        }
    }
}

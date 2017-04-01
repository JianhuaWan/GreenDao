//package com.xiangyue.db;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.ExpandableListView;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.huawei.hae.mcloud.rt.apkplugin.Plugin;
//import com.huawei.productinfo.BaseActivity;
//import com.huawei.productinfo.R;
//import com.huawei.productinfo.adapter.ProductQueryListAdapter;
//import com.huawei.productinfo.adapter.QueryAdapter;
//import com.huawei.productinfo.interfaces.IProductFindServiceImpl;
//import com.huawei.productinfo.model.ProductFindModel;
//import com.huawei.productinfo.util.SearchSDBHelper;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class QueryProductActivity extends BaseActivity implements View.OnClickListener {
//
//    /**
//     * ExpandableListView
//     */
//    private ExpandableListView mExpandableListView;
//    /**
//     * 列表数据源
//     */
//    private static ArrayList<ProductFindModel> mDataList;
//    private ArrayList<ProductFindModel> tempChangedata = new ArrayList<>();
//    private ArrayList<ProductFindModel> tempChangedataItem = new ArrayList<>();
//    /**
//     * 列表适配器
//     */
//
//    private ProductQueryListAdapter mAdapter;
//    private QueryAdapter toastAdater;
//
//    /**
//     * 标题左边后退键
//     */
//    private ImageView mTitleLeft;
//    private TextView title_img_right;
//
//    private EditText edit_text;
//    /**
//     * 无网络或无数据时显示的Layout上面的返回button
//     */
//    private ImageView title_img_left;
//
//    ArrayList<String> modelstop = new ArrayList<>();
//
//    private Map<String, String> map = new HashMap<>();
//    Map<String, String> modelsfianlList = new HashMap<>();
//    Map<String, String> modelsfianlListtemp = new HashMap<>();
//
//    private ArrayList<String> item = new ArrayList<>();
//    private ListView list_olddata;
//    private ArrayList<String> toastlist = new ArrayList<>();
//
//    private View headerView;
//    private SearchSDBHelper mHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_product_query);
//        mDataList = new ArrayList<>();
//        initTools();
//        initViewAndListener();
//        addHeaderView();
//        getServerData();
//        addAllData();
//    }
//
//    private void addAllData() {
//        mHelper.deldata();
//        mHelper.insert(modelstop, item);
//    }
//
//    private void addHeaderView() {
//        headerView = LayoutInflater.from(QueryProductActivity.this).inflate(R.layout.head_item, null);
//        list_olddata.addHeaderView(headerView);
//    }
//
//    private void initTools() {
//        mHelper = new SearchSDBHelper(QueryProductActivity.this, IProductFindServiceImpl.username);
//    }
//
//    private void getServerData() {
//        mDataList = getIntent().getParcelableArrayListExtra("list");
////        tempChangedata = getIntent().getParcelableArrayListExtra("list");
//        tempChangedata.addAll(mDataList);
//        mAdapter.setData(mDataList);
//
//        for (int i = 0; i < mDataList.size(); i++) {
//            modelstop.add(mDataList.get(i).getTypeName());
//            map.put(mDataList.get(i).getTypeName(), i + "");
//            for (int j = 0; j < mDataList.get(i).getTypelist().size(); j++) {
//                item.add(mDataList.get(i).getTypelist().get(j).getTypeName());
//                modelsfianlList.put(mDataList.get(i).getTypelist().get(j).getTypeName(), mDataList.get(i).getTypeName());
//
//            }
//        }
//        for (int i = mDataList.size() - 1; i >= 0; i--) {
//            for (int j = mDataList.get(i).getTypelist().size() - 1; j >= 0; j--) {
//                modelsfianlListtemp.put(mDataList.get(i).getTypelist().get(j).getTypeName(), mDataList.get(i).getTypeName());
//
//            }
//        }
//        title_img_right.setVisibility(View.VISIBLE);
//    }
//
//    /**
//     * 初始化布局和监听
//     */
//    private void initViewAndListener() {
//        list_olddata = (ListView) findViewById(R.id.list_olddata);
//        toastAdater = new QueryAdapter(QueryProductActivity.this, toastlist);
//        list_olddata.setAdapter(toastAdater);
//
//        title_img_right = (TextView) findViewById(R.id.title_img_right);
//        title_img_right.setOnClickListener(this);
//        mTitleLeft = (ImageView) findViewById(R.id.title_img_left);
//        mTitleLeft.setVisibility(View.VISIBLE);
//        mTitleLeft.setOnClickListener(this);
//        edit_text = (EditText) findViewById(R.id.edit_text);
//        edit_text.setOnClickListener(this);
//        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
//        title_img_left = (ImageView) findViewById(R.id.title_img_left);
//        title_img_left.setOnClickListener(this);
//        mAdapter = new ProductQueryListAdapter(QueryProductActivity.this, mDataList);
//        mExpandableListView.setAdapter(mAdapter);
//
//        /**
//         * 设置组item点击事件监听
//         */
//        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                ImageView iv = (ImageView) v.findViewById(R.id.expand_imageview);
//                if (parent.isGroupExpanded(groupPosition)) {//换成下箭头
//                    iv.setImageResource(R.mipmap.img_arrow_down_light);
//                } else {//换成上箭头
//                    iv.setImageResource(R.mipmap.img_arrow_up_light);
//                }
//                return false;
//            }
//        });
//
//        /**
//         * 设置子item点击事件监听
//         */
//        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Intent intent = new Intent();
//                intent.setClassName(IProductFindServiceImpl.mContext.getPackageName(), ProductInfoActivity.class.getName());
////                intent.putParcelableArrayListExtra("detail", mDataList.get(groupPosition).getTypelist());
////                intent.putExtra("title", mDataList.get(groupPosition).getTypeName());
////                intent.putExtra("index", childPosition);
//                Bundle bundle = new Bundle();
//
//
//                if (!edit_text.getText().toString().equals("")) {
//                    bundle.putString("typeName", tempChangedataItem.get(groupPosition).getTypeName());
//                    bundle.putParcelableArrayList("typeList", tempChangedataItem.get(groupPosition).getTypelist());
//                } else {
//                    bundle.putParcelableArrayList("typeList", mDataList.get(groupPosition).getTypelist());
//                    bundle.putString("typeName", mDataList.get(groupPosition).getTypeName());
//                }
//                bundle.putInt("index", childPosition);
//                intent.putExtra("data", bundle);
////                startActivity(intent);
//                Plugin.startActivity(IProductFindServiceImpl.mContext, intent);
//                return false;
//            }
//        });
//
//        /**
//         * 设置组item展开监听
//         */
//        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                for (int i = 0; i < mDataList.size(); i++) {
//                    if (groupPosition != i) {
//                        mExpandableListView.collapseGroup(i);
//                    }
//                }
//            }
//        });
//        list_olddata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position != 0) {
//                    String s = (String) toastAdater.getItem(position - 1);
//                    edit_text.setText(s);
//                    query();
//                }
//            }
//        });
//        edit_text.addTextChangedListener(mTextWatcher);//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.edit_text:
//                if (toastlist.size() != 0) {
//                    toastlist.clear();
//                }
//                mExpandableListView.setVisibility(View.GONE);
//                list_olddata.setVisibility(View.VISIBLE);
//                String[] searchInfos = mHelper.queryAllSearch();
//                for (int i = 0; i < searchInfos.length; i++) {
//                    toastlist.add(searchInfos[i]);
//                }
//                toastAdater.setDate(toastlist);
//                break;
//            case R.id.title_img_left:
//                finish();
//                break;
//            case R.id.title_img_right:
//                //查询
//                String[] key = mHelper.queryAllSearch_data(edit_text.getText().toString());
//                if (key.length != 0) {
//                    edit_text.setText(key[0]);
//                    query1(key);
//                } else {
//                    Toast.makeText(QueryProductActivity.this, R.string.noting, Toast.LENGTH_LONG).show();
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//
//    public void query() {
//        mExpandableListView.setVisibility(View.VISIBLE);
//        list_olddata.setVisibility(View.GONE);
//        if (tempChangedataItem.size() != 0) {
//            tempChangedataItem.clear();//清空之前数据
//        }
//        if (modelstop.contains(edit_text.getText().toString())) {
//            //一级全字段搜索
//            int position = Integer.parseInt(map.get(edit_text.getText().toString()));
//            edit_text.setSelection(edit_text.getText().length());
//            tempChangedataItem.add(0, tempChangedata.get(position));
//            mAdapter.setData(tempChangedataItem);
//            mExpandableListView.expandGroup(0);
//            String ed_search_msg = edit_text.getText().toString();
//            mHelper.insertOrUpdate(ed_search_msg);
//        } else {
//            for (int i = 0; i < modelstop.size(); i++) {
//                //一级模糊化搜索
//                if (modelstop.get(i).startsWith(edit_text.getText().toString())) {
//                    edit_text.setText(modelstop.get(i));
//                    edit_text.setSelection(modelstop.get(i).length());
//                    int position = Integer.parseInt(map.get(edit_text.getText().toString()));
//                    tempChangedataItem.add(0, tempChangedata.get(position));
//                    mAdapter.setData(tempChangedataItem);
//                    mExpandableListView.expandGroup(0);
//                    String ed_search_msg = edit_text.getText().toString();
//                    mHelper.insertOrUpdate(ed_search_msg);
//                    return;
//                }
//            }
//            //二级模糊化搜索
//            for (int j = 0; j < item.size(); j++) {
//                if (item.get(j).startsWith(edit_text.getText().toString())) {
//                    edit_text.setText(item.get(j));
//                    edit_text.setSelection(item.get(j).length());
//                    int position = Integer.parseInt(map.get(modelsfianlList.get(item.get(j))));
//                    tempChangedataItem.add(0, tempChangedata.get(position));
//                    mAdapter.setData(tempChangedataItem);
//                    mExpandableListView.expandGroup(0);
//                    String ed_search_msg = edit_text.getText().toString();
//                    mHelper.insertOrUpdate(ed_search_msg);
//                    return;
//                }
//            }
//
//            if (tempChangedataItem.size() != 0) {
//                tempChangedataItem.clear();
//            }
//            mAdapter.setData(tempChangedataItem);
//
//        }
//    }
//
//    TextWatcher mTextWatcher = new TextWatcher() {
//        private CharSequence temp;
//
//        @Override
//        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
//            // TODO Auto-generated method stub
//            temp = s;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//            // TODO Auto-generated method stub
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            // TODO Auto-generated method stub
//            if (temp.length() == 0) {
//                mAdapter.setData(mDataList);
//                mExpandableListView.setVisibility(View.VISIBLE);
//                list_olddata.setVisibility(View.GONE);
//            }
//
//        }
//    };
//
//    List<Integer> positionList = new ArrayList<>();
//
//    public void query1(String[] key) {
//        mExpandableListView.setVisibility(View.VISIBLE);
//        list_olddata.setVisibility(View.GONE);
//
//        if (tempChangedataItem.size() != 0) {
//            tempChangedataItem.clear();//清空之前数据
//        }
//        if (positionList.size() != 0) {
//            positionList.clear();//清空之前数据
//        }
//
//        for (int i = 0; i < key.length; i++) {
//            if (modelstop.contains(key[i])) {
//                //一级全字段搜索
//                int position = Integer.parseInt(map.get(key[i]));
//                positionList.add(position);
//                edit_text.setSelection(edit_text.getText().length());
//                tempChangedataItem.add(i, tempChangedata.get(position));//存一条数据
//                String ed_search_msg = edit_text.getText().toString();
//                mHelper.insertOrUpdate(ed_search_msg);
//            }
//        }
//        for (int i = 0; i < key.length; i++) {
//            for (int j = 0; j < item.size(); j++) {
//                if (item.get(j).equals(key[i])) {
//                    String keys = key[i];
//                    edit_text.setSelection(edit_text.getText().length());
//                    int position = Integer.parseInt(map.get(modelsfianlList.get(item.get(j))));
//                    if (!positionList.contains(position)) {
//                        positionList.add(position);
//                        tempChangedataItem.add(tempChangedata.get(position));
//                        String ed_search_msg = edit_text.getText().toString();
//                        mHelper.insertOrUpdate(ed_search_msg);
//                    }
//                }
//            }
//
//        }
//        for (int i = 0; i < key.length; i++) {
//            for (int j = 0; j < item.size(); j++) {
//                if (item.get(j).equals(key[i])) {
//                    String keys = key[i];
//                    edit_text.setSelection(edit_text.getText().length());
//                    int position = Integer.parseInt(map.get(modelsfianlListtemp.get(item.get(j))));
//                    if (!positionList.contains(position)) {
//                        positionList.add(position);
//                        tempChangedataItem.add(tempChangedata.get(position));
//                        String ed_search_msg = edit_text.getText().toString();
//                        mHelper.insertOrUpdate(ed_search_msg);
//                    }
//                }
//            }
//
//        }
//
//        mAdapter.setData(tempChangedataItem);
//        mExpandableListView.expandGroup(0);
//    }
//}
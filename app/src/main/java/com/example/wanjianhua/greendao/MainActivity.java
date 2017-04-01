package com.example.wanjianhua.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.wanjianhua.gen.UsersDao;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {
    @BindView(R.id.btn_add)
    Button btnAdd;
    //    @BindView(R.id.ed_content)
//    EditText edContent;
//    @BindView(R.id.btn_add)
//    Button btnAdd;
//    @BindView(R.id.btn_del)
//    Button btnDel;
//    @BindView(R.id.btn_update)
//    Button btnUpdate;
//    @BindView(R.id.btn_query)
//    Button btnQuery;
//    @BindView(tv_content)
//    TextView tvContent;
    private UsersDao mUsersDao;
    private Users mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDate();
    }

    private void initDate() {
        //获取实例
        mUsersDao = BaseApplication.getInstance().getDaoSession().getUsersDao();
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        Toast.makeText(MainActivity.this, "111", Toast.LENGTH_LONG).show();
    }

//    @OnClick({R.id.btn_add, R.id.btn_del, R.id.btn_update, R.id.btn_query})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_add:
//                mUsers = new Users();
//                mUsers.setName(edContent.getText().toString());
//                mUsersDao.insert(mUsers);
//                break;
//            case R.id.btn_del:
//                mUsers = new Users();
//                mUsers.setName(edContent.getText().toString());
//                mUsersDao.delete(mUsers);
//                break;
//            case R.id.btn_update:
//                mUsers = new Users();
//                mUsers.setName(edContent.getText().toString());
//                mUsersDao.update(mUsers);
//                break;
//            case R.id.btn_query:
//                List<Users> userses = mUsersDao.loadAll();
//                String userName = "";
//                for (int i = 0; i < userses.size(); i++) {
//                    userName += userses.get(i).getName() + ",";
//                }
//                tvContent.setText(userName);
//                break;
//        }
//    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_add:
//                mUsers = new Users();
//                mUsers.setName("anni");
//                mUsersDao.insert(mUsers);
//                break;
//            case R.id.btn_del:
////                mUsersDao.delete((Long(long)a);
//                break;
//            case R.id.btn_update:
//                mUsers = new Users();
//                mUsers.setName("annidddyyyyyy");
//                mUsersDao.update(mUsers);
//                break;
//            case R.id.btn_query:
//                List<Users> userses = mUsersDao.loadAll();
//                String userName = "";
//                for (int i = 0; i < userses.size(); i++) {
//                    userName += userses.get(i).getName() + ",";
//                }
//                tv_content.setText(userName);
//                break;
//            default:
//                break;
//        }
//    }
}

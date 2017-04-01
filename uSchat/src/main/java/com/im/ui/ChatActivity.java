package com.im.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.ChatAdapter;
import com.im.adapter.OnRecyclerViewListener;
import com.im.base.ParentWithNaviActivity;
import com.im.bean.AddFriendMessage;
import com.im.util.Util;
import com.xiangyue.act.BaiduMapActivity;
import com.xiangyue.act.ImageGridActivity;
import com.xiangyue.act.R;
import com.xiangyue.adpter.ExpressionAdapter;
import com.xiangyue.adpter.ExpressionPagerAdapter;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.util.CommonUtils;
import com.xiangyue.util.SmileUtils;
import com.xiangyue.weight.AlertDialog;
import com.xiangyue.weight.ExpandGridView;
import com.xiangyue.weight.PasteEditText;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.bmob.newim.bean.BmobIMAudioMessage;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMLocationMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMVideoMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.BmobRecordManager;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.newim.listener.ObseverListener;
import cn.bmob.newim.listener.OnRecordChangeListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;
import de.greenrobot.event.Subscribe;

/**
 * 聊天界面
 *
 * @author :smile
 * @project:ChatActivity
 * @date :2016-01-25-18:23
 */
public class ChatActivity extends ParentWithNaviActivity implements ObseverListener {

    @Bind(R.id.ll_chat)
    LinearLayout ll_chat;

    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;

    @Bind(R.id.rc_view)
    RecyclerView rc_view;

    @Bind(R.id.edit_msg)
    PasteEditText edit_msg;

    @Bind(R.id.btn_chat_add)
    Button btn_chat_add;
    @Bind(R.id.btn_chat_emo)
    ImageView btn_chat_emo;
    @Bind(R.id.btn_speak)
    Button btn_speak;
    @Bind(R.id.btn_chat_voice)
    Button btn_chat_voice;
    @Bind(R.id.btn_chat_keyboard)
    Button btn_chat_keyboard;
    @Bind(R.id.btn_chat_send)
    Button btn_chat_send;

    @Bind(R.id.layout_more)
    LinearLayout layout_more;
    @Bind(R.id.layout_add)
    LinearLayout layout_add;
    @Bind(R.id.layout_emo)
    LinearLayout layout_emo;
    @Bind(R.id.pager_emo)
    ViewPager pager_emo;

    // 语音有关
    @Bind(R.id.layout_record)
    RelativeLayout layout_record;
    @Bind(R.id.tv_voice_tips)
    TextView tv_voice_tips;
    @Bind(R.id.iv_record)

    ImageView iv_record;
    private Drawable[] drawable_Anims;// 话筒动画
    BmobRecordManager recordManager;
    ChatAdapter adapter;
    protected LinearLayoutManager layoutManager;
    BmobIMConversation c;
    @Bind(R.id.edittext_layout)
    RelativeLayout edittext_layout;


    @Override
    protected String title() {
        return c.getConversationTitle();
    }

    @Override
    public Object right() {
        return R.drawable.chat_voice_cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        c = BmobIMConversation.obtain(BmobIMClient.getInstance(), (BmobIMConversation) getBundle().getSerializable("c"));
        c.setConversationTitle(getBundle().getString("nickName"));
        initNaviView();
        initSwipeLayout();
        initVoiceView();
        initBottomView();
        initSettings();
        initEmoj();
    }

    private List<String> reslist;

    private void initEmoj() {
        // 表情list
        reslist = getExpressionRes(100);
        // 初始化表情viewpager
        List<View> views = new ArrayList<View>();
        View gv1 = getGridChildView(1);
        View gv2 = getGridChildView(2);
        View gv3 = getGridChildView(3);
        View gv4 = getGridChildView(4);
        View gv5 = getGridChildView(5);
        views.add(gv1);
        views.add(gv2);
        views.add(gv3);
        views.add(gv4);
        views.add(gv5);
        pager_emo.setAdapter(new ExpressionPagerAdapter(views));
    }

    public List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 1; x <= getSum; x++) {
            String filename = "ee_" + x;
            reslist.add(filename);
        }
        return reslist;

    }

    /**
     * 获取表情的gridview的子view
     *
     * @param i
     * @return
     */
    private View getGridChildView(int i) {
        View view = View.inflate(this, R.layout.expression_gridview, null);
        ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
        List<String> list = new ArrayList<String>();
        if (i == 1) {
            List<String> list1 = reslist.subList(0, 20);
            list.addAll(list1);
        } else if (i == 2) {
            list.addAll(reslist.subList(20, 40));
        } else if (i == 3) {
            list.addAll(reslist.subList(40, 60));
        } else if (i == 4) {
            list.addAll(reslist.subList(60, 80));
        } else if (i == 5) {
            list.addAll(reslist.subList(80, reslist.size()));
        }
        list.add("delete_expression");
        final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this, 1, list);
        gv.setAdapter(expressionAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename = expressionAdapter.getItem(position);
                try {
                    // 文字输入框可见时，才可输入表情
                    // 按住说话可见，不让输入表情
                    if (btn_chat_keyboard.getVisibility() != View.VISIBLE) {
                        if (filename != "delete_expression") { // 不是删除键，显示表情
                            // 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
                            Class clz = Class.forName("com.xiangyue.util.SmileUtils");
                            Field field = clz.getField(filename);
                            edit_msg.append(SmileUtils.getSmiledText(ChatActivity.this,
                                    (String) field.get(null)));
                        } else { // 删除文字或者表情
                            if (!TextUtils.isEmpty(edit_msg.getText())) {

                                int selectionStart = edit_msg.getSelectionStart();// 获取光标的位置
                                if (selectionStart > 0) {
                                    String body = edit_msg.getText().toString();
                                    String tempStr = body.substring(0, selectionStart);
                                    int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
                                    if (i != -1) {
                                        CharSequence cs = tempStr.substring(i, selectionStart);
                                        if (SmileUtils.containsKey(cs.toString()))
                                            edit_msg.getEditableText().delete(i, selectionStart);
                                        else
                                            edit_msg.getEditableText().delete(selectionStart - 1,
                                                    selectionStart);
                                    } else {
                                        edit_msg.getEditableText().delete(selectionStart - 1, selectionStart);
                                    }
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                }

            }
        });
        return view;
    }

    private void initSettings() {
        settings = new MicroRecruitSettings(ChatActivity.this);
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {
                finish();
            }

            @Override
            public void clickRight() {
                new AlertDialog(ChatActivity.this).builder().setTitle("提示")
                        .setMsg("是否清空消息?")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                c.clearMessage(true);
                                adapter.setClean(ChatActivity.this, c);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
            }
        };
    }


    private void initSwipeLayout() {

        sw_refresh.setEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(layoutManager);

        adapter = new ChatAdapter(this, c);
        rc_view.setAdapter(adapter);
        ll_chat.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_chat.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                sw_refresh.setRefreshing(true);
                //自动刷新
                queryMessages(null);
            }
        });
        sw_refresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //下拉加载
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BmobIMMessage msg = adapter.getFirstMessage();
                queryMessages(msg);
            }
        });
        //设置RecyclerView的点击事件
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

                if (layout_more.getVisibility() == View.VISIBLE) {
                    layout_more.setVisibility(View.GONE);
                }
                if (layout_emo.getVisibility() == View.VISIBLE) {
                    layout_emo.setVisibility(View.GONE);
                }
                //隐藏键盘
                hideSoftInputView();
            }

            @Override
            public boolean onItemLongClick(int position) {
                //这里省了个懒，直接长按就删除了该消息
//                c.deleteMessage(adapter.getItem(position));
//                adapter.remove(position);
//                Toast.makeText(ChatActivity.this, "删除" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void initBottomView() {
        edittext_layout.requestFocus();
        edit_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                layoutManager.setStackFromEnd(true);
            }
        });
        edit_msg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
                    scrollToBottom();
                }
                return false;
            }
        });
        edit_msg.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                scrollToBottom();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {

                    btn_chat_send.setVisibility(View.VISIBLE);
                    btn_chat_keyboard.setVisibility(View.GONE);
                    btn_chat_voice.setVisibility(View.GONE);
                } else {

                    if (btn_chat_voice.getVisibility() != View.VISIBLE) {
                        btn_chat_voice.setVisibility(View.VISIBLE);
                        btn_chat_send.setVisibility(View.GONE);
                        btn_chat_keyboard.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //反射的文字换成表情
//                try {
//                    String filename = s.toString();
//                    Class clz = Class.forName("com.xiangyue.util.SmileUtils");
//                    Field field = clz.getField(filename);
//                    edit_msg.append(SmileUtils.getSmiledText(ChatActivity.this,
//                            (String) field.get(null)));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }
        });
    }

    /**
     * 初始化语音布局
     *
     * @param
     * @return void
     */
    private void initVoiceView() {
        btn_speak.setOnTouchListener(new VoiceTouchListener());
        initVoiceAnimRes();
        initRecordManager();
    }

    /**
     * 初始化语音动画资源
     *
     * @param
     * @return void
     * @Title: initVoiceAnimRes
     */
    private void initVoiceAnimRes() {
        drawable_Anims = new Drawable[]{
                getResources().getDrawable(R.drawable.record_animate_01),
                getResources().getDrawable(R.drawable.record_animate_02),
                getResources().getDrawable(R.drawable.record_animate_03),
                getResources().getDrawable(R.drawable.record_animate_04),
                getResources().getDrawable(R.drawable.record_animate_05),
                getResources().getDrawable(R.drawable.record_animate_06)};
    }

    private void initRecordManager() {
        // 语音相关管理器
        recordManager = BmobRecordManager.getInstance(this);
        // 设置音量大小监听--在这里开发者可以自己实现：当剩余10秒情况下的给用户的提示，类似微信的语音那样
        recordManager.setOnRecordChangeListener(new OnRecordChangeListener() {

            @Override
            public void onVolumnChanged(int value) {
                iv_record.setImageDrawable(drawable_Anims[value]);
            }

            @Override
            public void onTimeChanged(int recordTime, String localPath) {
                if (recordTime >= BmobRecordManager.MAX_RECORD_TIME) {// 1分钟结束，发送消息
                    // 需要重置按钮
                    btn_speak.setPressed(false);
                    btn_speak.setClickable(false);
                    // 取消录音框
                    layout_record.setVisibility(View.INVISIBLE);
                    // 发送语音消息
                    sendVoiceMessage(localPath, recordTime);
                    //是为了防止过了录音时间后，会多发一条语音出去的情况。
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            btn_speak.setClickable(true);
                        }
                    }, 1000);
                }
            }
        });
    }

    /**
     * 长按说话
     *
     * @author smile
     * @date 2014-7-1 下午6:10:16
     */
    class VoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!Util.checkSdCard()) {
                        toast("发送语音需要sdcard支持！");
                        return false;
                    }
                    try {
                        v.setPressed(true);
                        layout_record.setVisibility(View.VISIBLE);
                        tv_voice_tips.setText(getString(R.string.voice_cancel_tips));
                        // 开始录音
                        recordManager.startRecording(c.getConversationId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                case MotionEvent.ACTION_MOVE: {
                    if (event.getY() < 0) {
                        tv_voice_tips.setText(getString(R.string.voice_cancel_tips));
                        tv_voice_tips.setTextColor(Color.RED);
                    } else {
                        tv_voice_tips.setText(getString(R.string.voice_up_tips));
                        tv_voice_tips.setTextColor(Color.WHITE);
                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                    v.setPressed(false);
                    layout_record.setVisibility(View.INVISIBLE);
                    try {
                        if (event.getY() < 0) {// 放弃录音
                            recordManager.cancelRecording();
                        } else {
                            int recordTime = recordManager.stopRecording();
                            if (recordTime > 1) {
                                // 发送语音文件
                                sendVoiceMessage(recordManager.getRecordFilePath(c.getConversationId()), recordTime);
                            } else {// 录音时间过短，则提示录音过短的提示
                                layout_record.setVisibility(View.GONE);
                                showShortToast().show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                default:
                    return false;
            }
        }
    }

    Toast toast;

    /**
     * 显示录音时间过短的Toast
     *
     * @return void
     * @Title: showShortToast
     */
    private Toast showShortToast() {
        if (toast == null) {
            toast = new Toast(this);
        }
        View view = LayoutInflater.from(this).inflate(
                R.layout.include_chat_voice_short, null);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        return toast;
    }

    @OnClick(R.id.edit_msg)
    public void onEditClick(View view) {
        if (layout_more.getVisibility() == View.VISIBLE) {
            layout_add.setVisibility(View.GONE);
            layout_emo.setVisibility(View.GONE);
            layout_more.setVisibility(View.GONE);
        }
    }

    @OnFocusChange(R.id.edit_msg)
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_active);
        } else {
            edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_normal);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.btn_chat_emo)
    public void onEmoClick(View view) {
        if (layout_more.getVisibility() == View.GONE) {
            showEditState(true);
        } else {
            if (layout_add.getVisibility() == View.VISIBLE) {
                layout_add.setVisibility(View.GONE);
                layout_emo.setVisibility(View.VISIBLE);
                btn_chat_emo.setBackgroundResource(R.drawable.chatting_biaoqing_btn_enable);
                btn_chat_add.setBackground(getResources().getDrawable(R.drawable.skin_aio_panel_plus_nor));
            } else {
                btn_chat_emo.setBackgroundResource(R.drawable.chatting_biaoqing_btn_normal);
                layout_more.setVisibility(View.GONE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.btn_chat_add)
    public void onAddClick(View view) {
        if (layout_more.getVisibility() == View.GONE) {
            btn_chat_emo.setBackgroundResource(R.drawable.chatting_biaoqing_btn_normal);
            btn_chat_add.setBackground(getResources().getDrawable(R.drawable.skin_aio_panel_plus_press));
            layout_more.setVisibility(View.VISIBLE);
            layout_add.setVisibility(View.VISIBLE);
            layout_emo.setVisibility(View.GONE);
            hideSoftInputView();
        } else {
            if (layout_emo.getVisibility() == View.VISIBLE) {
                layout_emo.setVisibility(View.GONE);
                layout_add.setVisibility(View.VISIBLE);
                btn_chat_add.setBackground(getResources().getDrawable(R.drawable.skin_aio_panel_plus_press));
                btn_chat_emo.setBackgroundResource(R.drawable.chatting_biaoqing_btn_normal);
            } else {
                btn_chat_add.setBackground(getResources().getDrawable(R.drawable.skin_aio_panel_plus_nor));
                btn_chat_emo.setBackgroundResource(R.drawable.chatting_biaoqing_btn_normal);
                layout_more.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.btn_chat_voice)
    public void onVoiceClick(View view) {
        edit_msg.setVisibility(View.GONE);
        layout_more.setVisibility(View.GONE);
        btn_chat_voice.setVisibility(View.GONE);
        edittext_layout.setVisibility(View.GONE);
        btn_chat_keyboard.setVisibility(View.VISIBLE);
        btn_speak.setVisibility(View.VISIBLE);
        hideSoftInputView();
    }

    @OnClick(R.id.btn_chat_keyboard)
    public void onKeyClick(View view) {
        showEditState(false);
    }

    @OnClick(R.id.btn_chat_send)
    public void onSendClick(View view) {
        sendMessage();
    }

    @OnClick(R.id.tv_picture)
    public void onPictureClick(View view) {
        sendLocalImageMessage();
//        sendOtherMessage();
    }

    @OnClick(R.id.tv_camera)
    public void onCameraClick(View view) {
        sendRemoteImageMessage();
    }

    @OnClick(R.id.tv_location)
    public void onLocationClick(View view) {
        sendLocationMessage();
    }

    @OnClick(R.id.tv_video)
    public void onVideoClick(View view) {
//        sendVideoMessage();
        Toast.makeText(ChatActivity.this, "暂无开放", Toast.LENGTH_LONG).show();
    }

    /**
     * 根据是否点击笑脸来显示文本输入框的状态
     *
     * @param isEmo 用于区分文字和表情
     * @return void
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showEditState(boolean isEmo) {
        edit_msg.setVisibility(View.VISIBLE);
        btn_chat_keyboard.setVisibility(View.GONE);
        btn_chat_voice.setVisibility(View.VISIBLE);
        if (!edit_msg.getText().toString().equals("")) {
            btn_chat_send.setVisibility(View.VISIBLE);
            btn_chat_voice.setVisibility(View.GONE);
        } else {
            btn_chat_voice.setVisibility(View.VISIBLE);
            btn_chat_send.setVisibility(View.GONE);
        }


        edittext_layout.setVisibility(View.VISIBLE);
        btn_speak.setVisibility(View.GONE);
        edit_msg.requestFocus();
        if (isEmo) {
            layout_more.setVisibility(View.VISIBLE);
            layout_emo.setVisibility(View.VISIBLE);
            btn_chat_emo.setBackgroundResource(R.drawable.chatting_biaoqing_btn_enable);
            btn_chat_add.setBackground(getResources().getDrawable(R.drawable.skin_aio_panel_plus_nor));
            layout_add.setVisibility(View.GONE);
            hideSoftInputView();
        } else {
            btn_chat_add.setBackground(getResources().getDrawable(R.drawable.skin_aio_panel_plus_nor));
            btn_chat_emo.setBackgroundResource(R.drawable.chatting_biaoqing_btn_normal);
            layout_more.setVisibility(View.GONE);
            showSoftInputView();
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInputView() {
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .showSoftInput(edit_msg, 0);
        }
    }

    /**
     * 发送文本消息
     */
    private void sendMessage() {
        String text = edit_msg.getText().toString();
        if (TextUtils.isEmpty(text.trim())) {
            toast("请输入内容");
            return;
        }
        BmobIMTextMessage msg = new BmobIMTextMessage();
        msg.setContent(text);
        //可设置额外信息
        Map<String, Object> map = new HashMap<>();
        map.put("level", "1");//随意增加信息
        msg.setExtraMap(map);
        c.sendMessage(msg, listener);
    }

    /**
     * 直接发送远程图片地址
     */
    public void sendRemoteImageMessage() {
        selectPicFromCamera();
    }

    private File cameraFile;
    private MicroRecruitSettings settings;

    /**
     * 照相获取图片
     */
    public void selectPicFromCamera() {
        if (!CommonUtils.isExitsSdcard()) {
            String st = "sd_card_does_not_exist";
            Toast.makeText(getApplicationContext(), st, Toast.LENGTH_LONG).show();
            return;
        }

        cameraFile = new File(getDownFilePath(), settings.phone.getValue().toString()
                + System.currentTimeMillis() + ".jpg");
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                REQUEST_CODE_CAMERA);
    }

    public static String getDownFilePath() {
        String dirName = "";
        // 获取SD卡
        dirName = Environment.getExternalStorageDirectory() + "/xiangyue/";
        File f = new File(dirName);
        if (!f.exists()) {
            f.mkdir();
        }
        return dirName;
    }

    public static final int REQUEST_CODE_LOCAL = 19;
    public static final int REQUEST_CODE_CAMERA = 18;

    /**
     * 发送本地图片地址
     */
    public void sendLocalImageMessage() {
        //正常情况下，需要调用系统的图库或拍照功能获取到图片的本地地址，开发者只需要将本地的文件地址传过去就可以发送文件类型的消息
        //选择图片
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);

//        //因此也可以使用BmobIMFileMessage来发送文件消息
//        BmobIMFileMessage file =new BmobIMFileMessage("文件地址");
//        c.sendMessage(file,listener);
    }

    /**
     * 发送语音消息
     *
     * @param local
     * @param length
     * @return void
     * @Title: sendVoiceMessage
     */
    private void sendVoiceMessage(String local, int length) {
        BmobIMAudioMessage audio = new BmobIMAudioMessage(local);
        //可设置额外信息-开发者设置的额外信息，需要开发者自己从extra中取出来
        Map<String, Object> map = new HashMap<>();
        map.put("from", "优酷");
        audio.setExtraMap(map);
        //设置语音文件时长：可选
//        audio.setDuration(length);
        c.sendMessage(audio, listener);
    }

    private static final int REQUEST_CODE_MAP = 4;
    public static final int REQUEST_CODE_SELECT_VIDEO = 23;

    /**
     * 发送地理位置
     */
    public void sendLocationMessage() {

        startActivityForResult(new Intent(this, BaiduMapActivity.class), REQUEST_CODE_MAP);
    }

    /**
     * 发送视频
     */
    public void sendVideoMessage() {

        startActivityForResult(new Intent(this, ImageGridActivity.class), REQUEST_CODE_SELECT_VIDEO);
    }

    /**
     * 发送自定义消息，比如：好友请求
     */
    public void sendOtherMessage() {
        AddFriendMessage msg = new AddFriendMessage();
        msg.setContent("XXX添加你为好友");
        Map<String, Object> map = new HashMap<>();
        map.put("message", "很高兴认识你，可以加个好友吗？");
        msg.setExtraMap(map);
        c.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    toast("发送成功");
                } else {//发送失败
                    toast("发送失败:" + e.getMessage());
                }
            }
        });
    }

    /**
     * 消息发送监听器
     */
    public MessageSendListener listener = new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);
            //文件类型的消息才有进度值
        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);
            adapter.addMessage(msg);
            edit_msg.setText("");
            scrollToBottom();
        }

        @Override
        public void done(BmobIMMessage msg, BmobException e) {
            adapter.notifyDataSetChanged();
            edit_msg.setText("");
            scrollToBottom();
            if (e != null) {
                toast(e.getMessage());
            }
        }
    };

    /**
     * 首次加载，可设置msg为null，下拉刷新的时候，默认取消息表的第一个msg作为刷新的起始时间点，默认按照消息时间的降序排列
     *
     * @param msg
     */
    public void queryMessages(BmobIMMessage msg) {
        c.queryMessages(msg, 10, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                sw_refresh.setRefreshing(false);
                if (e == null) {
                    if (null != list && list.size() > 0) {
                        adapter.addMessages(list);
                        layoutManager.scrollToPositionWithOffset(list.size() - 1, 0);
                    }
                } else {
                    toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                }
            }
        });
    }

    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
    }

    /**
     * 接收到聊天消息
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        addMessage2Chat(event);
    }

    /**
     * 添加消息到聊天界面中
     *
     * @param event
     */
    private void addMessage2Chat(MessageEvent event) {
        BmobIMMessage msg = event.getMessage();
        if (c != null && event != null && c.getConversationId().equals(event.getConversation().getConversationId()) //如果是当前会话的消息
                && !msg.isTransient()) {//并且不为暂态消息
            if (adapter.findPosition(msg) < 0) {//如果未添加到界面中
                adapter.addMessage(msg);
                //更新该会话下面的已读状态
                c.updateReceiveStatus(msg);
            }
            scrollToBottom();
        } else {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (layout_more.getVisibility() == View.VISIBLE) {
                layout_more.setVisibility(View.GONE);
                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onResume() {
        //锁屏期间的收到的未读消息需要添加到聊天界面中
        addUnReadMessage();
        //添加通知监听
        BmobNotificationManager.getInstance(this).addObserver(this);
        // 有可能锁屏期间，在聊天界面出现通知栏，这时候需要清除通知
        BmobNotificationManager.getInstance(this).cancelNotification();
        super.onResume();
    }

    /**
     * 添加未读的通知栏消息到聊天界面
     */
    private void addUnReadMessage() {
        List<MessageEvent> cache = BmobNotificationManager.getInstance(this).getNotificationCacheList();
        if (cache.size() > 0) {
            int size = cache.size();
            for (int i = 0; i < size; i++) {
                MessageEvent event = cache.get(i);
                addMessage2Chat(event);
            }
        }
        scrollToBottom();
    }

    @Override
    protected void onPause() {
        //取消通知栏监听
        BmobNotificationManager.getInstance(this).removeObserver(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //清理资源
        recordManager.clear();
        //更新此会话的所有消息为已读状态
        hideSoftInputView();
        c.updateLocalCache();
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    sendPicByUri(selectedImage);
                }
            }
        } else if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片
            if (cameraFile != null && cameraFile.exists()) {
                BmobIMImageMessage image = new BmobIMImageMessage();
                image.setRemoteUrl(cameraFile.getAbsolutePath());
                c.sendMessage(image, listener);
            }
        } else if (requestCode == REQUEST_CODE_MAP) {
            // 地图
            if (data == null) {
                return;
            }
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            String locationAddress = data.getStringExtra("address");
            if (locationAddress != null && !locationAddress.equals("")) {
                //测试数据，真实数据需要从地图SDK中获取
                BmobIMLocationMessage location = new BmobIMLocationMessage(locationAddress, latitude, longitude);
                c.sendMessage(location, listener);
            } else {
                String st = "unable_to_get_loaction";
                Toast.makeText(this, st, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CODE_SELECT_VIDEO) { // 发送本地选择的视频
            if (data == null) {
                return;
            }
            int duration = data.getIntExtra("dur", 0);
            String videoPath = data.getStringExtra("path");
//            Toast.makeText(ChatActivity.this, "文件地址" + videoPath, Toast.LENGTH_LONG).show();
            File file = new File(videoPath);
//            long size = file.length();
            BmobIMVideoMessage video = new BmobIMVideoMessage(file.getAbsolutePath());
//            video.setRemoteUrl(file.getAbsolutePath());
            c.sendMessage(video, listener);
        }
    }

    private void sendPicByUri(Uri selectedImage) {
        // String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
        String st8 = "can't_find_pictures";
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("_data");
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(this, st8, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            BmobIMImageMessage image = new BmobIMImageMessage(picturePath);
            c.sendMessage(image, listener);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(this, st8, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            BmobIMImageMessage image = new BmobIMImageMessage(file.getAbsolutePath());
            c.sendMessage(image, listener);
        }
    }
}

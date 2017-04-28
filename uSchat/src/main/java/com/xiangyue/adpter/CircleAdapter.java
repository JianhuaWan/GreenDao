package com.xiangyue.adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.xiangyue.act.AssistsActivity;
import com.xiangyue.act.CommontActivity;
import com.xiangyue.act.OtherInfoDetailActivity;
import com.xiangyue.act.R;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.CircleBean;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wWX321637 on 2016/5/9.
 */
public class CircleAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<CircleBean> jsonBeans = new ArrayList<>();
    private MicroRecruitSettings settings;

    public CircleAdapter(Context context, List<CircleBean> jsonBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.jsonBeans = jsonBeans;
        settings = new MicroRecruitSettings(context);
    }

    public void setData(Context context, List<CircleBean> jsonBeans) {
        this.context = context;
        this.jsonBeans = jsonBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return jsonBeans.size();
    }

    @Override
    public CircleBean getItem(int position) {
        // TODO Auto-generated method stub
        return jsonBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.circle_item_main, null);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_useraddress = (TextView) convertView.findViewById(R.id.tv_useraddress);
            viewHolder.tv_from = (TextView) convertView.findViewById(R.id.tv_from);
            viewHolder.month = (TextView) convertView.findViewById(R.id.month);
            viewHolder.day = (TextView) convertView.findViewById(R.id.day);
            viewHolder.img_photo1 = (ImageView) convertView.findViewById(R.id.img_photo1);
            viewHolder.img_photo2 = (ImageView) convertView.findViewById(R.id.img_photo2);
            viewHolder.img_photo3 = (ImageView) convertView.findViewById(R.id.img_photo3);
            viewHolder.img_photo4 = (ImageView) convertView.findViewById(R.id.img_photo4);
            viewHolder.tv_laud = (TextView) convertView.findViewById(R.id.tv_laud);
            viewHolder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            viewHolder.myheadIcon = (ImageView) convertView.findViewById(R.id.myheadIcon);
            viewHolder.linear_share = (LinearLayout) convertView.findViewById(R.id.linear_share);
            viewHolder.linear_assist = (LinearLayout) convertView.findViewById(R.id.linear_assist);
            viewHolder.linear_step = (LinearLayout) convertView.findViewById(R.id.linear_step);
            viewHolder.tv_countcommon = (TextView) convertView.findViewById(R.id.tv_countcommon);
            viewHolder.daytime = (TextView) convertView.findViewById(R.id.daytime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_content.setText(jsonBeans.get(position).getContent());
        viewHolder.tv_useraddress.setText(jsonBeans.get(position).getAddress());
        viewHolder.tv_from.setText("系统:" + jsonBeans.get(position).getFromroot());
        viewHolder.month.setText(jsonBeans.get(position).getCreatedAt().substring(5, 7));
        viewHolder.day.setText(jsonBeans.get(position).getCreatedAt().substring(8, 10));


        viewHolder.linear_assist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点赞
                Intent intent = new Intent();
                intent.putExtra("uuid", jsonBeans.get(position).getUuid());
                intent.setClass(context, AssistsActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.linear_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("uuid", jsonBeans.get(position).getUuid());
                intent.setClass(context, CommontActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.myheadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("userid", jsonBeans.get(position).getUsername().getUsername());
                intent.putExtra("objectId", jsonBeans.get(position).getUsername().getObjectId());
                intent.setClass(context, OtherInfoDetailActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.linear_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindowbyshare(v, position);
            }
        });
        if (jsonBeans.get(position).getPhotos().size() == 1) {
            viewHolder.img_photo1.setVisibility(View.VISIBLE);
            viewHolder.img_photo2.setVisibility(View.INVISIBLE);
            viewHolder.img_photo3.setVisibility(View.INVISIBLE);
            viewHolder.img_photo4.setVisibility(View.INVISIBLE);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(0), R.drawable.default_head, viewHolder.img_photo1, null);
        } else if (jsonBeans.get(position).getPhotos().size() == 2) {
            viewHolder.img_photo3.setVisibility(View.INVISIBLE);
            viewHolder.img_photo4.setVisibility(View.INVISIBLE);
            viewHolder.img_photo1.setVisibility(View.VISIBLE);
            viewHolder.img_photo2.setVisibility(View.VISIBLE);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(0), R.drawable.default_head, viewHolder.img_photo1, null);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(1), R.drawable.default_head, viewHolder.img_photo2, null);
        } else if (jsonBeans.get(position).getPhotos().size() == 3) {
            viewHolder.img_photo4.setVisibility(View.INVISIBLE);
            viewHolder.img_photo1.setVisibility(View.VISIBLE);
            viewHolder.img_photo2.setVisibility(View.VISIBLE);
            viewHolder.img_photo3.setVisibility(View.VISIBLE);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(0), R.drawable.default_head, viewHolder.img_photo1, null);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(1), R.drawable.default_head, viewHolder.img_photo2, null);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(2), R.drawable.default_head, viewHolder.img_photo3, null);
        } else if (jsonBeans.get(position).getPhotos().size() == 4) {
            viewHolder.img_photo1.setVisibility(View.VISIBLE);
            viewHolder.img_photo2.setVisibility(View.VISIBLE);
            viewHolder.img_photo3.setVisibility(View.VISIBLE);
            viewHolder.img_photo4.setVisibility(View.VISIBLE);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(0), R.drawable.default_head, viewHolder.img_photo1, null);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(1), R.drawable.default_head, viewHolder.img_photo2, null);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(2), R.drawable.default_head, viewHolder.img_photo3, null);
            ViewUtil.setPicture(context, jsonBeans.get(position).getPhotos().get(3), R.drawable.default_head, viewHolder.img_photo4, null);
        }
        viewHolder.tv_nickname.setText(jsonBeans.get(position).getUsername().getNickName());
        ViewUtil.setPicture(context, jsonBeans.get(position).getUsername().getHeadImage().getFileUrl(context), R.drawable.default_head, viewHolder.myheadIcon, null);
        viewHolder.daytime.setText(jsonBeans.get(position).getCreatedAt().substring(11, jsonBeans.get(position).getCreatedAt().length() - 3));
        return convertView;
    }

    class ViewHolder {
        TextView tv_content;
        TextView tv_useraddress;
        TextView tv_from;
        TextView month, day;
        ImageView img_photo1, img_photo2, img_photo3, img_photo4;
        TextView tv_laud;
        TextView tv_nickname;
        ImageView myheadIcon;
        TextView daytime;
        LinearLayout linear_share, linear_step, linear_assist;
        TextView tv_countcommon;

    }

    private PopupWindow window;

    private void showPopwindowbyshare(View vs, final int position) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_share_activity1, null);
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //        设置弹出窗体需要软键盘，
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        再设置模式，和Activity的一样，覆盖，调整大小。
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout ll_viewLayout = (LinearLayout) view.findViewById(R.id.ll_botton);
        View view2 = (View) view.findViewById(R.id.nothing_view);
        view2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                window.dismiss();

            }
        });

        TextView tv_wechat = (TextView) ll_viewLayout.findViewById(R.id.tv_wechat);
        tv_wechat.setOnClickListener(new View.OnClickListener() {

                                         @Override
                                         public void onClick(View v) {
                                             window.dismiss();
                                             new Thread(new Runnable() {
                                                 @Override
                                                 public void run() {
                                                     wechatShare(context, "0", "http://jianyuan.bmob.cn/", "捡缘", jsonBeans.get(position).getContent(), jsonBeans.get(position).getPhotos().get(0));
                                                 }
                                             }).start();

                                         }
                                     }

        );
        TextView tv_wechatfriend = (TextView) ll_viewLayout.findViewById(R.id.tv_wechatfriend);
        tv_wechatfriend.setOnClickListener(new View.OnClickListener()

                                           {

                                               @Override
                                               public void onClick(View v) {
                                                   window.dismiss();
                                                   new Thread(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           wechatShare(context, "1", "http://jianyuan.bmob.cn/", jsonBeans.get(position).getContent(), jsonBeans.get(position).getContent(), jsonBeans.get(position).getPhotos().get(0));
                                                       }
                                                   }).start();
                                               }
                                           }

        );
        TextView tv_qq = (TextView) ll_viewLayout.findViewById(R.id.tv_qq);
        tv_qq.setOnClickListener(new View.OnClickListener()

                                 {

                                     @Override
                                     public void onClick(View v) {
                                         window.dismiss();
                                         Toast.makeText(context, "QQ", Toast.LENGTH_LONG).show();
                                     }
                                 }

        );
        TextView tv_qzone = (TextView) ll_viewLayout.findViewById(R.id.tv_qzone);
        tv_qzone.setOnClickListener(new View.OnClickListener()

                                    {

                                        @Override
                                        public void onClick(View v) {
                                            window.dismiss();
                                            Toast.makeText(context, "空间", Toast.LENGTH_LONG).show();
                                        }
                                    }

        );
        TextView tv_sina = (TextView) ll_viewLayout.findViewById(R.id.tv_sina);
        tv_sina.setOnClickListener(new View.OnClickListener()

                                   {

                                       @Override
                                       public void onClick(View v) {
                                           window.dismiss();
                                           Toast.makeText(context, "微博", Toast.LENGTH_LONG).show();
                                       }
                                   }

        );
        TextView tv_more = (TextView) ll_viewLayout.findViewById(R.id.tv_more);
        tv_more.setOnClickListener(new View.OnClickListener()

                                   {

                                       @Override
                                       public void onClick(View v) {
                                           window.dismiss();
                                           Intent intent = new Intent();
                                           intent.setAction(Intent.ACTION_SEND);
                                           intent.putExtra(Intent.EXTRA_TEXT, jsonBeans.get(position).getContent() + "#捡缘#");
                                           intent.setType("text/plain");
                                           context.startActivity(intent);
//                                           Toast.makeText(context, "更多", Toast.LENGTH_LONG).show();
                                       }
                                   }

        );
        TextView tv_share_back = (TextView) ll_viewLayout.findViewById(R.id.share_back);
        tv_share_back.setOnClickListener(new View.OnClickListener()

                                         {

                                             @Override
                                             public void onClick(View v) {
                                                 // TODO Auto-generated method stub
                                                 window.dismiss();
                                             }
                                         }

        );


        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        window.showAtLocation(vs, Gravity
                .BOTTOM, 0, 0);

        window.setOnDismissListener(new PopupWindow.OnDismissListener()

                                    {

                                        @Override
                                        public void onDismiss() {
                                        }
                                    }

        );
    }

    private static String url;

    public static void wechatShare(Context context, String type, String titleUrl, String title, String shareContent,
                                   String imgurl) {
        boolean flag = BaseApplication.getInstance().getWxApi().isWXAppInstalled();
        if (!flag) {
            Looper.prepare();
            Toast.makeText(context, "没有检测到微信客户端", Toast.LENGTH_SHORT).show();
            Looper.loop();
            return;
        }

        url = titleUrl;

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = shareContent;
        Bitmap thumb = null;
        if (null == imgurl || imgurl.equals("")) {
            thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        } else {
            BufferedInputStream bais;
            try {
                URL imageurl = new URL(imgurl);
                bais = new BufferedInputStream(imageurl.openStream());
                thumb = BitmapFactory.decodeStream(bais);
                int rawHeight = thumb.getHeight();
                int rawWidth = thumb.getWidth();
                int newHeight = 200;
                int newWidth = 200;
                float widthScale = ((float) newWidth) / rawWidth;
                float heightScale = ((float) newHeight) / rawHeight;
                Matrix matrix = new Matrix();
                matrix.postScale(widthScale, heightScale);
                thumb = Bitmap.createBitmap(thumb, 0, 0, rawWidth, rawHeight, matrix, true);
            } catch (Exception e) {
                e.printStackTrace();
                thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            }
        }
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if ("0".equals(type)) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if ("1".equals(type)) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        BaseApplication.getInstance().getWxApi().sendReq(req);
        if (null != thumb && !thumb.isRecycled()) {
            thumb.recycle();
        }
    }
}

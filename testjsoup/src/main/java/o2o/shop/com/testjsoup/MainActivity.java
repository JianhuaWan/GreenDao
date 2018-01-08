package o2o.shop.com.testjsoup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
{

    private ListView list;
    private List<TestBean> testBeans = new ArrayList<>();
    TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        adapter = new TestAdapter(MainActivity.this, testBeans);
        list.setAdapter(adapter);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                loadDate();
            }
        }).start();

    }


    private void loadDate()
    {
        try
        {
            //从一个URL加载一个Document对象。
            Document doc = Jsoup.connect("https://www.bmob.cn/cloud").get();

            Element container = doc.getElementsByAttributeValue("class", "section2").first();


            Elements tableLineInfos = container.select("ul").get(0).children();
            for(Element lineInfo : tableLineInfos)
            {
                Elements tableLi = lineInfo.select("li");
                Elements tableLis = tableLi.select(".banxin");
                Elements title = tableLis.select("h3");
                Elements url = tableLis.select("img");
                String url1 = url.get(0).attr("src");//截取图片
                Elements detail = tableLis.select("p");
                TestBean testBean = new TestBean();
                testBean.setTitle(title.text());
                testBean.setPhotourl(url1);
                testBean.setDetail(detail.text());
                testBeans.add(testBean);
                System.out.println("jsoup is :" + tableLis.text());
            }
            handler.sendEmptyMessage(100);
        }

        catch(Exception e)
        {
            Log.i("mytag", e.toString());
        }
    }

    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 100:
                    adapter.setData(testBeans);
                    break;
                default:
                    break;
            }
        }
    };


    class TestAdapter extends BaseAdapter
    {
        private LayoutInflater inflater;
        private List<TestBean> testBeans = new ArrayList<>();

        public TestAdapter(Context context, List<TestBean> testBeans)
        {
            super();
            this.testBeans = testBeans;
            this.inflater = LayoutInflater.from(MainActivity.this);
        }

        @Override
        public int getCount()
        {
            return testBeans.size();
        }

        @Override
        public TestBean getItem(int position)
        {
            return testBeans.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        public void setData(List<TestBean> jsonBeans)
        {
            this.testBeans = jsonBeans;
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;
            if(convertView == null)
            {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.test_item, null);
                viewHolder.imgageview = (ImageView) convertView.findViewById(R.id.img_photo);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_title.setText(testBeans.get(position).getTitle());
            viewHolder.tv_detail.setText(testBeans.get(position).getDetail());
            Picasso.with(MainActivity.this).load(testBeans.get(position).getPhotourl()).into(viewHolder.imgageview);
            return convertView;
        }

        class ViewHolder
        {
            TextView tv_title, tv_detail;
            ImageView imgageview;
        }
    }
}

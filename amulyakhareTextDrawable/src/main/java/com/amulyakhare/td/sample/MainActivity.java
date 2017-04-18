package com.amulyakhare.td.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.amulyakhare.td.R;
import com.amulyakhare.td.sample.sample.DataItem;
import com.amulyakhare.td.sample.sample.DataSource;

public class MainActivity extends Activity {

	public static final String TYPE = "TYPE";
	private DataSource mDataSource;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.listView);
		mDataSource = new DataSource(this);
		mListView.setAdapter(new SampleAdapter());
	}

	private class SampleAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mDataSource.getCount();
		}

		@Override
		public DataItem getItem(int position) {
			return mDataSource.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(MainActivity.this,
						R.layout.list_item_layout, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			DataItem item = getItem(position);

			final Drawable drawable = item.getDrawable();
			holder.imageView.setImageDrawable(drawable);
			holder.textView.setText(item.getLabel());

			holder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
					null, null);

			// fix for animation not playing for some below 4.4 devices
			if (drawable instanceof AnimationDrawable) {
				holder.imageView.post(new Runnable() {
					@Override
					public void run() {
						((AnimationDrawable) drawable).stop();
						((AnimationDrawable) drawable).start();
					}
				});
			}

			return convertView;
		}
	}

	private static class ViewHolder {

		private ImageView imageView;

		private TextView textView;

		private ViewHolder(View view) {
			imageView = (ImageView) view.findViewById(R.id.imageView);
			textView = (TextView) view.findViewById(R.id.textView);
		}
	}
}

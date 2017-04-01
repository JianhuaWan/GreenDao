package com.xiangyue.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.util.Log;

public class Assets {
	private static final String ENCODING = "UTF-8";
	private Context context;

	public Assets(Context context) {
		this.context = context;
	}

	// 从assets 文件夹中获取文件并读取数�?
	public String getFromAssets(String fileName) throws IOException {
		InputStream in = context.getResources().getAssets().open(fileName);
		// 获取文件的字节数
		int lenght = in.available();
		// 创建byte数组
		byte[] buffer = new byte[lenght];
		// 将文件中的数据读到byte数组�?
		in.read(buffer);
		String result = EncodingUtils.getString(buffer, ENCODING);
		Log.i("info", "result=" + result);
		in.close();
		return result;
	}
	/**
	 * 从Raw 文件夹中获取文件并读取数�?
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	/*
	 * public String getFromRaw(String fileName) throws IOException {
	 * InputStream in = context.getResources().openRawResource(R.raw.date);
	 * //获取文件的字节数 int lenght = in.available(); //创建byte数组 byte[] buffer = new
	 * byte[lenght]; //将文件中的数据读到byte数组�? in.read(buffer); String result =
	 * EncodingUtils.getString(buffer, ENCODING);
	 * Log.i("info","result="+result); in.close(); return result; }
	 */
}

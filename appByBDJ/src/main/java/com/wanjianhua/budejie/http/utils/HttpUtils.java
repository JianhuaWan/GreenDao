package com.wanjianhua.budejie.http.utils;

import android.util.Log;

import com.wanjianhua.budejie.http.IHttpRequestParam;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ying on 2016/6/16.
 */
public class HttpUtils {
    private static final int CONNECT_TIME_OUT = 1000 * 20;
    private static final int READ_TIME_OUT = 1000 * 20;

    public static String get(String urlAddress, Map<String, Object> headMap) {
        if (headMap == null) {
            headMap = new HashMap<>();
        }
        try {
            HttpURLConnection connect = getConnect(urlAddress, headMap, "GET");
            connect.connect();
            int code = connect.getResponseCode();
            if (code == 200) {
                byte[] bytes = StreamTool.inputStream(connect.getInputStream());

//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connect.getInputStream(),"UTF-8"));
////                BufferedInputStream bis = new BufferedInputStream(connect.getInputStream());
//                StringBuffer sb = new StringBuffer();
////                byte[] buf = new bute
//                String line = null;
//                while ( (line = bufferedReader.readLine())!=null){
//                    sb.append(line);
//                }
//                bufferedReader.close();
//                return sb.toString();

                return new String(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String get(String urlAddress) {
        return get(urlAddress, null);
    }

    /**
     * @param urlAddress   请求地址
     * @param requestParam 请求参数  包含请求头参数
     * @return
     */
    public static String post(String urlAddress, IHttpRequestParam<Map<String, Object>, Map<String, Object>> requestParam) {
        Map<String, Object> requestParam1 = requestParam.getRequestParam();
        String paramToString = paramToString(requestParam1);
        Log.d(HttpUtils.class.getSimpleName(), "请求接口：" + urlAddress + "  参数:" + paramToString);
        try {
            HttpURLConnection connection = getConnect(urlAddress, requestParam.getHeaderParam(), "POST");
            connection.connect();
            StreamTool.outputStream(connection.getOutputStream(), paramToString);
            int code = connection.getResponseCode();
            if (code == 200) {
                return StreamTool.inputStreamReader(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param urlAddress    请求地址
     * @param headMap       请求头信息
     * @param requestMethod 请求方式
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnect(String urlAddress, Map<String, Object> headMap, String requestMethod) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setConnectTimeout(CONNECT_TIME_OUT);
        connection.setReadTimeout(READ_TIME_OUT);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        Set<String> set = headMap.keySet();
        for (String s : set) {
            connection.setRequestProperty(s, headMap.get(s).toString());
        }
        connection.setRequestProperty("Charset", "UTF-8");
        return connection;
    }

    private static String paramToString(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = map.keySet();
        for (String s : set) {
            sb.append(s);
            sb.append("=");
            sb.append(map.get(s));
            sb.append("&");
        }
        String substring = sb.substring(0, sb.length() - 1);
        return substring;
    }
}

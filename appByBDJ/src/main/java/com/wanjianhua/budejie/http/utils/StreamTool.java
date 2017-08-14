package com.wanjianhua.budejie.http.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by ying on 2016/6/21.
 */
public class StreamTool {

    public static void outputStream(OutputStream out, String string) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(out);
        bos.write(string.getBytes());
        bos.flush();
        bos.close();
    }

    public static byte[] inputStream(InputStream in) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024 * 10];
        while ((bis.read(buf)) != -1) {
            bos.write(buf, 0, buf.length);
        }
        bos.close();
        byte[] data = bos.toByteArray();
        return data;
    }

    public static String inputStreamReader(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        return sb.toString();
    }
}

package com.xiangyue.video;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 本地代理Socket
 */
public class PlayerSocketServer {

    private PlayerSocketServer() {
    }

    /**
     * 单例
     */
    private static PlayerSocketServer singleTon;

    /**
     * 启动Socket服务器的线程
     */
    private Thread socketThread;

    /**
     * Socket服务器
     */
    private ServerSocket mServerSocket;

    /**
     * 读取视频流的线程
     */
    private HttpThread mHttpThread;

    /**
     * 远程服务器地址
     */
    private String remoteUrl = "";

    /**
     * Cookie
     */
    private String cookie;

    /**
     * 网络回调
     */
    private IPlayerSocketServer listener;
    private Context mcontext;

    public void setListener(IPlayerSocketServer listener) {
        this.listener = listener;
    }

    public interface IPlayerSocketServer {

        /**
         * 连接远程服务器出错
         */
        public void onError();
    }

    /**
     * 单例不解释
     *
     * @return
     */
    public static PlayerSocketServer getSingleTon() {
        if (singleTon == null) {
            singleTon = new PlayerSocketServer();
        }
        // 创建Socket服务器
        if (singleTon.socketThread == null || !singleTon.socketThread.isAlive()) {
            singleTon.startSocketServer();
        }

        return singleTon;
    }

    /**
     * 开启Socket服务器
     */
    private void startSocketServer() {
        socketThread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    mServerSocket = new ServerSocket(0);
                    while (true) {
                        System.out.println("-------333-----");
                        final Socket s = mServerSocket.accept();
                        if (mHttpThread != null) {
                            mHttpThread.flag = false;
                            mHttpThread.interrupt();
                        }
                        mHttpThread = new HttpThread(s, remoteUrl);
                        mHttpThread.start();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        singleTon.socketThread.start();
    }

    /**
     * 检查服务器是否正在运行
     *
     * @return true为正在运行
     */
    public boolean isServerRunning() {
        return mServerSocket != null;
    }

    /**
     * 获得本地Socket地址
     *
     * @param url    远程服务器视频地址
     * @param cookie
     * @return 本地Socket地址
     */
    public String getLocalUrl(Context context, String url, String cookie) {
        this.cookie = cookie;
        mcontext = context;
        remoteUrl = url;
        return "http://127.0.0.1:" + mServerSocket.getLocalPort();
    }

    /**
     * 释放单例，Socket服务器
     */
    public static void release() {
        if (singleTon != null && singleTon.mServerSocket != null) {
            try {
                singleTon.mServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        singleTon = null;
    }

    /**
     * 中转流线程
     */
    private class HttpThread extends Thread {

        /**
         * 控制线程状态
         */
        private boolean flag;

        /**
         * 远程服务器视频地址
         */
        private String remoteUrl;

        /**
         * Socket，视频流往这里写
         */
        private Socket s;

        /**
         * 读取范围
         */
        private byte[] b;

        /**
         * 每次读取长度
         */
        private int n;

        /**
         * 流
         */
        private BufferedReader br;

        /**
         * 流
         */
        private OutputStream os;

        /**
         * 用于保存API发送过来的请求头内容
         */
        private String str;

        public HttpThread(Socket s, String url) {
            flag = true;
            this.s = s;
            this.remoteUrl = url;
            b = new byte[4096];
        }

        @Override
        public void run() {
            try {

                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                long temp = s.getInputStream().available();
                os = s.getOutputStream();
                str = "";
                // String path =
                // Environment.getExternalStorageDirectory().getPath() +
                // "/e.mp4";
                // FileOutputStream fos = new FileOutputStream(new File(path));
                URL url = new URL(remoteUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                while ((str = br.readLine()) != null && flag) {
                    System.out.println("------------" + str);
                    if (str.trim().equals("")) {// 结束
                        // 检查网络连接状态
                        // IFileInputStream in = null;
                        InputStream his = null;
                        // String filepath =
                        // Environment.getExternalStorageDirectory().getPath() +
                        // "/hai1.mp4";
                        // File file = new File(filepath);// 原始文件路徑
                        // if (!file.exists())
                        // file.createNewFile();
                        // IFile iFile =
                        // IDeskService.iDeskFile(Environment.getExternalStorageDirectory().getPath()
                        // + "/qwe.mp4");
                        // long size = iFile.length();
                        // Log.d("~", iFile.getName() + ":" + size + "B");
                        // in = IDeskService.iDeskInputStream(iFile);
                        // his =
                        // mcontext.getResources().getAssets().open("VideRecordDemo.mp4");
                        // his = new
                        // FileInputStream(Environment.getExternalStorageDirectory().getPath()
                        // + "/a.mp4");
                        try {
                            connection.setRequestMethod("GET");
                            connection.setDoInput(true);
                            connection.setRequestProperty("Cookie", cookie);
                            his = connection.getInputStream();
                            Map<String, List<String>> map = connection.getHeaderFields();
                            for (Entry<String, List<String>> entry : map.entrySet()) {
                                if (writeResponeHeader(os, entry)) {
                                    os.write("\r\n".getBytes("utf-8"));
                                }
                            }
                            os.write("\r\n".getBytes("utf-8"));

                            System.out.println("w1");
                            // long h = in.available();
                            while (flag) {
                                if ((n = his.read(b)) != -1) {
                                    os.write(b, 0, n);
                                    // for (byte cc : b) {
                                    // System.out.print(" "+(char)cc);
                                    // }
                                } else {
                                    flag = false;
                                    break;
                                }
                                // for (byte cc : b) {
                                // System.out.print(" " + (char) cc);
                                // }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            // if (flag) {
                            // System.out.println(e.toString());
                            // listener.onError();
                            // writeHttpError(os);
                            //
                            // }
                            flag = false;
                            return;
                        } finally {
                            // if (connection != null) {
                            // connection.disconnect();
                            // }
                        }
                    } else if (str.contains(":")) {
                        // API发送请求头里的HOST和Cookie头都不用塞给远程服务器
                        if (!str.contains("Host") && !str.contains("Cookie")) {
                            // parseHttpRequest(connection, str);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (flag) {
                    listener.onError();
                    writeHttpError(os);
                }
            } finally {
                try {
                    s.shutdownInput();
                    s.shutdownOutput();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 解析Http请求头数据，塞进连接远程服务器的连接
         *
         * @param connect    连接远程服务器的连接
         * @param requestStr 每行请求头
         */
        private void parseHttpRequest(HttpURLConnection connect, String requestStr) {
            String[] keyValue = requestStr.split(":");
            connect.addRequestProperty(keyValue[0].trim(), keyValue[1].trim());
        }

        /**
         * 把远程服务器返回的响应头，塞回给API
         *
         * @param os    塞回API的流
         * @param entry 响应头
         * @throws IOException 异常处理
         */
        private boolean writeResponeHeader(OutputStream os, Entry<String, List<String>> entry) throws IOException {
            String s = "";
            String key = entry.getKey();
            String values = "";
            for (int i = 0; i < entry.getValue().size(); i++) {
                values += entry.getValue().get(i);
                if (i != entry.getValue().size() - 1) {
                    values += ",";
                }
            }
            if (key != null) {
                s += key + ":";
            }
            s += values;
            os.write(s.getBytes("utf-8"));
            return true;
        }

        /**
         * @param os
         */
        private void writeHttpError(OutputStream os) {
            try {
                os.write("HTTP/1.1 200 OK\r\n".getBytes());
                os.write("Connection:close\r\n".getBytes());
                os.write("Content-Length: 0".getBytes());
                os.write("\r\n".getBytes());
                os.write("Content-Type:text/html\r\n".getBytes());
                os.write("\r\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

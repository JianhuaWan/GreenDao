package com.xiangyue.util;

import android.os.Build;

/**
 * 项目名称：SacureFile 类描述： 创建人：zWX321639 创建时间：2016/3/22 12:08 修改人：zWX321639
 * 修改时间：2016/3/22 12:08 修改备注：@version
 **/

public class SystemInfoTools {
    public static String makeInfoString(String[] description, String[] prop) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prop.length; i++) {
            sb.append(description[i]).append(":").append(prop[i]).append("\r\n");
        }
        return sb.toString();
    }

    public static String getBuildInfo() {
        String board = Build.BOARD;// 型号
        String brand = Build.BRAND;// 品牌
        // String supported_abis = Build.SUPPORTED_ABIS[0];// 处理器
        String device = Build.DEVICE;// 操作系统号

        return device;
    }

    public static String getSystemPropertyInfo() {
        String os_version = System.getProperty("os.version");
        String os_name = System.getProperty("os.name");
        String os_arch = System.getProperty("os.arch");
        String user_home = System.getProperty("user.home");
        String user_name = System.getProperty("user.name");
        String user_dir = System.getProperty("user.dir");
        String user_timezone = System.getProperty("user.timezone");
        String path_separator = System.getProperty("path.separator");
        String line_separator = System.getProperty("line.separator");
        String file_separator = System.getProperty("file.separator");
        String java_vendor_url = System.getProperty("java.vendor.url");
        String java_class_path = System.getProperty("java.class.path");
        String java_class_version = System.getProperty("java.class.version");
        String java_version = System.getProperty("java.version");
        String java_vendor = System.getProperty("java.vendor");
        String java_home = System.getProperty("java.home");
        String[] prop = {os_version, os_name, os_arch, user_home, user_name, user_dir, user_timezone, path_separator,
                line_separator, file_separator, java_vendor_url, java_class_path, java_class_version, java_vendor,
                java_version, java_home};
        String[] description = {"os_version", "os_name", "os_arch", "user_home", "user_name", "user_dir",
                "user_timezone", "path_separator", "line_separator", "file_separator", "java_vendor_url",
                "java_class_path", "java_class_version", "java_vendor", "java_version", "java_home"};
        return makeInfoString(description, prop);
    }
}

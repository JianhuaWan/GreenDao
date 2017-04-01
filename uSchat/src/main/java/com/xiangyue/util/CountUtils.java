package com.xiangyue.util;

/**
 * Created by wWX321637 on 2016/5/31.
 */
public class CountUtils {
    public static int Count(int nowcount) {
        if (nowcount <= 9) {
            return 0;
        } else if (nowcount >= 10 && nowcount <= 29) {
            return 1;
        } else if (nowcount >= 30 && nowcount <= 59) {
            return 2;
        } else if (nowcount >= 60 && nowcount <= 89) {
            return 3;
        } else if (nowcount >= 90 && nowcount <= 119) {
            return 4;
        } else if (nowcount >= 120 && nowcount <= 149) {
            return 5;
        } else if (nowcount >= 150 && nowcount <= 179) {
            return 6;
        } else if (nowcount >= 180 && nowcount <= 209) {
            return 7;
        } else if (nowcount >= 210 && nowcount <= 239) {
            return 8;
        } else if (nowcount >= 240 && nowcount <= 269) {
            return 9;
        } else if (nowcount >= 270 && nowcount <= 299) {
            return 10;
        } else if (nowcount >= 300 && nowcount <= 329) {
            return 11;
        } else if (nowcount >= 330 && nowcount <= 359) {
            return 12;
        } else if (nowcount >= 360 && nowcount <= 389) {
            return 13;
        } else if (nowcount >= 390 && nowcount <= 419) {
            return 14;
        } else if (nowcount >= 420 && nowcount <= 449) {
            return 15;
        } else if (nowcount >= 450 && nowcount <= 479) {
            return 16;
        } else if (nowcount >= 480 && nowcount <= 509) {
            return 17;
        } else if (nowcount >= 510 && nowcount <= 539) {
            return 18;
        } else if (nowcount >= 540 && nowcount <= 569) {
            return 19;
        } else if (nowcount >= 570 && nowcount <= 599) {
            return 20;
        } else {
            return 20;
        }
    }

}

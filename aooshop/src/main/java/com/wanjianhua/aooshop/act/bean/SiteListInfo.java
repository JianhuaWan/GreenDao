package com.wanjianhua.aooshop.act.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteListInfo implements Serializable {
    private String name;//名称
    private String code;//代码
    private String price;//买入价格
    private String totalprice;//总投入
    private String appreciate;//涨幅
    private List<Echelon> echelons;//梯队信息

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getAppreciate() {
        return appreciate;
    }

    public void setAppreciate(String appreciate) {
        this.appreciate = appreciate;
    }

    public List<Echelon> getEchelons() {
        return echelons;
    }

    public void setEchelons(List<Echelon> echelons) {
        this.echelons = echelons;
    }

    class Echelon implements Serializable {
        private List<Waveband> wavebands;//波段信息

        public List<Waveband> getWavebands() {
            return wavebands;
        }

        public void setWavebands(List<Waveband> wavebands) {
            this.wavebands = wavebands;
        }

        class Waveband implements Serializable {
            private String nowprice;
            private String nowappreciate;
            private String singlecount;
            private String singleprice;
            private String maxwin;
            private String maxloser;

            public String getNowprice() {
                return nowprice;
            }

            public void setNowprice(String nowprice) {
                this.nowprice = nowprice;
            }

            public String getNowappreciate() {
                return nowappreciate;
            }

            public void setNowappreciate(String nowappreciate) {
                this.nowappreciate = nowappreciate;
            }

            public String getSinglecount() {
                return singlecount;
            }

            public void setSinglecount(String singlecount) {
                this.singlecount = singlecount;
            }

            public String getSingleprice() {
                return singleprice;
            }

            public void setSingleprice(String singleprice) {
                this.singleprice = singleprice;
            }

            public String getMaxwin() {
                return maxwin;
            }

            public void setMaxwin(String maxwin) {
                this.maxwin = maxwin;
            }

            public String getMaxloser() {
                return maxloser;
            }

            public void setMaxloser(String maxloser) {
                this.maxloser = maxloser;
            }
        }
    }
}

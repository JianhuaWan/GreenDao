package com.wanjianhua.aooshop.act.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 6005001713 on 2017/12/19.
 */

public class ShopInfo implements Serializable {
    /**
     * result : 1000
     * data : {"id":3,"activityName":"Double-deck Snack Storage Box Melon Seeds Container","img":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175837_15068.jpg","activityType":null,"status":null,"target":3,"num":2,"startDate":1514005338000,"endDate":1520053338000,"discount":0,"describesHtml":null,"highlightsHtml":null,"stock":0,"spuId":7,"imgs":[{"skuImgId":1471,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175837_15068.jpg","skuId":11817},{"skuImgId":1472,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175838_22608.jpg","skuId":11817},{"skuImgId":1473,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175838_78782.jpg","skuId":11817},{"skuImgId":1474,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175839_66527.jpg","skuId":11817},{"skuImgId":1476,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175846_11999.jpg","skuId":11817},{"skuImgId":1477,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175846_44162.jpg","skuId":11817}]}
     */

    private int result;
    private DataBean data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * activityName : Double-deck Snack Storage Box Melon Seeds Container
         * img : https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175837_15068.jpg
         * activityType : null
         * status : null
         * target : 3
         * num : 2
         * startDate : 1514005338000
         * endDate : 1520053338000
         * discount : 0
         * describesHtml : null
         * highlightsHtml : null
         * stock : 0
         * spuId : 7
         * imgs : [{"skuImgId":1471,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175837_15068.jpg","skuId":11817},{"skuImgId":1472,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175838_22608.jpg","skuId":11817},{"skuImgId":1473,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175838_78782.jpg","skuId":11817},{"skuImgId":1474,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175839_66527.jpg","skuId":11817},{"skuImgId":1476,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175846_11999.jpg","skuId":11817},{"skuImgId":1477,"imgUrl":"https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175846_44162.jpg","skuId":11817}]
         */

        private int id;
        private String activityName;
        private String img;
        private Object activityType;
        private Object status;
        private int target;
        private int num;
        private long startDate;
        private long endDate;
        private int discount;
        private Object describesHtml;
        private Object highlightsHtml;
        private int stock;
        private int spuId;
        private List<ImgsBean> imgs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getActivityType() {
            return activityType;
        }

        public void setActivityType(Object activityType) {
            this.activityType = activityType;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public long getEndDate() {
            return endDate;
        }

        public void setEndDate(long endDate) {
            this.endDate = endDate;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public Object getDescribesHtml() {
            return describesHtml;
        }

        public void setDescribesHtml(Object describesHtml) {
            this.describesHtml = describesHtml;
        }

        public Object getHighlightsHtml() {
            return highlightsHtml;
        }

        public void setHighlightsHtml(Object highlightsHtml) {
            this.highlightsHtml = highlightsHtml;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getSpuId() {
            return spuId;
        }

        public void setSpuId(int spuId) {
            this.spuId = spuId;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public static class ImgsBean {
            /**
             * skuImgId : 1471
             * imgUrl : https://glodimg.chinabrands.com/pdm-product-pic/Distribution/2017/07/17/source-img/20170717175837_15068.jpg
             * skuId : 11817
             */

            private int skuImgId;
            private String imgUrl;
            private int skuId;

            public int getSkuImgId() {
                return skuImgId;
            }

            public void setSkuImgId(int skuImgId) {
                this.skuImgId = skuImgId;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getSkuId() {
                return skuId;
            }

            public void setSkuId(int skuId) {
                this.skuId = skuId;
            }
        }
    }
}

package sinia.com.smartmart.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/19.
 */
public class CartBean extends JsonBean {


    /**
     * merName : 水果店
     * items : [{"id":"402880ec569c952c01569c95a9560002","price":971.5,"goodNum":29,"goodName":"http://bmob-cdn-5631
     * .b0.upaiyun.com/2016/08/17/fce8cc8b99e24b73b11b057b26a80083.png","goodImage":"水萝卜"}]
     * merchantId : 402880eb56967f7401569680e041000f
     */

    private List<MerchantitemsBean> merchantitems;
    /**
     * goodId : http://bmob-cdn-5621.b0.upaiyun.com/2016/08/17/2ed9ea9afde945bd8602cc67f55b59c7.jpg
     * goodName : 西奈大西瓜
     * goodImage : http://bmob-cdn-5621.b0.upaiyun.com/2016/08/17/2ed9ea9afde945bd8602cc67f55b59c7.jpg
     */

    private List<HisitemsBean> hisitems;

    public List<MerchantitemsBean> getMerchantitems() {
        return merchantitems;
    }

    public void setMerchantitems(List<MerchantitemsBean> merchantitems) {
        this.merchantitems = merchantitems;
    }

    public List<HisitemsBean> getHisitems() {
        return hisitems;
    }

    public void setHisitems(List<HisitemsBean> hisitems) {
        this.hisitems = hisitems;
    }

    public static class MerchantitemsBean {
        private String merName;
        private String merchantId;
        private boolean isChecked = false;
        /**
         * id : 402880ec569c952c01569c95a9560002
         * price : 971.5
         * goodNum : 29
         * goodName : http://bmob-cdn-5631.b0.upaiyun.com/2016/08/17/fce8cc8b99e24b73b11b057b26a80083.png
         * goodImage : 水萝卜
         */

        private List<GoodsItemsBean> items;

        public String getMerName() {
            return merName;
        }

        public void setMerName(String merName) {
            this.merName = merName;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public List<GoodsItemsBean> getItems() {
            return items;
        }

        public void setItems(List<GoodsItemsBean> items) {
            this.items = items;
        }

        public static class GoodsItemsBean implements Serializable {
            private String id;
            private String goodId;
            private double price;
            private int goodNum;//商品重量
            private int num;//商品数量
            private String goodStatus;//1.上架 2，下架 3.删除
            private String unit;
            private String goodName;
            private String goodImage;
            private String normName;
            private boolean isChecked = false;

            public String getGoodStatus() {
                return goodStatus;
            }

            public void setGoodStatus(String goodStatus) {
                this.goodStatus = goodStatus;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getNormName() {
                return normName;
            }

            public void setNormName(String normName) {
                this.normName = normName;
            }

            public String getGoodId() {
                return goodId;
            }

            public void setGoodId(String goodId) {
                this.goodId = goodId;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getGoodNum() {
                return goodNum;
            }

            public void setGoodNum(int goodNum) {
                this.goodNum = goodNum;
            }

            public String getGoodName() {
                return goodName;
            }

            public void setGoodName(String goodName) {
                this.goodName = goodName;
            }

            public String getGoodImage() {
                return goodImage;
            }

            public void setGoodImage(String goodImage) {
                this.goodImage = goodImage;
            }
        }
    }

    public static class HisitemsBean {
        private String goodId;
        private String goodName;
        private String goodImage;

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public String getGoodImage() {
            return goodImage;
        }

        public void setGoodImage(String goodImage) {
            this.goodImage = goodImage;
        }
    }
}

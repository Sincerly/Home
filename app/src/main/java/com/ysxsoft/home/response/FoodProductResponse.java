package com.ysxsoft.home.response;

import java.util.List;

/**
 * create by Sincerly on 2019/7/2 0002
 **/
public class FoodProductResponse {

    /**
     * data : [{"productid":1,"name":"商品","pic":"http://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","price":"100.00","rule":1}]
     * code : 200
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * productid : 1
         * name : 商品
         * pic : http://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
         * price : 100.00
         * rule : 1
         */

        private String productid;
        private String name;
        private String pic;
        private String price;
        private String rule;
        private String status;
        private String store;
        private int shopCartNum;//在购物车的数量 本地数据库数量

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getRule() {
            return rule;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getShopCartNum() {
            return shopCartNum;
        }

        public void setShopCartNum(int shopCartNum) {
            this.shopCartNum = shopCartNum;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIsrule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }
    }
}

package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/20 0020
 **/
public class ShopProductResponse {

    /**
     * data : [{"productid":4,"name":"ssss","pic":"http://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","price":"100.00","isrule":1},{"productid":1,"name":"商品","pic":"http://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","price":"100.00","isrule":1}]
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
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * productid : 4
         * name : ssss
         * pic : http://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
         * price : 100.00
         * isrule : 1
         */

        private String productid;
        private String name;
        private String pic;
        private String price;
        private String isrule;
        private String store;
        private String c_cateid;//一级id
        private String cateid;//二级id
        private String status;//0仓库中，1上架中，2已售罄
        private int shopCartNum;//在购物车的数量 本地数据库数量

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStore() {
            return store == null ? "" : store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getC_cateid() {
            return c_cateid == null ? "" : c_cateid;
        }

        public void setC_cateid(String c_cateid) {
            this.c_cateid = c_cateid;
        }

        public String getCateid() {
            return cateid == null ? "" : cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
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
            return isrule;
        }

        public void setIsrule(String isrule) {
            this.isrule = isrule;
        }
    }
}

package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/24 0024
 **/
public class GoldProductListResponse {

    /**
     * data : [{"productid":5,"name":"哈哈哈了","pic":"http://jia.sanzhima.cn/uploads/images/20190516/d88c6ed4e5365e5faf550256ab9bdd88.png","price":"100.00"}]
     * code : 200
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
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
         * productid : 5
         * name : 哈哈哈了
         * pic : http://jia.sanzhima.cn/uploads/images/20190516/d88c6ed4e5365e5faf550256ab9bdd88.png
         * price : 100.00
         */
        private String productid;
        private String name;
        private String pic;
        private String price;

        public String getProductid() {
            return productid == null ? "" : productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic == null ? "" : pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/25 0025
 **/
public class GoldOrderResponse {
    /**
     * data : [{"orderid":18,"dsn":"jia1561362194468","gname":"商品啊","account":"100","num":1,"ruleid":10,"status":1,"pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"}]
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
         * orderid : 18
         * dsn : jia1561362194468
         * gname : 商品啊
         * account : 100
         * num : 1
         * ruleid : 10
         * status : 1
         * pic : http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
         */

        private String orderid;
        private String dsn;
        private String gname;
        private String account;
        private String price;
        private String num;
        private String ruleid;
        private String status;
        private String pic;

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOrderid() {
            return orderid == null ? "" : orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getDsn() {
            return dsn == null ? "" : dsn;
        }

        public void setDsn(String dsn) {
            this.dsn = dsn;
        }

        public String getGname() {
            return gname == null ? "" : gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getAccount() {
            return account == null ? "" : account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getNum() {
            return num == null ? "" : num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getRuleid() {
            return ruleid == null ? "" : ruleid;
        }

        public void setRuleid(String ruleid) {
            this.ruleid = ruleid;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPic() {
            return pic == null ? "" : pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}

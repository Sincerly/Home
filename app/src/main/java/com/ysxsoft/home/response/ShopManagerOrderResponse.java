package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/7/2 0002
 **/
public class ShopManagerOrderResponse {

    /**
     * code : 200
     * data : [{"orderid":10,"dsn":"jia1559185851811","num":2,"account":"240","status":1,"tui":0,"type":2,"product":[{"gname":"商品","gid":1,"rule_name":"白色,xll","price":"120.00","num":2,"ruleid":2,"pic":"http://jia.sanzhima.cn/uploads/images/20190506/df46ee4991a6f931e8d2cbae7e47340d.jpg"}]}]
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
         * orderid : 10
         * dsn : jia1559185851811
         * num : 2
         * account : 240
         * status : 1
         * tui : 0
         * type : 2
         * product : [{"gname":"商品","gid":1,"rule_name":"白色,xll","price":"120.00","num":2,"ruleid":2,"pic":"http://jia.sanzhima.cn/uploads/images/20190506/df46ee4991a6f931e8d2cbae7e47340d.jpg"}]
         */

        private String orderid;
        private String dsn;
        private String num;
        private String account;
        private String status;
        private String tui;
        private String type;
        private List<ProductBean> product;

        public String getOrderid() {
            return orderid;
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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAccount() {
            return account == null ? "" : account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTui() {
            return tui;
        }

        public void setTui(String tui) {
            this.tui = tui;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ProductBean> getProduct() {
            if (product == null) {
                return new ArrayList<>();
            }
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ProductBean {
            /**
             * gname : 商品
             * gid : 1
             * rule_name : 白色,xll
             * price : 120.00
             * num : 2
             * ruleid : 2
             * pic : http://jia.sanzhima.cn/uploads/images/20190506/df46ee4991a6f931e8d2cbae7e47340d.jpg
             */

            private String gname;
            private String gid;
            private String rule_name;
            private String price;
            private String num;
            private String ruleid;
            private String pic;

            public String getGname() {
                return gname == null ? "" : gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getGid() {
                return gid == null ? "" : gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getRule_name() {
                return rule_name == null ? "" : rule_name;
            }

            public void setRule_name(String rule_name) {
                this.rule_name = rule_name;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public String getPic() {
                return pic == null ? "" : pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}

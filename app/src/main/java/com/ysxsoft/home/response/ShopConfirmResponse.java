package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/7/1 0001
 **/
public class ShopConfirmResponse {

    /**
     * data : {"quan":0,"order":{"orderid":10,"yun":"0.00","account":"240","address":"","addtime":1559185852,"username":"","mobile":"","addressid":0},"detail":[{"gname":"商品","price":"120.00","rule_name":"白色,xll","ruleid":2,"num":2,"pic":"http://www.jia.com/uploads/images/20190506/df46ee4991a6f931e8d2cbae7e47340d.jpg"}]}
     * code : 200
     * msg : 成功
     */

    private DataBean data;
    private String code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * quan : 0
         * order : {"orderid":10,"yun":"0.00","account":"240","address":"","addtime":1559185852,"username":"","mobile":"","addressid":0}
         * detail : [{"gname":"商品","price":"120.00","rule_name":"白色,xll","ruleid":2,"num":2,"pic":"http://www.jia.com/uploads/images/20190506/df46ee4991a6f931e8d2cbae7e47340d.jpg"}]
         */

        private String quan;
        private OrderBean order;
        private List<DetailBean> detail;
        private String time;

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getQuan() {
            return quan;
        }

        public void setQuan(String quan) {
            this.quan = quan;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<DetailBean> getDetail() {
            if (detail == null) {
                return new ArrayList<>();
            }
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class OrderBean {
            /**
             * orderid : 10
             * yun : 0.00
             * account : 240
             * address :
             * addtime : 1559185852
             * username :
             * mobile :
             * addressid : 0
             */

            private String orderid;
            private String yun;
            private String account;
            private String address;
            private String addtime;
            private String username;
            private String mobile;
            private String addressid;

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getYun() {
                return yun;
            }

            public void setYun(String yun) {
                this.yun = yun;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getAddressid() {
                return addressid;
            }

            public void setAddressid(String addressid) {
                this.addressid = addressid;
            }
        }

        public static class DetailBean {
            /**
             * gname : 商品
             * price : 120.00
             * rule_name : 白色,xll
             * ruleid : 2
             * num : 2
             * pic : http://www.jia.com/uploads/images/20190506/df46ee4991a6f931e8d2cbae7e47340d.jpg
             */

            private String gname;
            private String price;
            private String rule_name;
            private String ruleid;
            private String num;
            private String pic;

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getRule_name() {
                return rule_name;
            }

            public void setRule_name(String rule_name) {
                this.rule_name = rule_name;
            }

            public String getRuleid() {
                return ruleid;
            }

            public void setRuleid(String ruleid) {
                this.ruleid = ruleid;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}

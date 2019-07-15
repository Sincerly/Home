package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/7/2 0002
 **/
public class FoodConfirmOrderReponse {

    /**
     * data : {"quan":2,"order":{"orderid":4,"yun":"","account":"363","address":"河南省郑州市中原区大学科技园","addtime":1559284167,"username":"张三","mobile":"18530080885","bao":"3","num":3},"detail":[{"gname":"商品","price":"120.00","rule_name":"六寸","ruleid":2,"num":3,"pic":"http://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"}]}
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
         * quan : 2
         * order : {"orderid":4,"yun":"","account":"363","address":"河南省郑州市中原区大学科技园","addtime":1559284167,"username":"张三","mobile":"18530080885","bao":"3","num":3}
         * detail : [{"gname":"商品","price":"120.00","rule_name":"六寸","ruleid":2,"num":3,"pic":"http://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"}]
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
            return quan == null ? "" : quan;
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
             * orderid : 4
             * yun :
             * account : 363
             * address : 河南省郑州市中原区大学科技园
             * addtime : 1559284167
             * username : 张三
             * mobile : 18530080885
             * bao : 3
             * num : 3
             */

            private String orderid;
            private String yun;
            private String account;
            private String address;
            private String addressid;
            private String addtime;
            private String username;
            private String mobile;
            private String bao;
            private String num;


            public String getAddressid() {
                return addressid == null ? "" : addressid;
            }

            public void setAddressid(String addressid) {
                this.addressid = addressid;
            }

            public String getOrderid() {
                return orderid == null ? "" : orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getYun() {
                return yun == null ? "" : yun;
            }

            public void setYun(String yun) {
                this.yun = yun;
            }

            public String getAccount() {
                return account == null ? "" : account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getAddress() {
                return address == null ? "" : address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddtime() {
                return addtime == null ? "" : addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getUsername() {
                return username == null ? "" : username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getMobile() {
                return mobile == null ? "" : mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getBao() {
                return bao == null ? "" : bao;
            }

            public void setBao(String bao) {
                this.bao = bao;
            }

            public String getNum() {
                return num == null ? "" : num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }

        public static class DetailBean {
            /**
             * gname : 商品
             * price : 120.00
             * rule_name : 六寸
             * ruleid : 2
             * num : 3
             * pic : http://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
             */

            private String gname;
            private String price;
            private String rule_name;
            private String ruleid;
            private String num;
            private String pic;

            public String getGname() {
                return gname == null ? "" : gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getRule_name() {
                return rule_name == null ? "" : rule_name;
            }

            public void setRule_name(String rule_name) {
                this.rule_name = rule_name;
            }

            public String getRuleid() {
                return ruleid == null ? "" : ruleid;
            }

            public void setRuleid(String ruleid) {
                this.ruleid = ruleid;
            }

            public String getNum() {
                return num == null ? "" : num;
            }

            public void setNum(String num) {
                this.num = num;
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

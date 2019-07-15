package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/26 0026
 **/
public class GoldHistoryDetailResponse {

    /**
     * data : {"orderid":25,"username":"田高琳","mobile":"18739509196","address":"河南省郑州市中原区详细地址","dsn":"jia1561512540469","addtime":"2019-06-26 09:29:00","gname":"哈哈哈了","ruleid":6,"rule_name":"红色,xl","price":"100.00","num":1,"account":"100","pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"}
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
         * orderid : 25
         * username : 田高琳
         * mobile : 18739509196
         * address : 河南省郑州市中原区详细地址
         * dsn : jia1561512540469
         * addtime : 2019-06-26 09:29:00
         * gname : 哈哈哈了
         * ruleid : 6
         * rule_name : 红色,xl
         * price : 100.00
         * num : 1
         * account : 100
         * pic : http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
         */

        private String orderid;
        private String username;
        private String mobile;
        private String address;
        private String dsn;
        private String addtime;
        private String gname;
        private String ruleid;
        private String rule_name;
        private String price;
        private String num;
        private String account;
        private String pic;
        private String status;
        private String payTime;

        public String getPayTime() {
            return payTime == null ? "" : payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDsn() {
            return dsn;
        }

        public void setDsn(String dsn) {
            this.dsn = dsn;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getRuleid() {
            return ruleid;
        }

        public void setRuleid(String ruleid) {
            this.ruleid = ruleid;
        }

        public String getRule_name() {
            return rule_name;
        }

        public void setRule_name(String rule_name) {
            this.rule_name = rule_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}

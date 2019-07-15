package com.ysxsoft.home.response;

import java.util.List;

/**
 * create by Sincerly on 2019/7/1 0001
 **/
public class CanUseCouponResponse {
    /**
     * data : [{"id":1,"uid":5,"coupon_id":1,"time":"2019-06-29","status":0,"addtime":1559200393,"quanid":1,"man":"100","size":"50"}]
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
         * id : 1
         * uid : 5
         * coupon_id : 1
         * time : 2019-06-29
         * status : 0
         * addtime : 1559200393
         * quanid : 1
         * man : 100
         * size : 50
         */

        private String id;
        private String uid;
        private String coupon_id;
        private String time;
        private String status;
        private String addtime;
        private String quanid;
        private String man;
        private String size;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getQuanid() {
            return quanid;
        }

        public void setQuanid(String quanid) {
            this.quanid = quanid;
        }

        public String getMan() {
            return man;
        }

        public void setMan(String man) {
            this.man = man;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}

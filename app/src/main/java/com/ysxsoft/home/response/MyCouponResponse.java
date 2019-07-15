package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/15 0015
 **/
public class MyCouponResponse{

    /**
     * data : [{"quanid":3,"coupon_id":1,"time":"2019-07-11","status":0,"man":"100","size":"50"},{"quanid":4,"coupon_id":2,"time":"2019-07-11","status":0,"man":"200","size":"80"}]
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
         * quanid : 3
         * coupon_id : 1
         * time : 2019-07-11
         * status : 0
         * man : 100
         * size : 50
         */

        private int quanid;
        private int coupon_id;
        private String time;
        private int status;
        private String man;
        private String size;
        private String day;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getQuanid() {
            return quanid;
        }

        public void setQuanid(int quanid) {
            this.quanid = quanid;
        }

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

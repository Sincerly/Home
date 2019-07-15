package com.ysxsoft.home.response;

import java.io.Serializable;
import java.util.List;

/**
 * create by Sincerly on 2019/6/18 0018
 **/
public class CarpoolListResponse implements Serializable{

    /**
     * data : [{"nid":1,"start":"河南省郑州市中原区","end":"河南省驻马店市驿城区","time":"07-01 00:00","addtime":"433062小时前","num":2,"status":0,"car":"","price":"0","mobile":"18530080883","style":0}]
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

    public static class DataBean implements Serializable {
        /**
         * nid : 1
         * start : 河南省郑州市中原区
         * end : 河南省驻马店市驿城区
         * time : 07-01 00:00
         * addtime : 433062小时前
         * num : 2
         * status : 0
         * car :
         * price : 0
         * mobile : 18530080883
         * style : 0
         */

        private String nid;
        private String start;
        private String end;
        private String time;
        private String addtime;
        private String num;
        private String status;
        private String car;
        private String price;
        private String mobile;
        private String style;

        public String getNid() {
            return nid == null ? "" : nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getStart() {
            return start == null ? "" : start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end == null ? "" : end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAddtime() {
            return addtime == null ? "" : addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getNum() {
            return num == null ? "" : num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCar() {
            return car == null ? "" : car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStyle() {
            return style == null ? "" : style;
        }

        public void setStyle(String style) {
            this.style = style;
        }
    }
}

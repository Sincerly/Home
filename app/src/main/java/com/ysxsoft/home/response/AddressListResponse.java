package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/24 0024
 **/
public class AddressListResponse {

    /**
     * data : [{"id":36,"uid":5,"name":"张三","mobile":"18530080883","province":"河南省","city":"郑州市","county":"中原区","detail":"大学科技园","addtime":1559187224,"status":0,"addressid":36}]
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
         * id : 36
         * uid : 5
         * name : 张三
         * mobile : 18530080883
         * province : 河南省
         * city : 郑州市
         * county : 中原区
         * detail : 大学科技园
         * addtime : 1559187224
         * status : 0
         * addressid : 36
         */

        private String id;
        private String uid;
        private String name;
        private String mobile;
        private String province;
        private String city;
        private String county;
        private String detail;
        private String addtime;
        private String status;
        private String addressid;
        private String code;//邮编

        public String getCode() {
            return code == null ? "" : code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid == null ? "" : uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince() {
            return province == null ? "" : province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city == null ? "" : city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county == null ? "" : county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getDetail() {
            return detail == null ? "" : detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getAddtime() {
            return addtime == null ? "" : addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddressid() {
            return addressid == null ? "" : addressid;
        }

        public void setAddressid(String addressid) {
            this.addressid = addressid;
        }
    }
}

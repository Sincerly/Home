package com.ysxsoft.home.response;

import java.util.List;

/**
 * create by Sincerly on 2019/6/21 0021
 **/
public class MyHousingResponse {

    /**
     * data : [{"nid":1,"title":"通和盛世","huxing":"三室一厅","size":"90","address":"河南省郑州市中原区","addressid":1507,"detail":"大学科技园","cateid":"新房","price":"1100.00","status":1}]
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
         * nid : 1
         * title : 通和盛世
         * huxing : 三室一厅
         * size : 90
         * address : 河南省郑州市中原区
         * addressid : 1507
         * detail : 大学科技园
         * cateid : 新房
         * price : 1100.00
         * status : 1
         */

        private String nid;
        private String title;
        private String huxing;
        private String size;
        private String address;
        private String addressid;
        private String detail;
        private String cateid;
        private String price;
        private String status;
        private String pic;

        public String getPic() {
            return pic == null ? "" : pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHuxing() {
            return huxing;
        }

        public void setHuxing(String huxing) {
            this.huxing = huxing;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressid() {
            return addressid;
        }

        public void setAddressid(String addressid) {
            this.addressid = addressid;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

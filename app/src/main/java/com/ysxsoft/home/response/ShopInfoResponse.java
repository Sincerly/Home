package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/20 0020
 **/
public class ShopInfoResponse {

    /**
     * data : {"superid":1,"name":"我的超市","mobile":"15555555555","address":"河南省郑州市中原区","detail":"大学科技园","pic":"http://www.jia.com/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg"}
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
         * superid : 1
         * name : 我的超市
         * mobile : 15555555555
         * address : 河南省郑州市中原区
         * detail : 大学科技园
         * pic : http://www.jia.com/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg
         */

        private String superid;
        private String name;
        private String mobile;
        private String address;
        private String detail;
        private String pic;

        public String getSuperid() {
            return superid == null ? "" : superid;
        }

        public void setSuperid(String superid) {
            this.superid = superid;
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

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDetail() {
            return detail == null ? "" : detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getPic() {
            return pic == null ? "" : pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}

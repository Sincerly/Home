package com.ysxsoft.home.response;

import java.util.List;

/**
 * create by Sincerly on 2019/6/19 0019
 **/
public class MyJobResponse {

    /**
     * data : [{"nid":12,"industry":"软件工程师","addressid":"郑州市","address":"中原区","position":"安卓开发工程师","company":"亿生信科技","experid":"0-1年","educationid":"高中","wageid":"0-1k","welfareid":["双休"],"status":1},{"nid":11,"industry":"软件工程师","addressid":"郑州市","address":"中原区","position":"安卓开发工程师","company":"亿生信科技","experid":"不限","educationid":"不限","wageid":"面议","welfareid":["双休","包吃","五险一金"],"status":1}]
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
         * nid : 12
         * industry : 软件工程师
         * addressid : 郑州市
         * address : 中原区
         * position : 安卓开发工程师
         * company : 亿生信科技
         * experid : 0-1年
         * educationid : 高中
         * wageid : 0-1k
         * welfareid : ["双休"]
         * status : 1
         */

        private String nid;
        private String industry;
        private String addressid;
        private String address;
        private String position;
        private String company;
        private String experid;
        private String educationid;
        private String wageid;
        private String status;
        private String natureid;

        public String getNatureid() {
            return natureid;
        }

        public void setNatureid(String natureid) {
            this.natureid = natureid;
        }

        private List<String> welfareid;

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getAddressid() {
            return addressid;
        }

        public void setAddressid(String addressid) {
            this.addressid = addressid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getExperid() {
            return experid;
        }

        public void setExperid(String experid) {
            this.experid = experid;
        }

        public String getEducationid() {
            return educationid;
        }

        public void setEducationid(String educationid) {
            this.educationid = educationid;
        }

        public String getWageid() {
            return wageid;
        }

        public void setWageid(String wageid) {
            this.wageid = wageid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getWelfareid() {
            return welfareid;
        }

        public void setWelfareid(List<String> welfareid) {
            this.welfareid = welfareid;
        }
    }
}

package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/18 0018
 **/
public class JobListResponse {

    /**
     * data : [{"nid":1,"industry":"酒店/餐饮","addressid":"郑州市","address":"中原区","position":"经理","company":"张三","experid":"0-1年","educationid":"0-1年","wageid":"0-1年","welfareid":null}]
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
         * nid : 1
         * industry : 酒店/餐饮
         * addressid : 郑州市
         * address : 中原区
         * position : 经理
         * company : 张三
         * experid : 0-1年
         * educationid : 0-1年
         * wageid : 0-1年
         * welfareid : null
         */

        private int nid;
        private String industry;
        private String addressid;
        private String address;
        private String position;
        private String company;
        private String experid;
        private String educationid;
        private String wageid;
        private List<String> welfareid;
        private String natureid;

        public String getNatureid() {
            return natureid == null ? "" : natureid;
        }

        public void setNatureid(String natureid) {
            this.natureid = natureid;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
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

        public List<String> getWelfareid() {
            if (welfareid == null) {
                return new ArrayList<>();
            }
            return welfareid;
        }

        public void setWelfareid(List<String> welfareid) {
            this.welfareid = welfareid;
        }
    }
}

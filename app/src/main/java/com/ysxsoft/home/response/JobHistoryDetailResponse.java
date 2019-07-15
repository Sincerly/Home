package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/19 0019
 **/
public class JobHistoryDetailResponse {

    /**
     * data : {"logid":4,"company":"张三","industry":"酒店/餐饮","position":"经理","start_time":2019,"end_time":2019,"content":"发的发生的"}
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
         * logid : 4
         * company : 张三
         * industry : 酒店/餐饮
         * position : 经理
         * start_time : 2019
         * end_time : 2019
         * content : 发的发生的
         */

        private String logid;
        private String company;
        private String industry;
        private String industryid;
        private String position;
        private String start_time;
        private String end_time;
        private String content;

        public String getIndustryid() {
            return industryid;
        }

        public void setIndustryid(String industryid) {
            this.industryid = industryid;
        }

        public String getLogid() {
            return logid == null ? "" : logid;
        }

        public void setLogid(String logid) {
            this.logid = logid;
        }

        public String getCompany() {
            return company == null ? "" : company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getIndustry() {
            return industry == null ? "" : industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getPosition() {
            return position == null ? "" : position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getStart_time() {
            return start_time == null ? "" : start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time == null ? "" : end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

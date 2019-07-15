package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/19 0019
 **/
public class JobRequestDetailResponse {

    /**
     * data : {"logo":"/static/admin/img/none.png","mobile":"15620953232","company":"张三","industry":"酒店/餐饮","address":"河南省郑州市中原区","detail":"","position":"经理","natureid":"全职","educationid":"大专","experid":"0-1年","wageid":"面议","welfareid":null,"certificate":"英语六级，会计证","addtime":"432727小时前","content":"","type":0,"log":[{"company":"张三","position":"经理","start_time":"1970-01","end_time":"1970-01","content":"发的发生的"}]}
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
         * logo : /static/admin/img/none.png
         * mobile : 15620953232
         * company : 张三
         * industry : 酒店/餐饮
         * address : 河南省郑州市中原区
         * detail :
         * position : 经理
         * natureid : 全职
         * educationid : 大专
         * experid : 0-1年
         * wageid : 面议
         * welfareid : null
         * certificate : 英语六级，会计证
         * addtime : 432727小时前
         * content :
         * type : 0
         * log : [{"company":"张三","position":"经理","start_time":"1970-01","end_time":"1970-01","content":"发的发生的"}]
         */

        private String logo;
        private String mobile;
        private String company;
        private String industry;
        private String industryid;//修改用
        private String address;
        private String detail;
        private String position;
        private String nature;//修改用
        private String natureid;
        private String education;//修改用
        private String educationid;
        private String exper;//修改用
        private String experid;
        private String wageid;
        private String wage;//修改用
        private List<String> welfareid;
        private String welfare;
        private String certificate;
        private String addtime;
        private String content;
        private String city;
        private String province;
        private String county;
        private int type;
        private String exper_max;//修改用
        private String exper_min;//修改用
        private List<LogBean> log;

        public String getExper_max() {
            return exper_max == null ? "" : exper_max;
        }

        public void setExper_max(String exper_max) {
            this.exper_max = exper_max;
        }

        public String getExper_min() {
            return exper_min == null ? "" : exper_min;
        }

        public void setExper_min(String exper_min) {
            this.exper_min = exper_min;
        }


        public String getCity() {
            return city == null ? "" : city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province == null ? "" : province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCounty() {
            return county == null ? "" : county;
        }

        public void setCounty(String county) {
            this.county = county;
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

        public String getIndustryid() {
            return industryid == null ? "" : industryid;
        }

        public void setIndustryid(String industryid) {
            this.industryid = industryid;
        }

        public String getNature() {
            return nature == null ? "" : nature;
        }

        public void setNature(String nature) {
            this.nature = nature;
        }

        public String getEducation() {
            return education == null ? "" : education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getExper() {
            return exper == null ? "" : exper;
        }

        public void setExper(String exper) {
            this.exper = exper;
        }

        public String getWage() {
            return wage == null ? "" : wage;
        }

        public void setWage(String wage) {
            this.wage = wage;
        }

        public String getWelfare() {
            return welfare == null ? "" : welfare;
        }

        public void setWelfare(String welfare) {
            this.welfare = welfare;
        }

        public String getLogo() {
            return logo == null ? "" : logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

        public String getPosition() {
            return position == null ? "" : position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getNatureid() {
            return natureid == null ? "" : natureid;
        }

        public void setNatureid(String natureid) {
            this.natureid = natureid;
        }

        public String getEducationid() {
            return educationid == null ? "" : educationid;
        }

        public void setEducationid(String educationid) {
            this.educationid = educationid;
        }

        public String getExperid() {
            return experid == null ? "" : experid;
        }

        public void setExperid(String experid) {
            this.experid = experid;
        }

        public String getWageid() {
            return wageid == null ? "" : wageid;
        }

        public void setWageid(String wageid) {
            this.wageid = wageid;
        }


        public String getCertificate() {
            return certificate == null ? "" : certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getAddtime() {
            return addtime == null ? "" : addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<LogBean> getLog() {
            if (log == null) {
                return new ArrayList<>();
            }
            return log;
        }

        public void setLog(List<LogBean> log) {
            this.log = log;
        }

        public static class LogBean {
            /**
             * company : 张三
             * position : 经理
             * start_time : 1970-01
             * end_time : 1970-01
             * content : 发的发生的
             */
            private String logid;
            private String company;
            private String position;
            private String start_time;
            private String end_time;
            private String content;

            public String getLogid() {
                return logid == null ? "" : logid;
            }

            public void setLogid(String logid) {
                this.logid = logid;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}

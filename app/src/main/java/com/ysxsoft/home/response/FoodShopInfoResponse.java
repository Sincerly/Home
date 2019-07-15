package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/7/2 0002
 **/
public class FoodShopInfoResponse {

    /**
     * data : {"companyid":2,"name":"ni的超市","score":"0","start_time":"08:00:00","end_time":"18:00:00","img":[],"address":"河南省郑州市二七区","pic":"http://jia.sanzhima.cn/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg"}
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
         * companyid : 2
         * name : ni的超市
         * score : 0
         * start_time : 08:00:00
         * end_time : 18:00:00
         * img : []
         * address : 河南省郑州市二七区
         * pic : http://jia.sanzhima.cn/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg
         */

        private int companyid;
        private String name;
        private String score;
        private String start_time;
        private String end_time;
        private String address;
        private String pic;
        private String mobile;
        private List<String> img;
        private String style;

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getCompanyid() {
            return companyid;
        }

        public void setCompanyid(int companyid) {
            this.companyid = companyid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public List<String> getImg() {
            if (img == null) {
                return new ArrayList<>();
            }
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}

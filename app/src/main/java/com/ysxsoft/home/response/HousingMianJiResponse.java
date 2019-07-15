package com.ysxsoft.home.response;

import java.util.List;

/**
 * create by Sincerly on 2019/6/20 0020
 **/
public class HousingMianJiResponse {

    /**
     * data : [{"sizeid":1,"title":"小于50平米"},{"sizeid":2,"title":"50-100平米"}]
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
         * sizeid : 1
         * title : 小于50平米
         */

        private String sizeid;
        private String title;

        public String getSizeid() {
            return sizeid;
        }

        public void setSizeid(String sizeid) {
            this.sizeid = sizeid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

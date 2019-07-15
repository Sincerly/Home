package com.ysxsoft.home.response;

import java.util.List;

/**
 * create by Sincerly on 2019/6/21 0021
 **/
public class HuXingListResponse {

    /**
     * data : {"shi":["3","2"],"ting":["2","1"],"wei":["1"]}
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
        private List<String> shi;
        private List<String> ting;
        private List<String> wei;

        public List<String> getShi() {
            return shi;
        }

        public void setShi(List<String> shi) {
            this.shi = shi;
        }

        public List<String> getTing() {
            return ting;
        }

        public void setTing(List<String> ting) {
            this.ting = ting;
        }

        public List<String> getWei() {
            return wei;
        }

        public void setWei(List<String> wei) {
            this.wei = wei;
        }
    }
}


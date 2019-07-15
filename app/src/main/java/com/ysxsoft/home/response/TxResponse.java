package com.ysxsoft.home.response;

public class TxResponse {

    /**
     * data : {"account":"1003.99","time":"2019-07-09 16:12:40"}
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
         * account : 1003.99
         * time : 2019-07-09 16:12:40
         */

        private String account;
        private String time;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}

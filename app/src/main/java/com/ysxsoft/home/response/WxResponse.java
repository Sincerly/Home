package com.ysxsoft.home.response;

import com.google.gson.annotations.SerializedName;

/**
 * create by Sincerly on 2019/7/1 0001
 **/
public class WxResponse {

    /**
     * data : {"prepayid":"wx01113832201382334a5a09061407414100","appid":"wx297392ff114ab087","partnerid":"1540410141","package":"Sign=WXPay","noncestr":"0agq5m7m0q7c1d0ajjf8bnsbe0okea4x","timestamp":1561952312,"sign":"668526583E84242C96F054B793766E52"}
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
         * prepayid : wx01113832201382334a5a09061407414100
         * appid : wx297392ff114ab087
         * partnerid : 1540410141
         * package : Sign=WXPay
         * noncestr : 0agq5m7m0q7c1d0ajjf8bnsbe0okea4x
         * timestamp : 1561952312
         * sign : 668526583E84242C96F054B793766E52
         */

        private String prepayid;
        private String appid;
        private String partnerid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private int timestamp;
        private String sign;

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}

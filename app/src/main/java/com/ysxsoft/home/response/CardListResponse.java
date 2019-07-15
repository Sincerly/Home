package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/20 0020
 **/
public class CardListResponse {


    /**
     * data : [{"cid":1,"name":"张三","bankid":"工商银行","bank":"大学科技园支行","code":"1234567895652"}]
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
        if(data==null){
            data=new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cid : 1
         * name : 张三
         * bankid : 工商银行
         * bank : 大学科技园支行
         * code : 1234567895652
         */

        private String cid;
        private String name;
        private String bankid;
        private String bankids;
        private String bank;
        private String code;
        private String pic;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getBankids() {
            return bankids == null ? "" : bankids;
        }

        public void setBankids(String bankids) {
            this.bankids = bankids;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBankid() {
            return bankid == null ? "" : bankid;
        }

        public void setBankid(String bankid) {
            this.bankid = bankid;
        }

        public String getBank() {
            return bank == null ? "" : bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getCode() {
            return code == null ? "" : code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}

package com.ysxsoft.home.response;

import java.util.List;

/**
 * create by Sincerly on 2019/6/29 0029
 **/
public class ShopProductRuleResponse {

    /**
     * data : [{"ruleid":2,"rule_name":"白色,xll","price":"120.00","pic":"http://www.jia.com/uploads/images/20190506/df46ee4991a6f931e8d2cbae7e47340d.jpg","store":100}]
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
         * ruleid : 2
         * rule_name : 白色,xll
         * price : 120.00
         * pic : http://www.jia.com/uploads/images/20190506/df46ee4991a6f931e8d2cbae7e47340d.jpg
         * store : 100
         */

        private int ruleid;
        private String rule_name;
        private String price;
        private String pic;
        private int store;

        public int getRuleid() {
            return ruleid;
        }

        public void setRuleid(int ruleid) {
            this.ruleid = ruleid;
        }

        public String getRule_name() {
            return rule_name == null ? "" : rule_name;
        }

        public void setRule_name(String rule_name) {
            this.rule_name = rule_name;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPic() {
            return pic == null ? "" : pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getStore() {
            return store;
        }

        public void setStore(int store) {
            this.store = store;
        }
    }
}

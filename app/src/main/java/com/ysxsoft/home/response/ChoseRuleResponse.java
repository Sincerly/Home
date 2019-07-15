package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/24 0024
 **/
public class ChoseRuleResponse {

    /**
     * data : {"id":6,"gid":5,"pic":4,"rule_name":"红色,xl","price":"100.00","store":101,"ruleid":"8,10","min_pic":"http://www.jia.comhttp://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","group_id":6}
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

    public static class DataBean {
        /**
         * id : 6
         * gid : 5
         * pic : 4
         * rule_name : 红色,xl
         * price : 100.00
         * store : 101
         * ruleid : 8,10
         * min_pic : http://www.jia.comhttp://www.jia.com/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
         * group_id : 6
         */

        private String id;
        private String gid;
        private String pic;
        private String rule_name;
        private String price;
        private String store;
        private String ruleid;
        private String min_pic;
        private String group_id;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGid() {
            return gid == null ? "" : gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getPic() {
            return pic == null ? "" : pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
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

        public String getStore() {
            return store == null ? "" : store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getRuleid() {
            return ruleid == null ? "" : ruleid;
        }

        public void setRuleid(String ruleid) {
            this.ruleid = ruleid;
        }

        public String getMin_pic() {
            return min_pic == null ? "" : min_pic;
        }

        public void setMin_pic(String min_pic) {
            this.min_pic = min_pic;
        }

        public String getGroup_id() {
            return group_id == null ? "" : group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }
    }
}

package com.ysxsoft.home.response;

import java.util.List;

/**
 * create by Sincerly on 2019/6/25 0025
 **/
public class GoldHistoryResponse {


    /**
     * data : [{"logid":1,"descr":"查看新闻赠送1金币","num":"+1","addtime":"2019-05-22 18:03","type":2},{"logid":2,"descr":"邀请用户赠送1金币","num":"+1","addtime":"2019-06-11 09:44","type":0},{"logid":3,"descr":"查看新闻赠送1金币","num":"+1","addtime":"2019-06-11 16:05","type":2},{"logid":4,"descr":"查看新闻赠送1金币","num":"+1","addtime":"2019-06-11 16:08","type":2},{"logid":5,"descr":"查看新闻赠送1金币","num":"+1","addtime":"2019-06-11 16:09","type":2},{"logid":6,"descr":"查看新闻赠送1金币","num":"+1","addtime":"2019-06-12 16:52","type":2},{"logid":7,"descr":"查看新闻赠送1金币","num":"+1","addtime":"2019-06-12 16:52","type":2},{"logid":8,"descr":"查看新闻赠送1金币","num":"+1","addtime":"2019-06-12 16:52","type":2},{"logid":9,"descr":"查看新闻赠送1金币","num":"+1","addtime":"2019-06-12 16:52","type":2},{"logid":10,"descr":"查看新闻赠送1金币","num":"+1","addtime":"2019-06-12 17:27","type":2}]
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
         * logid : 1
         * descr : 查看新闻赠送1金币
         * num : +1
         * addtime : 2019-05-22 18:03
         * type : 2
         */

        private String logid;
        private String descr;
        private String num;
        private String addtime;
        private String type;

        public String getLogid() {
            return logid == null ? "" : logid;
        }

        public void setLogid(String logid) {
            this.logid = logid;
        }

        public String getDescr() {
            return descr == null ? "" : descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public String getNum() {
            return num == null ? "" : num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAddtime() {
            return addtime == null ? "" : addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/20 0020
 **/
public class MessageDetailResponse {

    /**
     * data : {"mid":1,"type":"系统消息","content":"<p>发送给发的发货染色体和认同<\/p>","create_time":"2019-06-15 15:41","status":1}
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
         * mid : 1
         * type : 系统消息
         * content : <p>发送给发的发货染色体和认同</p>
         * create_time : 2019-06-15 15:41
         * status : 1
         */

        private String mid;
        private String type;
        private String content;
        private String create_time;
        private String status;

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time == null ? "" : create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

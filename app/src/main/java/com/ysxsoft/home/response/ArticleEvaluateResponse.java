package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/13 0013
 **/
public class ArticleEvaluateResponse {


    /**
     * data : [{"did":1,"uid":4,"pid":0,"content":"hhhh啦","addtime":"2019-05-22 18:13","avatar":"http://jia.sanzhima.cn/static/admin/img/none.png","nickname":"hhh","zan_num":0,"is_zan":0,"sub":[{"did":2,"uid":5,"content":"我回复你了","nickname":"yk8825"}]}]
     * code : 0
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
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * did : 1
         * uid : 4
         * pid : 0
         * content : hhhh啦
         * addtime : 2019-05-22 18:13
         * avatar : http://jia.sanzhima.cn/static/admin/img/none.png
         * nickname : hhh
         * zan_num : 0
         * is_zan : 0
         * sub : [{"did":2,"uid":5,"content":"我回复你了","nickname":"yk8825"}]
         */

        private String did;
        private String uid;
        private String pid;
        private String content;
        private String addtime;
        private String avatar;
        private String nickname;
        private String zan_num;
        private String is_zan;
        private List<SubBean> sub;

        public String getDid() {
            return did == null ? "" : did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getUid() {
            return uid == null ? "" : uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPid() {
            return pid == null ? "" : pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime == null ? "" : addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getZan_num() {
            return zan_num == null ? "" : zan_num;
        }

        public void setZan_num(String zan_num) {
            this.zan_num = zan_num;
        }

        public String getIs_zan() {
            return is_zan == null ? "" : is_zan;
        }

        public void setIs_zan(String is_zan) {
            this.is_zan = is_zan;
        }

        public List<SubBean> getSub() {
            if (sub == null) {
                return new ArrayList<>();
            }
            return sub;
        }

        public void setSub(List<SubBean> sub) {
            this.sub = sub;
        }

        public static class SubBean {
            /**
             * did : 2
             * uid : 5
             * content : 我回复你了
             * nickname : yk8825
             */

            private String did;
            private String uid;
            private String content;
            private String nickname;

            public String getDid() {
                return did == null ? "" : did;
            }

            public void setDid(String did) {
                this.did = did;
            }

            public String getUid() {
                return uid == null ? "" : uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getContent() {
                return content == null ? "" : content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getNickname() {
                return nickname == null ? "" : nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}

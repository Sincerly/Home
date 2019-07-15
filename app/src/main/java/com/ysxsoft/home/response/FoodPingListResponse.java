package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/7/2 0002
 **/
public class FoodPingListResponse {

    /**
     * data : [{"id":1,"uid":5,"productid":1,"companyid":2,"content":"很好","pic":"","score":4,"addtime":"2019-05-31","type":1,"avatar":"","username":"yk8825"}]
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
         * id : 1
         * uid : 5
         * productid : 1
         * companyid : 2
         * content : 很好
         * pic :
         * score : 4
         * addtime : 2019-05-31
         * type : 1
         * avatar :
         * username : yk8825
         */

        private String id;
        private String uid;
        private String productid;
        private String companyid;
        private String content;
        private List<String> pic;
        private String score;
        private String addtime;
        private String type;
        private String avatar;
        private String username;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid == null ? "" : uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getProductid() {
            return productid == null ? "" : productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getCompanyid() {
            return companyid == null ? "" : companyid;
        }

        public void setCompanyid(String companyid) {
            this.companyid = companyid;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getPic() {
            if (pic == null) {
                return new ArrayList<>();
            }
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }

        public String getScore() {
            return score == null ? "" : score;
        }

        public void setScore(String score) {
            this.score = score;
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

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}

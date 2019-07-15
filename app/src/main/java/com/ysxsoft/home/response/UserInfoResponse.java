package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/26 0026
 **/
public class UserInfoResponse {


    /**
     * data : {"uid":6,"nickname":"Sincerly","username":"田高琳1","mobile":"18739509196","sex":1,"level":0,"avatar_img":"http://thirdqq.qlogo.cn/g?b=oidb&k=5WFY2RdV8ysFKmWqKyDueg&s=100","role":3,"score":5,"certify":"411122199809068311","gold":"73711","car":0,"house":0,"job":0,"money":"99.58","url":"www.baidu.com","freeze":0,"superid":1,"is_show":0,"message":0}
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
         * uid : 6
         * nickname : Sincerly
         * username : 田高琳1
         * mobile : 18739509196
         * sex : 1
         * level : 0
         * avatar_img : http://thirdqq.qlogo.cn/g?b=oidb&k=5WFY2RdV8ysFKmWqKyDueg&s=100
         * role : 3
         * score : 5
         * certify : 411122199809068311
         * gold : 73711
         * car : 0
         * house : 0
         * job : 0
         * money : 99.58
         * url : www.baidu.com
         * freeze : 0
         * superid : 1
         * is_show : 0
         * message : 0
         */

        private String uid;
        private String nickname;
        private String username;
        private String mobile;
        private String sex;
        private String level;
        private String avatar_img;
        private String role;
        private String score;
        private String certify;
        private String gold;
        private String car;
        private String house;
        private String job;
        private String money;
        private String url;
        private String freeze;
        private String superid;
        private String is_show;
        private String message;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getAvatar_img() {
            return avatar_img;
        }

        public void setAvatar_img(String avatar_img) {
            this.avatar_img = avatar_img;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getCertify() {
            return certify;
        }

        public void setCertify(String certify) {
            this.certify = certify;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFreeze() {
            return freeze;
        }

        public void setFreeze(String freeze) {
            this.freeze = freeze;
        }

        public String getSuperid() {
            return superid;
        }

        public void setSuperid(String superid) {
            this.superid = superid;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

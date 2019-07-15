package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/7/2 0002
 **/
public class PlatformCheckResponse {


    /**
     * data : {"uid":6,"avatar_img":"http://thirdqq.qlogo.cn/g?b=oidb&k=5WFY2RdV8ysFKmWqKyDueg&s=100","mobile":"18739509196","nickname":"Sincerly","is_pass":1,"isquan":0,"role":3,"car":0,"house":0,"job":0,"invicode":"92058"}
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
         * avatar_img : http://thirdqq.qlogo.cn/g?b=oidb&k=5WFY2RdV8ysFKmWqKyDueg&s=100
         * mobile : 18739509196
         * nickname : Sincerly
         * is_pass : 1
         * isquan : 0
         * role : 3
         * car : 0
         * house : 0
         * job : 0
         * invicode : 92058
         */

        private String uid;
        private String avatar_img;
        private String mobile;
        private String nickname;
        private String is_pass;
        private String isquan;
        private String role;
        private String car;
        private String house;
        private String job;
        private String invicode;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAvatar_img() {
            return avatar_img;
        }

        public void setAvatar_img(String avatar_img) {
            this.avatar_img = avatar_img;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIs_pass() {
            return is_pass;
        }

        public void setIs_pass(String is_pass) {
            this.is_pass = is_pass;
        }

        public String getIsquan() {
            return isquan;
        }

        public void setIsquan(String isquan) {
            this.isquan = isquan;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
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

        public String getInvicode() {
            return invicode;
        }

        public void setInvicode(String invicode) {
            this.invicode = invicode;
        }
    }
}

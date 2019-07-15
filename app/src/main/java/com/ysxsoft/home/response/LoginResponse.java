package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/11 0011
 **/
public class LoginResponse {
    /**
     * data : {"uid":5,"invicode":"59306","mobile":"18530080883","nickname":"yk8825","is_pass":1,"isquan":0}
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
         * uid : 5
         * invicode : 59306
         * mobile : 18530080883
         * nickname : yk8825
         * is_pass : 1
         * isquan : 0
         */

        private String uid;
        private String invicode;
        private String mobile;
        private String nickname;
        private String is_pass;
        private String isquan;
        private String car;
        private String job;
        private String house;

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getInvicode() {
            return invicode;
        }

        public void setInvicode(String invicode) {
            this.invicode = invicode;
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
    }
}

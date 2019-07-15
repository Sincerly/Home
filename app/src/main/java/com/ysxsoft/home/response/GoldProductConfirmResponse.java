package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/24 0024
 **/
public class GoldProductConfirmResponse {

    /**
     * data : {"uid":"5","group_id":"10","num":"1","product":{"price":"100.00","pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","rule_name":"红色,xxl","gid":6,"name":"商品啊","num":"1","account":100},"address":{"id":36,"uid":5,"name":"张三","mobile":"18530080885","province":"河南省","city":"郑州市","county":"中原区","detail":"大学科技园","addtime":1559187224,"status":1,"code":""}}
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
         * group_id : 10
         * num : 1
         * product : {"price":"100.00","pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","rule_name":"红色,xxl","gid":6,"name":"商品啊","num":"1","account":100}
         * address : {"id":36,"uid":5,"name":"张三","mobile":"18530080885","province":"河南省","city":"郑州市","county":"中原区","detail":"大学科技园","addtime":1559187224,"status":1,"code":""}
         */

        private String uid;
        private String group_id;
        private String num;
        private ProductBean product;
        private AddressBean address;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class ProductBean {
            /**
             * price : 100.00
             * pic : http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
             * rule_name : 红色,xxl
             * gid : 6
             * name : 商品啊
             * num : 1
             * account : 100
             */

            private String price;
            private String pic;
            private String rule_name;
            private String gid;
            private String name;
            private String num;
            private String account;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getRule_name() {
                return rule_name;
            }

            public void setRule_name(String rule_name) {
                this.rule_name = rule_name;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }
        }

        public static class AddressBean {
            /**
             * id : 36
             * uid : 5
             * name : 张三
             * mobile : 18530080885
             * province : 河南省
             * city : 郑州市
             * county : 中原区
             * detail : 大学科技园
             * addtime : 1559187224
             * status : 1
             * code :
             */

            private String id;
            private String uid;
            private String name;
            private String mobile;
            private String province;
            private String city;
            private String county;
            private String detail;
            private String addtime;
            private String status;
            private String code;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
    }
}

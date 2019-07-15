package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

public class FoodOrderDetailResponse {

    /**
     * data : {"uid":"田高琳1","status":2,"tui":0,"username":"田高琳","mobile":"18739509196","address":"河南省郑州市中原区详细地址","account":"469","num":18,"dsn":"jia1562230568725","addtime":"2019-07-04 16:56:08","paytime":"2019-07-04 16:56","paytype":3,"yun":"0.00","bao":"18.00","quanid":0,"huo":0,"quan":null,"l_time":0,"type":3,"style":0,"tuides":"包装费，配送费","tui_money":0,"tuidetail":null,"product":[{"gname":"发送到","gid":4,"rule_name":"","ruleid":0,"num":7,"price":"33.00","pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"萨达都是","gid":2,"rule_name":"","ruleid":0,"num":11,"price":"20.00","pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]}
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
         * uid : 田高琳1
         * status : 2
         * tui : 0
         * username : 田高琳
         * mobile : 18739509196
         * address : 河南省郑州市中原区详细地址
         * account : 469
         * num : 18
         * dsn : jia1562230568725
         * addtime : 2019-07-04 16:56:08
         * paytime : 2019-07-04 16:56
         * paytype : 3
         * yun : 0.00
         * bao : 18.00
         * quanid : 0
         * huo : 0
         * quan : null
         * l_time : 0
         * type : 3
         * style : 0
         * tuides : 包装费，配送费
         * tui_money : 0
         * tuidetail : null
         * product : [{"gname":"发送到","gid":4,"rule_name":"","ruleid":0,"num":7,"price":"33.00","pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"萨达都是","gid":2,"rule_name":"","ruleid":0,"num":11,"price":"20.00","pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]
         */

        private String uid;
        private String status;
        private String tui;
        private String username;
        private String mobile;
        private String address;
        private String account;
        private String num;
        private String dsn;
        private String addtime;
        private String paytime;
        private String paytype;
        private String yun;
        private String bao;
        private String quanid;
        private String huo;
        private Object quan;
        private String l_time;
        private String type;
        private String style;
        private String tuides;
        private String tui_money;
        private Object tuidetail;
        private List<ProductBean> product;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTui() {
            return tui;
        }

        public void setTui(String tui) {
            this.tui = tui;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getDsn() {
            return dsn;
        }

        public void setDsn(String dsn) {
            this.dsn = dsn;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPaytime() {
            return paytime;
        }

        public void setPaytime(String paytime) {
            this.paytime = paytime;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getYun() {
            return yun;
        }

        public void setYun(String yun) {
            this.yun = yun;
        }

        public String getBao() {
            return bao;
        }

        public void setBao(String bao) {
            this.bao = bao;
        }

        public String getQuanid() {
            return quanid;
        }

        public void setQuanid(String quanid) {
            this.quanid = quanid;
        }

        public String getHuo() {
            return huo;
        }

        public void setHuo(String huo) {
            this.huo = huo;
        }

        public Object getQuan() {
            return quan;
        }

        public void setQuan(Object quan) {
            this.quan = quan;
        }

        public String getL_time() {
            return l_time;
        }

        public void setL_time(String l_time) {
            this.l_time = l_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getTuides() {
            return tuides;
        }

        public void setTuides(String tuides) {
            this.tuides = tuides;
        }

        public String getTui_money() {
            return tui_money;
        }

        public void setTui_money(String tui_money) {
            this.tui_money = tui_money;
        }

        public Object getTuidetail() {
            return tuidetail;
        }

        public void setTuidetail(Object tuidetail) {
            this.tuidetail = tuidetail;
        }

        public List<ProductBean> getProduct() {
            if(product==null){
                product=new ArrayList<>();
            }
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ProductBean {
            /**
             * gname : 发送到
             * gid : 4
             * rule_name :
             * ruleid : 0
             * num : 7
             * price : 33.00
             * pic : http://jia.sanzhima.cn/static/admin/img/none.png
             */

            private String gname;
            private String gid;
            private String rule_name;
            private String ruleid;
            private String num;
            private String price;
            private String pic;

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getRule_name() {
                return rule_name;
            }

            public void setRule_name(String rule_name) {
                this.rule_name = rule_name;
            }

            public String getRuleid() {
                return ruleid;
            }

            public void setRuleid(String ruleid) {
                this.ruleid = ruleid;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

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
        }
    }
}

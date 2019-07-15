package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/7/2 0002
 **/
public class ShopOrderDetailResponse {


    /**
     * data : {"uid":"田高琳1","status":1,"tui":1,"username":"田高琳","mobile":"18739509196","address":"河南省郑州市中原区详细地址","account":"524","num":4,"dsn":"jia1561975408601","addtime":"2019-07-01 18:03:28","paytime":"2019-07-01 18:20","paytype":3,"yun":"0.00","quanid":3,"quan":{"man":"100","size":"50"},"type":6,"style":2,"tuides":"无","tui_money":524,"tuidetail":{"id":3,"dsn":"1562144011184","uid":6,"orderid":92,"reason":"呵呵","content":"呵呵说明","pic":null,"addtime":"2019-07-03 16:53:31","refuse":""},"product":[{"gname":"aaa","gid":3,"rule_name":"白色","ruleid":4,"num":4,"price":"131.00","pic":"http://jia.sanzhima.cn/uploads/images/20190516/d88c6ed4e5365e5faf550256ab9bdd88.png"}]}
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
         * status : 1
         * tui : 1
         * username : 田高琳
         * mobile : 18739509196
         * address : 河南省郑州市中原区详细地址
         * account : 524
         * num : 4
         * dsn : jia1561975408601
         * addtime : 2019-07-01 18:03:28
         * paytime : 2019-07-01 18:20
         * paytype : 3
         * yun : 0.00
         * quanid : 3
         * quan : {"man":"100","size":"50"}
         * type : 6
         * style : 2
         * tuides : 无
         * tui_money : 524
         * tuidetail : {"id":3,"dsn":"1562144011184","uid":6,"orderid":92,"reason":"呵呵","content":"呵呵说明","pic":null,"addtime":"2019-07-03 16:53:31","refuse":""}
         * product : [{"gname":"aaa","gid":3,"rule_name":"白色","ruleid":4,"num":4,"price":"131.00","pic":"http://jia.sanzhima.cn/uploads/images/20190516/d88c6ed4e5365e5faf550256ab9bdd88.png"}]
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
        private String quanid;
        private ItemBean quan;
        private String type;
        private String style;
        private String tuides;
        private String tui_money;
        private TuidetailBean tuidetail;
        private List<ProductBean> product;

        public String getUid() {
            return uid == null ? "" : uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTui() {
            return tui == null ? "" : tui;
        }

        public void setTui(String tui) {
            this.tui = tui;
        }

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAccount() {
            return account == null ? "" : account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getNum() {
            return num == null ? "" : num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getDsn() {
            return dsn == null ? "" : dsn;
        }

        public void setDsn(String dsn) {
            this.dsn = dsn;
        }

        public String getAddtime() {
            return addtime == null ? "" : addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPaytime() {
            return paytime == null ? "" : paytime;
        }

        public void setPaytime(String paytime) {
            this.paytime = paytime;
        }

        public String getPaytype() {
            return paytype == null ? "" : paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getYun() {
            return yun == null ? "" : yun;
        }

        public void setYun(String yun) {
            this.yun = yun;
        }

        public String getQuanid() {
            return quanid == null ? "" : quanid;
        }

        public void setQuanid(String quanid) {
            this.quanid = quanid;
        }

        public ItemBean getQuan() {
            return quan;
        }

        public void setQuan(ItemBean quan) {
            this.quan = quan;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStyle() {
            return style == null ? "" : style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getTuides() {
            return tuides == null ? "" : tuides;
        }

        public void setTuides(String tuides) {
            this.tuides = tuides;
        }

        public String getTui_money() {
            return tui_money == null ? "" : tui_money;
        }

        public void setTui_money(String tui_money) {
            this.tui_money = tui_money;
        }

        public TuidetailBean getTuidetail() {
            return tuidetail;
        }

        public void setTuidetail(TuidetailBean tuidetail) {
            this.tuidetail = tuidetail;
        }

        public List<ProductBean> getProduct() {
            if (product == null) {
                return new ArrayList<>();
            }
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ItemBean {
            /**
             * man : 100
             * size : 50
             */

            private String man;
            private String size;

            public String getMan() {
                return man == null ? "" : man;
            }

            public void setMan(String man) {
                this.man = man;
            }

            public String getSize() {
                return size == null ? "" : size;
            }

            public void setSize(String size) {
                this.size = size;
            }
        }

        public static class TuidetailBean {
            /**
             * id : 3
             * dsn : 1562144011184
             * uid : 6
             * orderid : 92
             * reason : 呵呵
             * content : 呵呵说明
             * pic : null
             * addtime : 2019-07-03 16:53:31
             * refuse :
             */

            private String id;
            private String dsn;
            private String uid;
            private String orderid;
            private String reason;
            private String content;
            private List<String> pic;
            private String addtime;
            private String refuse;

            public String getId() {
                return id == null ? "" : id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDsn() {
                return dsn == null ? "" : dsn;
            }

            public void setDsn(String dsn) {
                this.dsn = dsn;
            }

            public String getUid() {
                return uid == null ? "" : uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getOrderid() {
                return orderid == null ? "" : orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getReason() {
                return reason == null ? "" : reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
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

            public String getAddtime() {
                return addtime == null ? "" : addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getRefuse() {
                return refuse == null ? "" : refuse;
            }

            public void setRefuse(String refuse) {
                this.refuse = refuse;
            }
        }

        public static class ProductBean {
            /**
             * gname : aaa
             * gid : 3
             * rule_name : 白色
             * ruleid : 4
             * num : 4
             * price : 131.00
             * pic : http://jia.sanzhima.cn/uploads/images/20190516/d88c6ed4e5365e5faf550256ab9bdd88.png
             */

            private String gname;
            private String gid;
            private String rule_name;
            private String ruleid;
            private String num;
            private String price;
            private String pic;

            public String getGname() {
                return gname == null ? "" : gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getGid() {
                return gid == null ? "" : gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getRule_name() {
                return rule_name == null ? "" : rule_name;
            }

            public void setRule_name(String rule_name) {
                this.rule_name = rule_name;
            }

            public String getRuleid() {
                return ruleid == null ? "" : ruleid;
            }

            public void setRuleid(String ruleid) {
                this.ruleid = ruleid;
            }

            public String getNum() {
                return num == null ? "" : num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPic() {
                return pic == null ? "" : pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}

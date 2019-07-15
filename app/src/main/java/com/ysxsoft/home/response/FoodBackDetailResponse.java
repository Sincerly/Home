package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/7/3 0003
 **/
public class FoodBackDetailResponse {


    /**
     * data : {"uid":"田高琳1","status":1,"tui":1,"username":"田高琳","mobile":"18739509196","address":"河南省郑州市中原区详细地址","account":"838","num":19,"dsn":"jia1562117226215","addtime":"2019-07-03 09:27:06","paytime":"2019-07-03 11:57","paytype":3,"yun":"0.00","bao":"19.00","quanid":0,"huo":0,"quan":null,"l_time":0,"type":9,"style":2,"tuides":"包装费，配送费","tui_money":819,"tuidetail":{"id":1,"dsn":"1562227427805","uid":6,"orderid":57,"reason":"退款原因","content":"退款说明","pic":["http://jia.sanzhima.cn/uploads/apifile/20190704/4f343a013b3e7277b798e35f1121f12a.jpeg"],"addtime":"2019-07-04 16:03:47","refuse":""},"product":[{"gname":"发斯蒂芬","gid":3,"rule_name":"大份","ruleid":3,"num":7,"price":"25.00","pic":"http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg"},{"gname":"发送到","gid":4,"rule_name":"","ruleid":0,"num":8,"price":"33.00","pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"商品","gid":1,"rule_name":"六寸","ruleid":2,"num":3,"price":"120.00","pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"},{"gname":"萨达都是","gid":2,"rule_name":"","ruleid":0,"num":1,"price":"20.00","pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]}
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
         * account : 838
         * num : 19
         * dsn : jia1562117226215
         * addtime : 2019-07-03 09:27:06
         * paytime : 2019-07-03 11:57
         * paytype : 3
         * yun : 0.00
         * bao : 19.00
         * quanid : 0
         * huo : 0
         * quan : null
         * l_time : 0
         * type : 9
         * style : 2
         * tuides : 包装费，配送费
         * tui_money : 819
         * tuidetail : {"id":1,"dsn":"1562227427805","uid":6,"orderid":57,"reason":"退款原因","content":"退款说明","pic":["http://jia.sanzhima.cn/uploads/apifile/20190704/4f343a013b3e7277b798e35f1121f12a.jpeg"],"addtime":"2019-07-04 16:03:47","refuse":""}
         * product : [{"gname":"发斯蒂芬","gid":3,"rule_name":"大份","ruleid":3,"num":7,"price":"25.00","pic":"http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg"},{"gname":"发送到","gid":4,"rule_name":"","ruleid":0,"num":8,"price":"33.00","pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"商品","gid":1,"rule_name":"六寸","ruleid":2,"num":3,"price":"120.00","pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"},{"gname":"萨达都是","gid":2,"rule_name":"","ruleid":0,"num":1,"price":"20.00","pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]
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
        private ItemBean quan;
        private String l_time;
        private String type;
        private String style;
        private String tuides;
        private String tui_money;
        private TuidetailBean tuidetail;
        private List<ProductBean> product;

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

        public ItemBean getQuan() {
            return quan;
        }

        public void setQuan(ItemBean quan) {
            this.quan = quan;
        }

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

        public TuidetailBean getTuidetail() {
            return tuidetail;
        }

        public void setTuidetail(TuidetailBean tuidetail) {
            this.tuidetail = tuidetail;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class TuidetailBean {
            /**
             * id : 1
             * dsn : 1562227427805
             * uid : 6
             * orderid : 57
             * reason : 退款原因
             * content : 退款说明
             * pic : ["http://jia.sanzhima.cn/uploads/apifile/20190704/4f343a013b3e7277b798e35f1121f12a.jpeg"]
             * addtime : 2019-07-04 16:03:47
             * refuse :
             */

            private String id;
            private String dsn;
            private String uid;
            private String orderid;
            private String reason;
            private String content;
            private String addtime;
            private String refuse;
            private List<String> pic;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDsn() {
                return dsn;
            }

            public void setDsn(String dsn) {
                this.dsn = dsn;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getRefuse() {
                return refuse;
            }

            public void setRefuse(String refuse) {
                this.refuse = refuse;
            }

            public List<String> getPic() {
                if(pic==null){
                    pic=new ArrayList<>();
                }
                return pic;
            }

            public void setPic(List<String> pic) {
                this.pic = pic;
            }
        }

        public static class ProductBean {
            /**
             * gname : 发斯蒂芬
             * gid : 3
             * rule_name : 大份
             * ruleid : 3
             * num : 7
             * price : 25.00
             * pic : http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg
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

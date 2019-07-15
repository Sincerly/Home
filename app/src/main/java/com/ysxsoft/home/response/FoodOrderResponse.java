package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/7/3 0003
 **/
public class FoodOrderResponse {

    /**
     * code : 200
     * data : [{"orderid":57,"dsn":"jia1562117226215","num":19,"account":"838","status":0,"tui":0,"type":1,"product":[{"gname":"发斯蒂芬","gid":3,"rule_name":"大份","price":"25.00","num":7,"ruleid":3,"pic":"http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg"},{"gname":"发送到","gid":4,"rule_name":"","price":"33.00","num":8,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"商品","gid":1,"rule_name":"六寸","price":"120.00","num":3,"ruleid":2,"pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"},{"gname":"萨达都是","gid":2,"rule_name":"","price":"20.00","num":1,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]},{"orderid":52,"dsn":"jia1562117215100","num":19,"account":"838","status":0,"tui":0,"type":1,"product":[{"gname":"发斯蒂芬","gid":3,"rule_name":"大份","price":"25.00","num":7,"ruleid":3,"pic":"http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg"},{"gname":"发送到","gid":4,"rule_name":"","price":"33.00","num":8,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"商品","gid":1,"rule_name":"六寸","price":"120.00","num":3,"ruleid":2,"pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"},{"gname":"萨达都是","gid":2,"rule_name":"","price":"20.00","num":1,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]},{"orderid":47,"dsn":"jia1562117195725","num":19,"account":"838","status":0,"tui":0,"type":1,"product":[{"gname":"发斯蒂芬","gid":3,"rule_name":"大份","price":"25.00","num":7,"ruleid":3,"pic":"http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg"},{"gname":"发送到","gid":4,"rule_name":"","price":"33.00","num":8,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"商品","gid":1,"rule_name":"六寸","price":"120.00","num":3,"ruleid":2,"pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"},{"gname":"萨达都是","gid":2,"rule_name":"","price":"20.00","num":1,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]},{"orderid":42,"dsn":"jia1562116943171","num":19,"account":"838","status":0,"tui":0,"type":1,"product":[{"gname":"发斯蒂芬","gid":3,"rule_name":"大份","price":"25.00","num":7,"ruleid":3,"pic":"http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg"},{"gname":"发送到","gid":4,"rule_name":"","price":"33.00","num":8,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"商品","gid":1,"rule_name":"六寸","price":"120.00","num":3,"ruleid":2,"pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"},{"gname":"萨达都是","gid":2,"rule_name":"","price":"20.00","num":1,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]},{"orderid":37,"dsn":"jia1562116715910","num":19,"account":"838","status":0,"tui":0,"type":1,"product":[{"gname":"发斯蒂芬","gid":3,"rule_name":"大份","price":"25.00","num":7,"ruleid":3,"pic":"http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg"},{"gname":"发送到","gid":4,"rule_name":"","price":"33.00","num":8,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"商品","gid":1,"rule_name":"六寸","price":"120.00","num":3,"ruleid":2,"pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"},{"gname":"萨达都是","gid":2,"rule_name":"","price":"20.00","num":1,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]}]
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
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
         * orderid : 57
         * dsn : jia1562117226215
         * num : 19
         * account : 838
         * status : 0
         * tui : 0
         * type : 1
         * product : [{"gname":"发斯蒂芬","gid":3,"rule_name":"大份","price":"25.00","num":7,"ruleid":3,"pic":"http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg"},{"gname":"发送到","gid":4,"rule_name":"","price":"33.00","num":8,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"},{"gname":"商品","gid":1,"rule_name":"六寸","price":"120.00","num":3,"ruleid":2,"pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"},{"gname":"萨达都是","gid":2,"rule_name":"","price":"20.00","num":1,"ruleid":0,"pic":"http://jia.sanzhima.cn/static/admin/img/none.png"}]
         */

        private String orderid;
        private String company;
        private String dsn;
        private String num;
        private String account;
        private String status;
        private String tui;
        private String type;
        private String company_pic;
        private String quanid;
        private List<ProductBean> product;

        public String getQuanid() {
            return quanid;
        }

        public void setQuanid(String quanid) {
            this.quanid = quanid;
        }

        public String getCompany_pic() {
            return company_pic == null ? "" : company_pic;
        }

        public void setCompany_pic(String company_pic) {
            this.company_pic = company_pic;
        }

        public String getCompany() {
            return company == null ? "" : company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getOrderid() {
            return orderid == null ? "" : orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getDsn() {
            return dsn == null ? "" : dsn;
        }

        public void setDsn(String dsn) {
            this.dsn = dsn;
        }

        public String getNum() {
            return num == null ? "" : num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAccount() {
            return account == null ? "" : account;
        }

        public void setAccount(String account) {
            this.account = account;
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

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
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

        public static class ProductBean {
            /**
             * gname : 发斯蒂芬
             * gid : 3
             * rule_name : 大份
             * price : 25.00
             * num : 7
             * ruleid : 3
             * pic : http://jia.sanzhima.cn/uploads/images/20190702/710cbe6eb9b39e1f3174df957b481ba4.jpg
             */

            private String gname;
            private String gid;
            private String rule_name;
            private String price;
            private String num;
            private String ruleid;
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

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getNum() {
                return num == null ? "" : num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getRuleid() {
                return ruleid == null ? "" : ruleid;
            }

            public void setRuleid(String ruleid) {
                this.ruleid = ruleid;
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

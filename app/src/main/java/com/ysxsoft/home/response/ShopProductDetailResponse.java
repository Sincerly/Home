package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/7/1 0001
 **/
public class ShopProductDetailResponse {

    /**
     * data : {"product":{"productid":4,"name":"ssss","pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","content":"fasfgasdhgdfashwaerhr","sold":4,"store":-4,"cateid":"半身裙","wen":"温馨提示温馨提示温馨提示温馨提示温馨提示","cid":3,"c_cateid":2},"isrule":1,"rule":[{"ruleid":5,"rule_name":"fsdaf","price":"110.00","pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","store":96}]}
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

    public static class DataBean {
        /**
         * product : {"productid":4,"name":"ssss","pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","content":"fasfgasdhgdfashwaerhr","sold":4,"store":-4,"cateid":"半身裙","wen":"温馨提示温馨提示温馨提示温馨提示温馨提示","cid":3,"c_cateid":2}
         * isrule : 1
         * rule : [{"ruleid":5,"rule_name":"fsdaf","price":"110.00","pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","store":96}]
         */

        private ProductBean product;
        private String isrule;
        private List<RuleBean> rule;

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public String getIsrule() {
            return isrule == null ? "" : isrule;
        }

        public void setIsrule(String isrule) {
            this.isrule = isrule;
        }

        public List<RuleBean> getRule() {
            if (rule == null) {
                return new ArrayList<>();
            }
            return rule;
        }

        public void setRule(List<RuleBean> rule) {
            this.rule = rule;
        }

        public static class ProductBean {
            /**
             * productid : 4
             * name : ssss
             * pic : http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
             * content : fasfgasdhgdfashwaerhr
             * sold : 4
             * store : -4
             * cateid : 半身裙
             * wen : 温馨提示温馨提示温馨提示温馨提示温馨提示
             * cid : 3
             * c_cateid : 2
             */

            private String productid;
            private String name;
            private String pic;
            private String content;
            private String sold;
            private String store;
            private String cateid;
            private String wen;
            private String cid;
            private String c_cateid;
            private String price;

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getProductid() {
                return productid == null ? "" : productid;
            }

            public void setProductid(String productid) {
                this.productid = productid;
            }

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic() {
                return pic == null ? "" : pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getContent() {
                return content == null ? "" : content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getSold() {
                return sold == null ? "" : sold;
            }

            public void setSold(String sold) {
                this.sold = sold;
            }

            public String getStore() {
                return store == null ? "" : store;
            }

            public void setStore(String store) {
                this.store = store;
            }

            public String getCateid() {
                return cateid == null ? "" : cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public String getWen() {
                return wen == null ? "" : wen;
            }

            public void setWen(String wen) {
                this.wen = wen;
            }

            public String getCid() {
                return cid == null ? "" : cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getC_cateid() {
                return c_cateid == null ? "" : c_cateid;
            }

            public void setC_cateid(String c_cateid) {
                this.c_cateid = c_cateid;
            }
        }

        public static class RuleBean {
            /**
             * ruleid : 5
             * rule_name : fsdaf
             * price : 110.00
             * pic : http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
             * store : 96
             */

            private String ruleid;
            private String rule_name;
            private String price;
            private String pic;
            private String store;

            public String getRuleid() {
                return ruleid == null ? "" : ruleid;
            }

            public void setRuleid(String ruleid) {
                this.ruleid = ruleid;
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

            public String getPic() {
                return pic == null ? "" : pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getStore() {
                return store == null ? "" : store;
            }

            public void setStore(String store) {
                this.store = store;
            }
        }
    }
}

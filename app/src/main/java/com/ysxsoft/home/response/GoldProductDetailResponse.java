package com.ysxsoft.home.response;

import java.util.List;

/**
 * create by Sincerly on 2019/6/24 0024
 **/
public class GoldProductDetailResponse {

    /**
     * data : {"rule":[{"ruleid":6,"title":"颜色","sub":[{"ruleid":8,"title":"红色"},{"ruleid":9,"title":"白色"}]},{"ruleid":7,"title":"尺寸","sub":[{"ruleid":10,"title":"xl"},{"ruleid":11,"title":"xxl"}]}],"product":{"productid":6,"name":"商品啊","pic":["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"],"content":"<p>发多少富士达发多少<\/p>","sold":0,"store":50,"p_ruleid":"6,7","price":"89.00","group_id":9,"rule_name":"红色,xl","min_pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"}}
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
         * rule : [{"ruleid":6,"title":"颜色","sub":[{"ruleid":8,"title":"红色"},{"ruleid":9,"title":"白色"}]},{"ruleid":7,"title":"尺寸","sub":[{"ruleid":10,"title":"xl"},{"ruleid":11,"title":"xxl"}]}]
         * product : {"productid":6,"name":"商品啊","pic":["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"],"content":"<p>发多少富士达发多少<\/p>","sold":0,"store":50,"p_ruleid":"6,7","price":"89.00","group_id":9,"rule_name":"红色,xl","min_pic":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"}
         */

        private ProductBean product;
        private List<RuleBean> rule;

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public List<RuleBean> getRule() {
            return rule;
        }

        public void setRule(List<RuleBean> rule) {
            this.rule = rule;
        }

        public static class ProductBean {
            /**
             * productid : 6
             * name : 商品啊
             * pic : ["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"]
             * content : <p>发多少富士达发多少</p>
             * sold : 0
             * store : 50
             * p_ruleid : 6,7
             * price : 89.00
             * group_id : 9
             * rule_name : 红色,xl
             * min_pic : http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
             */
            private String productid;
            private String name;
            private String content;
            private String sold;
            private String store;
            private String p_ruleid;
            private String price;
            private String group_id;
            private String rule_name;
            private String min_pic;
            private List<String> pic;

            public String getProductid() {
                return productid;
            }

            public void setProductid(String productid) {
                this.productid = productid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getSold() {
                return sold;
            }

            public void setSold(String sold) {
                this.sold = sold;
            }

            public String getStore() {
                return store;
            }

            public void setStore(String store) {
                this.store = store;
            }

            public String getP_ruleid() {
                return p_ruleid;
            }

            public void setP_ruleid(String p_ruleid) {
                this.p_ruleid = p_ruleid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public String getRule_name() {
                return rule_name;
            }

            public void setRule_name(String rule_name) {
                this.rule_name = rule_name;
            }

            public String getMin_pic() {
                return min_pic;
            }

            public void setMin_pic(String min_pic) {
                this.min_pic = min_pic;
            }

            public List<String> getPic() {
                return pic;
            }

            public void setPic(List<String> pic) {
                this.pic = pic;
            }
        }

        public static class RuleBean {
            /**
             * ruleid : 6
             * title : 颜色
             * sub : [{"ruleid":8,"title":"红色"},{"ruleid":9,"title":"白色"}]
             */

            private String ruleid;
            private String title;
            private List<SubBean> sub;

            public String getRuleid() {
                return ruleid;
            }

            public void setRuleid(String ruleid) {
                this.ruleid = ruleid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<SubBean> getSub() {
                return sub;
            }

            public void setSub(List<SubBean> sub) {
                this.sub = sub;
            }

            public static class SubBean {
                /**
                 * ruleid : 8
                 * title : 红色
                 */

                private String ruleid;
                private String title;

                public String getRuleid() {
                    return ruleid;
                }

                public void setRuleid(String ruleid) {
                    this.ruleid = ruleid;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }
    }
}

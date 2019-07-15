package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/1/22 0022
 **/
public class ProductDetailResponse {

    /**
     * data : {"rule":[{"ruleid":1,"title":"颜色","sub":[{"ruleid":3,"title":"白色"}]},{"ruleid":2,"title":"尺寸","sub":[{"ruleid":4,"title":"大号"}]}],"product":{"productid":1,"name":"商品啊","pic":["http://zhyuce.sanzhima.cn/uploads/images/20190105/66dbce1117b3a70df4824df96ca4d7d2.jpg","http://zhyuce.sanzhima.cn/uploads/images/20190105/08bb7d9ea9c00480eabb558ba3b3a47b.jpg","http://zhyuce.sanzhima.cn/uploads/images/20190105/e17baa4e0f9462ac91170cad762979ae.jpg"],"content":"<p>十大范德萨个盛世嫡妃123<img src=\"/uploads/images/20190103/cc701e3521ea6b56c32ff9b508e93831.png\" title=\"logo-signin1.png\"/><\/p>","p_ruleid":"1,2","p_rule_name":"颜色,尺寸","video":"http://zhyuce.sanzhima.cn/uploads/files/20190105/c09ea07db225f3e155ce2cd7c323fb32.mp4","v_pic":3,"is_sou":2,"price":"100.00","store":100,"group_id":4,"rule_name":"白色,大号"},"server":"15622222222","qq":"2299999999"}
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
         * rule : [{"ruleid":1,"title":"颜色","sub":[{"ruleid":3,"title":"白色"}]},{"ruleid":2,"title":"尺寸","sub":[{"ruleid":4,"title":"大号"}]}]
         * product : {"productid":1,"name":"商品啊","pic":["http://zhyuce.sanzhima.cn/uploads/images/20190105/66dbce1117b3a70df4824df96ca4d7d2.jpg","http://zhyuce.sanzhima.cn/uploads/images/20190105/08bb7d9ea9c00480eabb558ba3b3a47b.jpg","http://zhyuce.sanzhima.cn/uploads/images/20190105/e17baa4e0f9462ac91170cad762979ae.jpg"],"content":"<p>十大范德萨个盛世嫡妃123<img src=\"/uploads/images/20190103/cc701e3521ea6b56c32ff9b508e93831.png\" title=\"logo-signin1.png\"/><\/p>","p_ruleid":"1,2","p_rule_name":"颜色,尺寸","video":"http://zhyuce.sanzhima.cn/uploads/files/20190105/c09ea07db225f3e155ce2cd7c323fb32.mp4","v_pic":3,"is_sou":2,"price":"100.00","store":100,"group_id":4,"rule_name":"白色,大号"}
         * server : 15622222222
         * qq : 2299999999
         */

        private ProductBean product;
        private String server;
        private String qq;
        private List<RuleBean> rule;

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
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
             * productid : 1
             * name : 商品啊
             * pic : ["http://zhyuce.sanzhima.cn/uploads/images/20190105/66dbce1117b3a70df4824df96ca4d7d2.jpg","http://zhyuce.sanzhima.cn/uploads/images/20190105/08bb7d9ea9c00480eabb558ba3b3a47b.jpg","http://zhyuce.sanzhima.cn/uploads/images/20190105/e17baa4e0f9462ac91170cad762979ae.jpg"]
             * content : <p>十大范德萨个盛世嫡妃123<img src="/uploads/images/20190103/cc701e3521ea6b56c32ff9b508e93831.png" title="logo-signin1.png"/></p>
             * p_ruleid : 1,2
             * p_rule_name : 颜色,尺寸
             * video : http://zhyuce.sanzhima.cn/uploads/files/20190105/c09ea07db225f3e155ce2cd7c323fb32.mp4
             * v_pic : 3
             * is_sou : 2
             * price : 100.00
             * store : 100
             * group_id : 4
             * rule_name : 白色,大号
             */

            private String productid;
            private String name;
            private String content;
            private String p_ruleid;
            private String p_rule_name;
            private String video;
            private String v_pic;
            private String is_sou;
            private String price;
            private String store;
            private String group_id;
            private String rule_name;
            private String min_pic;
            private List<String> pic;

            public String getMin_pic() {
                return min_pic == null ? "" : min_pic;
            }

            public void setMin_pic(String min_pic) {
                this.min_pic = min_pic;
            }

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

            public String getP_ruleid() {
                return p_ruleid;
            }

            public void setP_ruleid(String p_ruleid) {
                this.p_ruleid = p_ruleid;
            }

            public String getP_rule_name() {
                return p_rule_name;
            }

            public void setP_rule_name(String p_rule_name) {
                this.p_rule_name = p_rule_name;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getV_pic() {
                return v_pic;
            }

            public void setV_pic(String v_pic) {
                this.v_pic = v_pic;
            }

            public String getIs_sou() {
                return is_sou;
            }

            public void setIs_sou(String is_sou) {
                this.is_sou = is_sou;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStore() {
                return store;
            }

            public void setStore(String store) {
                this.store = store;
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

            public List<String> getPic() {
                return pic;
            }

            public void setPic(List<String> pic) {
                this.pic = pic;
            }
        }

        public static class RuleBean {
            /**
             * ruleid : 1
             * title : 颜色
             * sub : [{"ruleid":3,"title":"白色"}]
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
                if (sub == null) {
                    return new ArrayList<>();
                }
                return sub;
            }

            public void setSub(List<SubBean> sub) {
                this.sub = sub;
            }

            public static class SubBean {
                /**
                 * ruleid : 3
                 * title : 白色
                 */

                private String ruleid;
                private String title;

                private boolean isSelected;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

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

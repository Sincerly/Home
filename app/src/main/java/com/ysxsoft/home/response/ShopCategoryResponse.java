package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/20 0020
 **/
public class ShopCategoryResponse {

    /**
     * data : [{"cid":1,"title":"男装","pid":0,"level":0,"sub":[{"cid":5,"title":"衬衫","pid":1}]},{"cid":2,"title":"女装","pid":0,"level":1,"sub":[{"cid":3,"title":"半身裙","pid":2},{"cid":4,"title":"背带裤","pid":2}]}]
     * code : 200
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cid : 1
         * title : 男装
         * pid : 0
         * level : 0
         * sub : [{"cid":5,"title":"衬衫","pid":1}]
         */

        private String cid;
        private String title;
        private String pid;
        private String level;
        private List<SubBean> sub;

        public String getCid() {
            return cid == null ? "" : cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPid() {
            return pid == null ? "" : pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getLevel() {
            return level == null ? "" : level;
        }

        public void setLevel(String level) {
            this.level = level;
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
             * cid : 5
             * title : 衬衫
             * pid : 1
             */

            private String cid;
            private String title;
            private String pid;
            private int carNum;//购物车数量
            private  boolean isSelected;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public int getCarNum() {
                return carNum;
            }

            public void setCarNum(int carNum) {
                this.carNum = carNum;
            }

            public String getCid() {
                return cid == null ? "" : cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPid() {
                return pid == null ? "" : pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }
        }
    }
}

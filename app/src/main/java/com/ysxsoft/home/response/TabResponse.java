package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/26 0026
 **/
public class TabResponse {

    /**
     * data : {"mycate":[{"cid":2,"title":"交友"}],"cate":[{"cid":3,"title":"休闲"}]}
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
        private List<MycateBean> mycate;
        private List<CateBean> cate;

        public List<MycateBean> getMycate() {
            if (mycate == null) {
                return new ArrayList<>();
            }
            return mycate;
        }

        public void setMycate(List<MycateBean> mycate) {
            this.mycate = mycate;
        }

        public List<CateBean> getCate() {
            if (cate == null) {
                return new ArrayList<>();
            }
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public static class MycateBean {
            /**
             * cid : 2
             * title : 交友
             */

            private int cid;
            private String title;

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class CateBean {
            /**
             * cid : 3
             * title : 休闲
             */

            private int cid;
            private String title;

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}

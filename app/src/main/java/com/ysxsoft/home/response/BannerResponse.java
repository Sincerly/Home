package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/12 0012
 **/
public class BannerResponse {

    /**
     * data : {"banner":[{"img":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","url":""}],"cate":[{"cid":1,"title":"推荐"}]}
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
        private List<BannerBean> banner;
        private List<CateBean> cate;

        public List<BannerBean> getBanner() {
            if (banner == null) {
                return new ArrayList<>();
            }
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
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

        public static class BannerBean {
            /**
             * img : http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
             * url :
             */

            private String img;
            private String url;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class CateBean {
            /**
             * cid : 1
             * title : 推荐
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
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}

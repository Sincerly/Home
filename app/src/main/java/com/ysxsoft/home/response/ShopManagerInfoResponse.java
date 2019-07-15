package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

public class ShopManagerInfoResponse {


    /**
     * data : {"did":1,"name":"我的超市","username":"","mobile":"15555555555","province":"河南省","city":"郑州市","county":"中原区","detail":"大学科技园","start_time":"06:00","end_time":"18:00","avatar":"http://jia.sanzhima.cn/static/admin/img/none.png","pic":"http://jia.sanzhima.cn/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg","cerficate":"http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","img":[{"imgid":"108","url":"http://jia.sanzhima.cn/uploads/images/20190702/13df66cdda90eba2997fd621443704d6.jpg"},{"imgid":"100","url":"http://jia.sanzhima.cn/uploads/apifile/20190626/495f7ad9799d1bccd06953de5457d599.jpeg"}],"type":2,"style":1}
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
         * did : 1
         * name : 我的超市
         * username :
         * mobile : 15555555555
         * province : 河南省
         * city : 郑州市
         * county : 中原区
         * detail : 大学科技园
         * start_time : 06:00
         * end_time : 18:00
         * avatar : http://jia.sanzhima.cn/static/admin/img/none.png
         * pic : http://jia.sanzhima.cn/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg
         * cerficate : http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg
         * img : [{"imgid":"108","url":"http://jia.sanzhima.cn/uploads/images/20190702/13df66cdda90eba2997fd621443704d6.jpg"},{"imgid":"100","url":"http://jia.sanzhima.cn/uploads/apifile/20190626/495f7ad9799d1bccd06953de5457d599.jpeg"}]
         * type : 2
         * style : 1
         */

        private String did;
        private String name;
        private String username;
        private String mobile;
        private String province;
        private String city;
        private String county;
        private String detail;
        private String start_time;
        private String end_time;
        private String avatar;
        private String pic;
        private String cerficate;
        private String type;
        private String style;
        private List<ImgBean> img;

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCerficate() {
            return cerficate;
        }

        public void setCerficate(String cerficate) {
            this.cerficate = cerficate;
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

        public List<ImgBean> getImg() {
            if(img==null){
                img=new ArrayList<>();
            }
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public static class ImgBean {
            /**
             * imgid : 108
             * url : http://jia.sanzhima.cn/uploads/images/20190702/13df66cdda90eba2997fd621443704d6.jpg
             */

            private String imgid;
            private String url;

            public String getImgid() {
                return imgid;
            }

            public void setImgid(String imgid) {
                this.imgid = imgid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}

package com.ysxsoft.home.response;

import com.ysxsoft.common_base.adapter.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * create by Sincerly on 2019/5/14 0014
 **/
public class CollectArticleListResponse implements Serializable{

    /**
     * data : [{"nid":18,"title":"交朋友","img":["http://jia.sanzhima.cn/uploads/apifile/20190627/238b308ff52bc187a2d3f901a299aa94.jpeg"],"style":2,"discuss_num":0,"zan_num":0,"address":"郑州市-中原区","uid":6,"addtime":"2小时前","is_guan":0,"content":"测试测试","cateid":2,"pic":[{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190627/238b308ff52bc187a2d3f901a299aa94.jpeg","picid":"107"}],"cate":"交友","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":0,"sou":0},{"nid":17,"title":"测试","img":["http://jia.sanzhima.cn/uploads/apifile/20190618/abd39a1332d4c904c6fb0d7589798012.jpeg"],"style":1,"discuss_num":1,"zan_num":2,"address":"郑州市-中原区","uid":6,"addtime":"9天前","is_guan":0,"content":"1116","cateid":2,"pic":[{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190618/abd39a1332d4c904c6fb0d7589798012.jpeg","picid":"61"}],"cate":"交友","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":1,"sou":1},{"nid":16,"title":"测试","img":["http://jia.sanzhima.cn/uploads/apifile/20190618/f51c770b7b356755b1c30f87e2d91f32.png"],"style":1,"discuss_num":0,"zan_num":0,"address":"郑州市-中原区","uid":6,"addtime":"9天前","is_guan":0,"content":"测试","cateid":2,"pic":[{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190618/f51c770b7b356755b1c30f87e2d91f32.png","picid":"59"}],"cate":"交友","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":0,"sou":0},{"nid":15,"title":"123456","img":["http://jia.sanzhima.cn/uploads/apifile/20190617/3c9c62777efbddf11eda3e38b5ed517f.jpeg","http://jia.sanzhima.cn/uploads/apifile/20190617/5b49b738ae3b5bf865e590e8e392d202.jpeg","http://jia.sanzhima.cn/uploads/apifile/20190617/15e5c42170085ca36ddcb0f57e972798.jpeg"],"style":2,"discuss_num":0,"zan_num":2,"address":"河南省-郑州市-中原区","uid":6,"addtime":"10天前","is_guan":0,"content":"测试","cateid":2,"pic":[{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/3c9c62777efbddf11eda3e38b5ed517f.jpeg","picid":"56"},{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/5b49b738ae3b5bf865e590e8e392d202.jpeg","picid":"57"},{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/15e5c42170085ca36ddcb0f57e972798.jpeg","picid":"58"}],"cate":"交友","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":1,"sou":0},{"nid":9,"title":"标题啊","img":["http://jia.sanzhima.cn/uploads/apifile/20190617/a8d11cdcedfc5d2fb551141533854125.jpg"],"style":1,"discuss_num":0,"zan_num":0,"address":"郑州市-中原区","uid":6,"addtime":"10天前","is_guan":0,"content":"内容","cateid":2,"pic":[{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/a8d11cdcedfc5d2fb551141533854125.jpg","picid":""}],"cate":"交友","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":0,"sou":0},{"nid":10,"title":"标题啊","img":["http://jia.sanzhima.cn/uploads/apifile/20190617/f22ae07a0e2e53c504959e6c14e83f5e.jpg"],"style":1,"discuss_num":1,"zan_num":0,"address":"郑州市-中原区","uid":6,"addtime":"10天前","is_guan":0,"content":"内容","cateid":2,"pic":[{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/f22ae07a0e2e53c504959e6c14e83f5e.jpg","picid":"41"}],"cate":"交友","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":0,"sou":0},{"nid":11,"title":"3","img":[],"style":2,"discuss_num":0,"zan_num":0,"address":"河南省-郑州市-中原区","uid":6,"addtime":"10天前","is_guan":0,"content":"rrree","cateid":2,"pic":[{"pic":"http://jia.sanzhima.cn/static/admin/img/none.png","picid":""}],"cate":"交友","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":0,"sou":0},{"nid":12,"title":"标题啊","img":["http://jia.sanzhima.cn/uploads/apifile/20190617/07cf8a2d21699454a2d683f0bc551325.png","http://jia.sanzhima.cn/uploads/apifile/20190617/e816e198a9e9f9bb18b03cbbdf57af74.jpg"],"style":2,"discuss_num":0,"zan_num":1,"address":"郑州市-中原区","uid":6,"addtime":"10天前","is_guan":0,"content":"内容","cateid":2,"pic":[{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/07cf8a2d21699454a2d683f0bc551325.png","picid":"51"},{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/e816e198a9e9f9bb18b03cbbdf57af74.jpg","picid":"52"}],"cate":"交友","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":0,"sou":0},{"nid":13,"title":"222","img":["http://jia.sanzhima.cn/uploads/apifile/20190617/1b98c1dd7b0d4fbac4bd23768ad5452f.jpeg","http://jia.sanzhima.cn/uploads/apifile/20190617/dc3fc1e1b412b55d7277852b276478d9.jpeg"],"style":2,"discuss_num":0,"zan_num":1,"address":"河南省-郑州市-中原区","uid":6,"addtime":"10天前","is_guan":0,"content":"46247257224","cateid":3,"pic":[{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/1b98c1dd7b0d4fbac4bd23768ad5452f.jpeg","picid":"53"},{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/dc3fc1e1b412b55d7277852b276478d9.jpeg","picid":"54"}],"cate":"休闲","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":1,"sou":0},{"nid":14,"title":"2","img":["http://jia.sanzhima.cn/uploads/apifile/20190617/69e185cd56f2f855e4357f2c22dc9137.png"],"style":1,"discuss_num":0,"zan_num":0,"address":"河南省-郑州市-中原区","uid":6,"addtime":"10天前","is_guan":0,"content":"r44445","cateid":2,"pic":[{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190617/69e185cd56f2f855e4357f2c22dc9137.png","picid":""}],"cate":"交友","avatar":"http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg","nickname":"Sincerly1","iszan":0,"sou":0}]
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

    public static class DataBean implements MultiItemEntity, Serializable {
        public static final int LAYOUT1 = 0x01;
        public static final int LAYOUT2 = 0x02;
        private boolean isChecked = false;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        @Override
        public int getItemType() {
            if ("2".equals(getStyle())) {//1有视频的，2无视频的
                return LAYOUT1;
            }
            return LAYOUT2;
        }

        /**
         * nid : 18
         * title : 交朋友
         * img : ["http://jia.sanzhima.cn/uploads/apifile/20190627/238b308ff52bc187a2d3f901a299aa94.jpeg"]
         * style : 2
         * discuss_num : 0
         * zan_num : 0
         * address : 郑州市-中原区
         * uid : 6
         * addtime : 2小时前
         * is_guan : 0
         * content : 测试测试
         * cateid : 2
         * pic : [{"pic":"http://jia.sanzhima.cn/uploads/apifile/20190627/238b308ff52bc187a2d3f901a299aa94.jpeg","picid":"107"}]
         * cate : 交友
         * avatar : http://jia.sanzhima.cn/uploads/apifile/20190626/fb2e5128bf20703892c72cd291ecb5bd.jpeg
         * nickname : Sincerly1
         * iszan : 0
         * sou : 0
         */

        private String nid;
        private String title;
        private String style;
        private String discuss_num;
        private String zan_num;
        private String address;
        private String uid;
        private String addtime;
        private String is_guan;
        private String content;
        private String cateid;
        private String cate;
        private String avatar;
        private String nickname;
        private String iszan;
        private String sou;
        private String url;
        private List<String> img;
        private List<PicBean> pic;
        private String city;
        private String county;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getDiscuss_num() {
            return discuss_num;
        }

        public void setDiscuss_num(String discuss_num) {
            this.discuss_num = discuss_num;
        }

        public String getZan_num() {
            return zan_num;
        }

        public void setZan_num(String zan_num) {
            this.zan_num = zan_num;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getIs_guan() {
            return is_guan;
        }

        public void setIs_guan(String is_guan) {
            this.is_guan = is_guan;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getCate() {
            return cate;
        }

        public void setCate(String cate) {
            this.cate = cate;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIszan() {
            return iszan;
        }

        public void setIszan(String iszan) {
            this.iszan = iszan;
        }

        public String getSou() {
            return sou;
        }

        public void setSou(String sou) {
            this.sou = sou;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public static class PicBean implements Serializable{
            /**
             * pic : http://jia.sanzhima.cn/uploads/apifile/20190627/238b308ff52bc187a2d3f901a299aa94.jpeg
             * picid : 107
             */

            private String pic;
            private String picid;

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPicid() {
                return picid;
            }

            public void setPicid(String picid) {
                this.picid = picid;
            }
        }
    }
}

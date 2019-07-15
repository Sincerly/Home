package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/12 0012
 **/
public class ArticleDetailResponse {

    /**
     * data : {"nid":2,"title":"发斯蒂芬","img":"http://jia.sanzhima.cn/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg","discuss_num":0,"see_num":0,"content":"<p>发士大夫的是<\/p>","addtime":"25天前","uid":1,"style":2,"address":"","pic":"","type":1,"avatar":"","nickname":"超级管理员"}
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
         * nid : 2
         * title : 发斯蒂芬
         * img : http://jia.sanzhima.cn/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg
         * discuss_num : 0
         * see_num : 0
         * content : <p>发士大夫的是</p>
         * addtime : 25天前
         * uid : 1
         * style : 2
         * address :
         * pic :
         * type : 1
         * avatar :
         * nickname : 超级管理员
         */

        private int nid;
        private String title;
        private String img;
        private int discuss_num;
        private int see_num;
        private String content;
        private String addtime;
        private int uid;
        private int style;
        private String address;
        private List<String> pic;
        private int type;
        private String avatar;
        private String nickname;
        private String zan_num;
        private String iszan;
        private String sou;
        private String url;

        public String getUrl() {
            return url==null?"":url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIszan() {
            return iszan == null ? "" : iszan;
        }

        public void setIszan(String iszan) {
            this.iszan = iszan;
        }

        public String getSou() {
            return sou == null ? "" : sou;
        }

        public void setSou(String sou) {
            this.sou = sou;
        }

        public String getZan_num() {
            return zan_num == null ? "" : zan_num;
        }

        public void setZan_num(String zan_num) {
            this.zan_num = zan_num;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getDiscuss_num() {
            return discuss_num;
        }

        public void setDiscuss_num(int discuss_num) {
            this.discuss_num = discuss_num;
        }

        public int getSee_num() {
            return see_num;
        }

        public void setSee_num(int see_num) {
            this.see_num = see_num;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime == null ? "" : addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<String> getPic() {
            if (pic == null) {
                return new ArrayList<>();
            }
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}

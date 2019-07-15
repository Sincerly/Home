package com.ysxsoft.home.response;

import com.ysxsoft.common_base.adapter.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/12 0012
 **/
public class HomeArticleResponse {

    /**
     * data : [{"nid":5,"title":"发的啥地方","img":["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"],"style":1,"discuss_num":0,"zan_num":0,"address":"秦皇岛市-山海关区","uid":1,"addtime":"25天前","avatar":"","nickname":"超级管理员","iszan":0,"sou":0},{"nid":4,"title":"发送到","img":["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg","http://jia.sanzhima.cn/uploads/images/20190516/2520aa489d7d2554d6c9f04a283c5bd9.png"],"style":2,"discuss_num":0,"zan_num":0,"address":"天津城区-河东区","uid":1,"addtime":"25天前","avatar":"","nickname":"超级管理员","iszan":0,"sou":0}]
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
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements MultiItemEntity {
        public static final int LAYOUT1 = 0x01;
        public static final int LAYOUT2 = 0x02;
        /**
         * nid : 5
         * title : 发的啥地方
         * img : ["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"]
         * style : 1
         * discuss_num : 0
         * zan_num : 0
         * address : 秦皇岛市-山海关区
         * uid : 1
         * addtime : 25天前
         * avatar :
         * nickname : 超级管理员
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
        private String avatar;
        private String nickname;
        private String iszan;
        private String sou;
        private List<String> img;

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getTitle() {
            return title == null ? "" : title;
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
            return address == null ? "" : address;
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
            return addtime == null ? "" : addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
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
            if (img == null) {
                return new ArrayList<>();
            }
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        @Override
        public int getItemType() {
            if("1".equals(getStyle())) {//style 1有视频的，2无视频的
                return LAYOUT2;
            }
            return LAYOUT1;
        }
    }
}

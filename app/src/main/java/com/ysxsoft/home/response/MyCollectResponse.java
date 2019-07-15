package com.ysxsoft.home.response;

import com.ysxsoft.common_base.adapter.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

public class MyCollectResponse {

    /**
     * data : [{"nid":1,"title":"啦啦啦，啦啦啦","img":["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"],"style":2,"discuss_num":37,"zan_num":0,"address":"","uid":1,"addtime":"56天前","avatar":"http://jia.sanzhima.cn/static/admin/img/none.png","nickname":"超级管理员","iszan":1,"sou":1},{"nid":3,"title":"商业广告","img":["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"],"style":2,"discuss_num":4,"zan_num":0,"address":"","uid":1,"addtime":"48天前","avatar":"http://jia.sanzhima.cn/static/admin/img/none.png","nickname":"超级管理员","iszan":1,"sou":1},{"nid":5,"title":"发的啥地方","img":["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"],"style":1,"discuss_num":1,"zan_num":0,"address":"秦皇岛市-山海关区","uid":1,"addtime":"48天前","avatar":"http://jia.sanzhima.cn/static/admin/img/none.png","nickname":"超级管理员","iszan":1,"sou":1},{"nid":6,"title":"测试","img":["http://jia.sanzhima.cn/uploads/images/20190612/fc9c0d0c81993b6849933628c806cc5a.png"],"style":2,"discuss_num":7,"zan_num":0,"address":"","uid":1,"addtime":"23天前","avatar":"http://jia.sanzhima.cn/static/admin/img/none.png","nickname":"超级管理员","iszan":1,"sou":1},{"nid":17,"title":"测试","img":["http://jia.sanzhima.cn/uploads/apifile/20190618/abd39a1332d4c904c6fb0d7589798012.jpeg"],"style":1,"discuss_num":2,"zan_num":0,"address":"郑州市-中原区","uid":6,"addtime":"17天前","avatar":"http://thirdqq.qlogo.cn/g?b=oidb&k=5WFY2RdV8ysFKmWqKyDueg&s=100","nickname":"Sincerly","iszan":1,"sou":1}]
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
         * nid : 1
         * title : 啦啦啦，啦啦啦
         * img : ["http://jia.sanzhima.cn/uploads/images/20190509/b4efd2df11a3c7829295ddf485e4ce9c.jpg"]
         * style : 2
         * discuss_num : 37
         * zan_num : 0
         * address :
         * uid : 1
         * addtime : 56天前
         * avatar : http://jia.sanzhima.cn/static/admin/img/none.png
         * nickname : 超级管理员
         * iszan : 1
         * sou : 1
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
        private String type;
        private List<String> img;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }
}

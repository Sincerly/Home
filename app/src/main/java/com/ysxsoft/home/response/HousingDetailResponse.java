package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/21 0021
 **/
public class HousingDetailResponse {

    /**
     * data : {"name":"我的超市","mobile":"15555555555","title":"通和盛世","huxing":"10","price":"1100.00","size":92,"cateid":"新房","address":"河南省郑州市中原区","detail":"大学科技园","money":"90000.00","xiu":"毛坯","tool":["冰箱","洗衣机"],"pic":["http://jia.sanzhima.cn/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg"],"content":"很好\r\n不错\r\n哈哈哈","shi":0,"ting":0,"wei":0,"cate":1,"xiuid":3,"toolid":["7","8"]}
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
         * name : 我的超市
         * mobile : 15555555555
         * title : 通和盛世
         * huxing : 10
         * price : 1100.00
         * size : 92
         * cateid : 新房
         * address : 河南省郑州市中原区
         * detail : 大学科技园
         * money : 90000.00
         * xiu : 毛坯
         * tool : ["冰箱","洗衣机"]
         * pic : ["http://jia.sanzhima.cn/uploads/images/20190516/d8786ad1cedd732d420368b4fff12d2f.jpg"]
         * content : 很好
         不错
         哈哈哈
         * shi : 0
         * ting : 0
         * wei : 0
         * cate : 1
         * xiuid : 3
         * toolid : ["7","8"]
         */

        private String name;
        private String mobile;
        private String title;
        private String huxing;
        private String price;
        private String size;
        private String cateid;
        private String address;
        private String detail;
        private String money;
        private String xiu;
        private String content;
        private String shi;
        private String ting;
        private String wei;
        private String cate;
        private String xiuid;
        private String city;
        private String province;
        private String county;
        private List<String> tool;
        private List<Pic> pic;
        private List<String> toolid;

        public String getCity() {
            return city == null ? "" : city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province == null ? "" : province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCounty() {
            return county == null ? "" : county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHuxing() {
            return huxing == null ? "" : huxing;
        }

        public void setHuxing(String huxing) {
            this.huxing = huxing;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSize() {
            return size == null ? "" : size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getCateid() {
            return cateid == null ? "" : cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDetail() {
            return detail == null ? "" : detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getMoney() {
            return money == null ? "" : money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getXiu() {
            return xiu == null ? "" : xiu;
        }

        public void setXiu(String xiu) {
            this.xiu = xiu;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getShi() {
            return shi == null ? "" : shi;
        }

        public void setShi(String shi) {
            this.shi = shi;
        }

        public String getTing() {
            return ting == null ? "" : ting;
        }

        public void setTing(String ting) {
            this.ting = ting;
        }

        public String getWei() {
            return wei == null ? "" : wei;
        }

        public void setWei(String wei) {
            this.wei = wei;
        }

        public String getCate() {
            return cate == null ? "" : cate;
        }

        public void setCate(String cate) {
            this.cate = cate;
        }

        public String getXiuid() {
            return xiuid == null ? "" : xiuid;
        }

        public void setXiuid(String xiuid) {
            this.xiuid = xiuid;
        }

        public List<String> getTool() {
            if (tool == null) {
                return new ArrayList<>();
            }
            return tool;
        }

        public void setTool(List<String> tool) {
            this.tool = tool;
        }

        public List<Pic> getPic() {
            if (pic == null) {
                return new ArrayList<>();
            }
            return pic;
        }

        public void setPic(List<Pic> pic) {
            this.pic = pic;
        }

        public List<String> getToolid() {
            if (toolid == null) {
                return new ArrayList<>();
            }
            return toolid;
        }

        public void setToolid(List<String> toolid) {
            this.toolid = toolid;
        }

        public static class Pic{
            private String pic;
            private String picid;

            public String getPic() {
                return pic == null ? "" : pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPicid() {
                return picid == null ? "" : picid;
            }

            public void setPicid(String picid) {
                this.picid = picid;
            }
        }
    }
}

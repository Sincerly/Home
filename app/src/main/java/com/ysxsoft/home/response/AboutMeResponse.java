package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/20 0020
 **/
public class AboutMeResponse {

    /**
     * data : <p style="text-align: center;"><span style="font-size: 20px;"><strong>关于我们啊</strong></span></p>
     * code : 200
     * msg : 成功
     */

    private String data;
    private String code;
    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
}

package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/20 0020
 **/
public class ActionResponse {

    /**
     * code : 200
     * msg : 成功
     */

    private String code;
    private String msg;

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
}

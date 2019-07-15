package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/26 0026
 **/
public class SignResponse {
    private String code;
    private String msg;
    private String data;

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

    public String getData() {
        return data == null ? "" : data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

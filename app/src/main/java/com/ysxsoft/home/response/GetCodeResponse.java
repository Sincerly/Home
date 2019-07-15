package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/10 0010
 **/
public class GetCodeResponse {

    /**
     * data : 363073
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

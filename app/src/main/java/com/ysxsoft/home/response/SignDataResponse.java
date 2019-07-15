package com.ysxsoft.home.response;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/6/26 0026
 **/
public class SignDataResponse {

    /**
     * data : ["2019-06-10","2019-06-25"]
     * code : 200
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<String> data;

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

    public List<String> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}

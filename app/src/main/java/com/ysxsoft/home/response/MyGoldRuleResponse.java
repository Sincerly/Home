package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/6/25 0025
 **/
public class MyGoldRuleResponse {

    /**
     * data : <ol class=" list-paddingleft-2" style="list-style-type: decimal;"><li><p>兑换规则</p></li><li><p>法水电费第三方</p></li><li><p>法水电费第三方是的<br/></p></li></ol>
     * code : 200
     * msg : 成功
     */

    private String data;
    private String code;
    private String msg;

    public String getData() {
        return data == null ? "" : data;
    }

    public void setData(String data) {
        this.data = data;
    }

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

package com.ysxsoft.home.response;

/**
 * create by Sincerly on 2019/7/1 0001
 **/
public class AlipayResponse {

    /**
     * data : alipay_sdk=alipay-sdk-php-20161101&app_id=2019021263227346&biz_content=%7B%22body%22%3A%22%5Cu5546%5Cu54c1%5Cu4e0b%5Cu5355%22%2C%22subject%22%3A%22App%22%2C%22out_trade_no%22%3A%22zfb_20190701114011300245%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fjia.sanzhima.cn%2Findex.php%2Findex%2Falipay%2Fsuper_notify&sign_type=RSA2&timestamp=2019-07-01+11%3A40%3A11&version=1.0&sign=tWQaaO9A0Hj%2B38N0GZWMz3xl7amcD9ZeGcg2rN01cOZ2TLA9S0hdbOGeLrYeJsSfW8dzVROdGgSsjgU09Qe%2FvRQyFRMi6KB9hGISOcmD6otT0Ks2wb5uhX9AqIR0K58OkydaBpy%2Br%2FvZmnPLE0zGUCgf1ds%2FizOxPhc8Nt69gJWpoBKlBMr3Mk%2BXNFWzrRkp5H5WYKLJPbJdJL2qak6%2BnQMi5AKoU7oqD4ZzJ5lteRlgotBZHrIL88LTSx4xfevD66YtcAkf9m0QVwMsMppIc26glw3bPs8GrojA28FLPLKi5%2BN6G%2FC1CVy2OBssBelsZZFP58oK961s%2Ba8YFll1bQ%3D%3D
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

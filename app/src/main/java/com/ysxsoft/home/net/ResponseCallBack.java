package com.ysxsoft.home.net;

import android.content.Context;

import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * create by Sincerly on 2019/6/11 0011
 **/
public class ResponseCallBack<T> extends StringCallback {
    private T t;

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(String response, int id) {
//        T bean= (T) JsonUtils.parseByGson(response,);
    }
}

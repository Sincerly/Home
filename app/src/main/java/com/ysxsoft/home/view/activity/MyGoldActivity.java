package com.ysxsoft.home.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.utils.WebViewUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.MyGoldRuleResponse;
import com.ysxsoft.home.response.UserInfoResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/MyGoldActivity")
public class MyGoldActivity extends BaseActivity {


    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.button1)
    TextView button1;
    @BindView(R.id.button2)
    TextView button2;
    @BindView(R.id.webView)
    WebView webView;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getMyGoldActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        WebViewUtils.init(webView);
        getRule();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_gold;
    }

    @OnClick({R.id.backLayout, R.id.button1, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.button1:
                //兑换商品
                GoldProductListActivity.start();
                break;
            case R.id.button2:
                //金币明细
                GoldHistoryActivity.start();
                break;
        }
    }

    /**
     * 获取规则
     */
    private void getRule() {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().GOLD_HISTORY_RULE)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyGoldRuleResponse resp = JsonUtils.parseByGson(response, MyGoldRuleResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                String data = resp.getData();
                                WebViewUtils.setH5Data(webView, data);
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取规则失败");
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getUserInfo() {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().USER_INFO)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoadingDialog();
                        UserInfoResponse resp = JsonUtils.parseByGson(response, UserInfoResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                UserInfoResponse.DataBean data = resp.getData();
                                String gold=data.getGold();
                                money.setText(gold);
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            //获取个人信息失败
                        }
                    }
                });
    }
}

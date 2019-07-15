package com.ysxsoft.home.view.activity;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.utils.action.GetCodeTimerUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.GetCodeResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/SetPayPwdActivity")
public class SetPayPwdActivity extends BaseActivity {
    @BindView(R.id.backWithText)
    TextView backWithText;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightWithIcon)
    TextView rightWithIcon;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.phone)
    AppCompatEditText phone;
    @BindView(R.id.code)
    AppCompatEditText code;
    @BindView(R.id.getCode)
    TextView getCode;
    @BindView(R.id.pwd)
    AppCompatEditText pwd;
    @BindView(R.id.pwd2)
    AppCompatEditText pwd2;
    @BindView(R.id.submit)
    Button submit;
    private GetCodeTimerUtils utils;
    private boolean isRunning = false;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getSetPayPwdActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        utils = GetCodeTimerUtils.getInstance();
        initTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pay_pwd;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("支付密码");
    }

    @OnClick({R.id.backLayout, R.id.getCode, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.getCode:
                if ("".equals(phone.getText().toString())) {
                    showToast("请填写手机号！");
                    return;
                }
                if (!isRunning) {
                    utils.initDelayTime(60);
                    utils.initStepTime(1);
                    utils.setOnGetCodeListener(new GetCodeTimerUtils.OnGetCodeListener() {
                        @Override
                        public void onRunning(int totalTime) {
                            getCode.setText(totalTime + "s后可重新获取");
                            isRunning = true;
                        }

                        @Override
                        public void onFinish() {
                            utils.stopDelay();
                            getCode.setText("重新获取");
                            isRunning = false;
                        }
                    });
                    utils.startDelay();
                    getCode();
                }
                break;
            case R.id.submit:
                submit();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (utils != null) {
            utils.stopDelay();
        }
    }

    /**
     * 注册
     */
    private void submit() {
        submit.setEnabled(false);
        if ("".equals(phone.getText().toString())) {
            showToast("请填写手机号！");
            submit.setEnabled(true);
            return;
        }
        if ("".equals(code.getText().toString())) {
            showToast("请填写验证码！");
            submit.setEnabled(true);
            return;
        }
        if(pwd.getText().toString().length()<6){
            showToast("密码最少6位！");
            submit.setEnabled(true);
            return;
        }
        if ("".equals(pwd.getText().toString())) {
            showToast("请填写密码！");
            submit.setEnabled(true);
            return;
        }
        if ("".equals(pwd2.getText().toString())) {
            showToast("请填写确认密码！");
            submit.setEnabled(true);
            return;
        }
        if(!pwd.getText().toString().equals(pwd2.getText().toString())){
            showToast("两次密码不一致！");
            submit.setEnabled(true);
            return;
        }
        showLoadingDialog("正在设置");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SET_PWD)
                .addParams("uid", SharedPreferencesUtils.getUid(SetPayPwdActivity.this))
                .addParams("code", code.getText().toString())
                .addParams("pass", pwd.getText().toString())
                .addParams("repass", pwd2.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if(submit!=null){
                            submit.setEnabled(true);
                        }
                        hideLoadingDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if(submit!=null){
                            submit.setEnabled(true);
                        }
                        hideLoadingDialog();
                        ActionResponse resp = JsonUtils.parseByGson(response, ActionResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                SharedPreferencesUtils.saveHasPwd(SetPayPwdActivity.this,true);
                                showToast("设置成功");
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("设置失败");
                        }
                    }
                });
    }

    private void getCode() {
        showLoadingDialog("正在获取..");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().GET_CODE)
                .addParams("mobile", phone.getText().toString())
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
                        GetCodeResponse resp = JsonUtils.parseByGson(response, GetCodeResponse.class);
                        if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                            showToast("获取验证码成功");
                        } else {
                            showToast(resp.getMsg());
                        }
                    }
                });
    }
}

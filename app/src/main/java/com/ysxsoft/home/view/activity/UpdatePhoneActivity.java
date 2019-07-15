package com.ysxsoft.home.view.activity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
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
import com.ysxsoft.home.response.RegResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/UpdatePhoneActivity")
public class UpdatePhoneActivity extends BaseActivity {
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
    @BindView(R.id.submit)
    Button submit;
    private boolean isRunning = false;
    GetCodeTimerUtils utils;
    @Autowired
    String userPhone;

    public static void start(String phone) {
        ARouter.getInstance().build(ARouterPath.getUpdatePhoneActivity()).withString("userPhone", phone).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        utils = GetCodeTimerUtils.getInstance();
        if (userPhone != null) {
            phone.setText(userPhone);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("修改手机号");
    }

    @OnClick({R.id.backLayout, R.id.getCode, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.getCode:
                //获取验证码
                if ("".equals(phone.getText().toString())) {
                    showToast("请输入手机号");
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
                }
                break;
            case R.id.submit:
                //提交
                if ("".equals(phone.getText().toString())) {
                    showToast("请输入手机号");
                    return;
                }
                if ("".equals(code.getText().toString())) {
                    showToast("请输入验证码");
                    return;
                }
                finish();
                break;
        }
    }

    private void edit() {
        showLoadingDialog("正在注册");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().EDIT_PHONE)
                .addParams("mobile", phone.getText().toString())
                .addParams("code", code.getText().toString())
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
                        RegResponse resp = JsonUtils.parseByGson(response, RegResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                SharedPreferencesUtils.saveUid(UpdatePhoneActivity.this,"");
                                LoginActivity.start();
                                finish();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("注册失败");
                        }
                    }
                });
    }

}

package com.ysxsoft.home.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
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
import com.ysxsoft.common_base.utils.KeyBoardUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.picker.CityPicker;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.view.picker.CitySelectPicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/AddAddressActivity")
public class AddAddressActivity extends BaseActivity {
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
    @BindView(R.id.username)
    AppCompatEditText username;
    @BindView(R.id.phone)
    AppCompatEditText phone;
    @BindView(R.id.ems)
    AppCompatEditText ems;
    @BindView(R.id.addressDetail)
    AppCompatEditText addressDetail;
    @BindView(R.id.addressPicker)
    AppCompatTextView addressPicker;
    @BindView(R.id.submit)
    Button submit;
    private boolean isEdit = false;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getAddAddressActivity()).navigation();
    }

    public static void start(Activity activity, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getAddAddressActivity()).navigation(activity, requestCode);
    }

    public static void start(Activity activity, Bundle bundle, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getAddAddressActivity()).with(bundle).navigation(activity, requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        handleIntent();
    }

    private String p;
    private String c;
    private String d;
    private String p1;
    private String p2;
    private String p3;
    private String p4;
    private String id;

    private void handleIntent() {
        if (getIntent() != null) {
            Intent intent = getIntent();
             id = intent.getStringExtra("id");
            if (id != null) {
                p1 = intent.getStringExtra("username");//用户名
                p2 = intent.getStringExtra("phone");//手机号
                p3 = intent.getStringExtra("ems");//邮编
                p4 = intent.getStringExtra("addressDetail");//详细地址
                p = intent.getStringExtra("p");//省
                c = intent.getStringExtra("c");//市
                d = intent.getStringExtra("d");//区
                isEdit = true;

                username.setText(p1);
                phone.setText(p2);
                ems.setText(p3);
                addressDetail.setText(p4);
                addressPicker.setText(p+c+d);

                title.setText("修改地址信息");
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("新增地址");
    }

    @OnClick({R.id.backLayout, R.id.submit, R.id.addressPicker})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.submit:
                submit.setEnabled(false);
                //保存
                if (isEdit) {
                    //修改地址
                    edit();
                } else {
                    //添加地址
                    add();
                }
                break;
            case R.id.addressPicker:
                KeyBoardUtils.hideInputMethod(this);
                CitySelectPicker cityPicker = new CitySelectPicker();
                cityPicker.initData(this);
                cityPicker.setListener(new CitySelectPicker.OnCityPickerClickListener() {
                    @Override
                    public void onSelect(String province, String city, String district) {
                        p = province;
                        c = city;
                        d = district;
                        addressPicker.setText(p + c + d);
                    }
                });
                cityPicker.show();
                break;
        }
    }

    /**
     * 注册
     */
    private void add() {
        if ("".equals(username.getText().toString())) {
            showToast("请填写联系人名称！");
            submit.setEnabled(true);
            return;
        }
        if ("".equals(phone.getText().toString())) {
            showToast("请填写手机号！");
            submit.setEnabled(true);
            return;
        }

        if ("".equals(ems.getText().toString())) {
            showToast("请填写邮编！");
            submit.setEnabled(true);
            return;
        }
        if (p == null || "".equals(p)) {
            showToast("请选择城市！");
            submit.setEnabled(true);
            return;
        }
        if ("".equals(addressDetail.getText().toString())) {
            showToast("请填写详细地址！");
            submit.setEnabled(true);
            return;
        }
        showLoadingDialog("请稍后...");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ADD_ADDRESS_INFO)
                .addParams("uid", SharedPreferencesUtils.getUid(AddAddressActivity.this))
                .addParams("name", username.getText().toString())
                .addParams("mobile", phone.getText().toString())
                .addParams("province", p)
                .addParams("city", c)
                .addParams("county", d)
                .addParams("detail", addressDetail.getText().toString())
                .addParams("code", ems.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingDialog();
                        if (submit != null) {
                            submit.setEnabled(true);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoadingDialog();
                        if (submit != null) {
                            submit.setEnabled(true);
                        }
                        ActionResponse resp = JsonUtils.parseByGson(response, ActionResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取地址失败");
                        }
                    }
                });
    }

    /**
     * 修改地址
     */
    private void edit() {
        if ("".equals(username.getText().toString())) {
            showToast("请填写联系人名称！");
            submit.setEnabled(true);
            return;
        }
        if ("".equals(phone.getText().toString())) {
            showToast("请填写手机号！");
            submit.setEnabled(true);
            return;
        }

        if ("".equals(ems.getText().toString())) {
            showToast("请填写邮编！");
            submit.setEnabled(true);
            return;
        }
        if (p == null || "".equals(p)) {
            showToast("请选择城市！");
            submit.setEnabled(true);
            return;
        }
        if ("".equals(addressDetail.getText().toString())) {
            showToast("请填写详细地址！");
            submit.setEnabled(true);
            return;
        }
        showLoadingDialog("请稍后...");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().EDIT_ORDER_ADDRESS)
                .addParams("uid", SharedPreferencesUtils.getUid(AddAddressActivity.this))
                .addParams("addressid", id)
                .addParams("name", username.getText().toString())
                .addParams("mobile", phone.getText().toString())
                .addParams("province", p)
                .addParams("city", c)
                .addParams("county", d)
                .addParams("detail", addressDetail.getText().toString())
                .addParams("code", ems.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingDialog();
                        if (submit != null) {
                            submit.setEnabled(true);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoadingDialog();
                        if (submit != null) {
                            submit.setEnabled(true);
                        }
                        ActionResponse resp = JsonUtils.parseByGson(response, ActionResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取地址失败");
                        }
                    }
                });
    }
}

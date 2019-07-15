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
import com.google.gson.Gson;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.AddJobRequestResponse;
import com.ysxsoft.home.response.BankListResponse;
import com.ysxsoft.home.response.CardListResponse;
import com.ysxsoft.home.response.JobOptionsResponse;
import com.ysxsoft.home.view.dialog.SinglePicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/AddBankActivity")
public class AddBankActivity extends BaseActivity {
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
    @BindView(R.id.bankUserName)
    AppCompatEditText bankUserName;
    @BindView(R.id.bankSelectPicker)
    AppCompatTextView bankSelectPicker;
    @BindView(R.id.bankName)
    AppCompatEditText bankName;
    @BindView(R.id.bankNo)
    AppCompatEditText bankNo;
    @BindView(R.id.submit)
    Button submit;
    private boolean isEdit = false;
    private String bankids="";
    private String id;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getAddBankActivity()).navigation();
    }

    public static void start(Activity activity, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getAddBankActivity()).navigation(activity, requestCode);
    }

    public static void start(Activity activity, Bundle bundle, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getAddBankActivity()).with(bundle).navigation(activity, requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        handleIntent();
    }

    private void handleIntent() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            if (id != null) {
                String p1 = intent.getStringExtra("username");//持卡人
                String p2 = intent.getStringExtra("bankName");//银行名称
                String p3 = intent.getStringExtra("bankDetailName");//开户行
                String p4 = intent.getStringExtra("bankNo");//银行卡号
                bankids = intent.getStringExtra("bankids");//银行卡号
                isEdit = true;

                bankUserName.setText(p1);
                bankName.setText(p3);
                bankNo.setText(p4);
                bankSelectPicker.setText(p2);

                title.setText("修改银行卡信息");
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("新增银行卡");
    }

    @OnClick({R.id.backLayout, R.id.bankSelectPicker, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.bankSelectPicker:
                //选择银行卡
                if (bank.isEmpty()) {
                    getBankList();
                } else {
                    initBankPicker();
                }
                break;
            case R.id.submit:
                submit();
                break;
        }
    }

    private void initBankPicker() {
        List<String> list = new ArrayList<>();
        int initP = 0;
        for (int i = 0; i < bank.size(); i++) {
            list.add(bank.get(i).getName());
            if (bankids.equals(bank.get(i).getBankid())) {
                initP = i;
            }
        }
        SinglePicker picker = new SinglePicker(this, R.style.BottomDialogStyle);
        picker.setData(list, initP);
        picker.setListener(new SinglePicker.OnDialogSelectListener() {
            @Override
            public void OnSelect(String data, int p) {
                if (bank == null) {
                    bank = new ArrayList<>();
                }
                if (bank.size() > p) {
                    BankListResponse.DataBean d = bank.get(p);
                    bankids = d.getBankid();
                    bankSelectPicker.setText(d.getName());
                }
            }
        });
        picker.showDialog();
    }

    private void submit() {
        if ("".equals(bankUserName.getText().toString())) {
            showToast("请填写持卡人姓名");
            return;
        }
        if (bankids == null || "".equals(bankids)) {
            showToast("请选择银行名称！");
            return;
        }
        if ("".equals(bankName.getText().toString())) {
            showToast("请填写开户行！");
            return;
        }
        if ("".equals(bankNo.getText().toString())) {
            showToast("请填写银行号！");
            return;
        }
        showLoadingDialog("正在添加");
        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().CARD_EDIT)
                .addParams("uid", SharedPreferencesUtils.getUid(AddBankActivity.this))
                .addParams("name", bankUserName.getText().toString())
                .addParams("bankid", bankids)//银行id
                .addParams("bank", bankName.getText().toString())//开户行
                .addParams("code", bankNo.getText().toString());//卡号
        if (id != null&&!"".equals(id)) {
            builder.addParams("cid", id);
        }

        builder.tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoadingDialog();
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
                            showToast("操作失败");
                        }
                    }
                });
    }

    private List<BankListResponse.DataBean> bank = new ArrayList<>();

    private void getBankList() {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().BANK_LIST)
                .addParams("uid", SharedPreferencesUtils.getUid(AddBankActivity.this))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BankListResponse resp = JsonUtils.parseByGson(response, BankListResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<BankListResponse.DataBean> data = resp.getData();
                                bank.addAll(data);
                                initBankPicker();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取失败");
                        }
                    }
                });
    }
}

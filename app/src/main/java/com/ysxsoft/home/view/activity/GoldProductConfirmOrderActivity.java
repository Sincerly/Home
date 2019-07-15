package com.ysxsoft.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.utils.WebViewUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.GoldProductConfirmOrderResponse;
import com.ysxsoft.home.response.GoldProductConfirmResponse;
import com.ysxsoft.home.response.GoldProductDetailResponse;
import com.ysxsoft.home.response.ProductDetailResponse;
import com.zhy.http.okhttp.OkHttpUtils;
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
@Route(path = "/main/GoldProductConfirmOrderActivity")
public class GoldProductConfirmOrderActivity extends BaseActivity {
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
    @BindView(R.id.addressNoneLayout)
    LinearLayout addressNoneLayout;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.addressExtraLayout)
    LinearLayout addressExtraLayout;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.pname)
    TextView pname;
    @BindView(R.id.productMoney)
    TextView productMoney;
    @BindView(R.id.productNum)
    TextView productNum;
    @BindView(R.id.totalMoney)
    TextView totalMoney;
    @BindView(R.id.size)
    TextView size;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;

    @Autowired
    String groupId;
    @Autowired
    int n;
    private String productCount = "1";
    private String orderId;

    public static void start(String groupId, int n) {
        ARouter.getInstance().build(ARouterPath.getGoldProductConfirmOrderActivity()).withString("groupId", groupId).withInt("n", n).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        sureOrder();
    }

    private void sureOrder() {
        showLoadingDialog("加载中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().GOLD_CONFIRM_ORDER)
                .addParams("group_id", groupId)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("num", String.valueOf(n))
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
                        GoldProductConfirmResponse resp = JsonUtils.parseByGson(response, GoldProductConfirmResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                GoldProductConfirmResponse.DataBean d = resp.getData();
                                GoldProductConfirmResponse.DataBean.AddressBean bean = d.getAddress();
                                groupId = d.getGroup_id();
                                productCount = d.getNum();
                                if (bean == null) {
                                    addressNoneLayout.setVisibility(View.VISIBLE);
                                    addressExtraLayout.setVisibility(View.GONE);
                                } else {
                                    addressId=bean.getId();
                                    addressNoneLayout.setVisibility(View.GONE);
                                    addressExtraLayout.setVisibility(View.VISIBLE);
                                    name.setText(bean.getName() + " " + bean.getMobile());
                                    address.setText(bean.getProvince() + bean.getCity() + bean.getCounty() + bean.getDetail());
                                }

                                GoldProductConfirmResponse.DataBean.ProductBean product = d.getProduct();
                                Glide.with(GoldProductConfirmOrderActivity.this).load(product.getPic()).into(pic);
                                pname.setText(product.getName());
                                size.setText(product.getRule_name());
                                productMoney.setText(product.getPrice() + "金币");
                                productNum.setText("x" + product.getNum());
                                totalMoney.setText(product.getAccount() + "金币");
                                num.setText("共" + product.getNum() + "件商品 合计");
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取商品详情失败");
                        }
                    }
                });
    }

    private void createOrder() {
        submit.setEnabled(false);
        showLoadingDialog("加载中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().GOLD_CREATE_ORDER)
                .addParams("group_id", groupId)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("num", String.valueOf(n))
                .addParams("addressid", String.valueOf(addressId))
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
                        GoldProductConfirmOrderResponse resp = JsonUtils.parseByGson(response, GoldProductConfirmOrderResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                orderId = resp.getData();
                                if (orderId != null) {
                                    GoldProductOrderDetailActivity.start(orderId, 0);
                                }
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("兑换失败！");
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gold_product_confirm_order;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("确认订单");
    }

    @OnClick({R.id.backLayout, R.id.addressNoneLayout, R.id.addressExtraLayout, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.addressNoneLayout:
            case R.id.addressExtraLayout:
                //选择地址
                AddressLayoutActivity.start(this, 0x01);
                break;
            case R.id.submit:
                if (IntentUtils.detectionForDoubleClick()) {
                    createOrder();
                }
                break;
        }
    }

    /**
     * 检验密码
     *
     * @param pwd
     */
    private void checkPwd(String pwd) {
        showLoadingDialog("正在检验..");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().CHECK_PWD)
                .addParams("uid", SharedPreferencesUtils.getUid(GoldProductConfirmOrderActivity.this))
                .addParams("pass", pwd)
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
                        ActionResponse resp = JsonUtils.parseByGson(response, ActionResponse.class);
                        if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                            //密码检验通过
                        } else {
                            showToast(resp.getMsg());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    //请求码
                    if (data != null) {
//                        data.getStringExtra()
                        int size=data.getIntExtra("dataSize",0);
                        if (size>0){
                            if(data.getStringArrayExtra("id")!=null){
                                addressId = data.getStringExtra("id");//地址id
                                String username = data.getStringExtra("username");//联系人名称
                                String phone = data.getStringExtra("phone");//联系人手机号
                                String ems = data.getStringExtra("ems");//邮编
                                String addressDetail = data.getStringExtra("addressDetail");//详细地址
                                String p = data.getStringExtra("p");//省
                                String c = data.getStringExtra("c");//市
                                String d = data.getStringExtra("d");//区
                                if(username!=null){
                                    name.setText(username + " " + phone);
                                    address.setText(p + c + d + addressDetail);
                                    addressNoneLayout.setVisibility(View.GONE);
                                    addressExtraLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        }else{
                            addressNoneLayout.setVisibility(View.VISIBLE);
                            addressExtraLayout.setVisibility(View.GONE);
                        }
                    }
                    break;
            }
        }
    }

    private String addressId;
}

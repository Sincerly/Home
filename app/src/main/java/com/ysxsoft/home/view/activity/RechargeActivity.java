package com.ysxsoft.home.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.pay.alipay.AlipayUtils;
import com.ysxsoft.common_base.pay.wx.WxPayUtils;
import com.ysxsoft.common_base.pay.wx.WxPaymentManager;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.orm.ShopCart;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.AlipayResponse;
import com.ysxsoft.home.response.WxResponse;
import com.ysxsoft.home.view.dialog.SingleWheelPicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/RechargeActivity")
public class RechargeActivity extends BaseActivity {
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
    @BindView(R.id.accountPicker)
    TextView accountPicker;
    @BindView(R.id.money)
    AppCompatEditText money;
    @BindView(R.id.submit)
    AppCompatButton submit;
    String payType="1";

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getRechargeActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initWx();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("余额充值");
    }

    @OnClick({R.id.backLayout, R.id.accountPicker, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.accountPicker:
                //选择充值方式  TODO:待修改
                SingleWheelPicker singleWheelPicker=new SingleWheelPicker(this,R.style.BottomDialogStyle);
                singleWheelPicker.setData(singleWheelPicker.getAlipay(),0);
                singleWheelPicker.setListener(new SingleWheelPicker.OnDialogSelectListener() {
                    @Override
                    public void OnSelect(SingleWheelPicker.Item data, int position) {
                        //Toast.makeText(RechargeActivity.this, "选择了"+data.getStr(), Toast.LENGTH_SHORT).show();
                        Drawable down = getResources().getDrawable(R.mipmap.icon_light_gray_down);
                        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());

                        if(position==0){
                            //支付宝
                            payType="1";
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_pay_alipay);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            accountPicker.setCompoundDrawables(drawable, null, down, null);
                            accountPicker.setText("支付宝账户");
                        }else{
                            //微信
                            payType="2";
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_pay_wx);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            accountPicker.setCompoundDrawables(drawable, null, down, null);
                            accountPicker.setText("微信账户");
                        }
                    }
                });
                singleWheelPicker.showDialog();
                break;
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.submit:
                if(IntentUtils.detectionForDoubleClick()){
                    pay();
                }
                break;
        }
    }


    IWXAPI iwxapi;
    private void initWx() {
        iwxapi = WxPayUtils.getInstance().reg(RechargeActivity.this);
        WxPaymentManager.getInstance().attachEvent(new WxPaymentManager.OnPayResultListener() {
            @Override
            public void onCancel() {
                showToast("支付取消！");
            }

            @Override
            public void onSuccess() {
                showToast("支付成功！");
                finish();
            }

            @Override
            public void onError() {
                showToast("支付失败！");
            }

            @Override
            public void onResponseCode(int wxResponseCode) {
                Log.e("tag", wxResponseCode + "");
            }
        });
    }

    /**
     * 支付
     */
    private void pay() {
        if("".equals(money.getText().toString())){
            showToast("请输入充值金额");
            return;
        }
        double d=Double.valueOf(money.getText().toString());
        if(d==0){
            showToast("充值金额不能为0");
            return;
        }
        showLoadingDialog("充值中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().RECHARGE)
                .addParams("num", money.getText().toString())
                .addParams("uid", SharedPreferencesUtils.getUid(RechargeActivity.this))
                .addParams("style", payType)//1支付宝，2微信
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
                        if ("1".equals(payType)) {
                            //支付宝
                            AlipayResponse resp = JsonUtils.parseByGson(response, AlipayResponse.class);
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                String data = resp.getData();
                                AlipayUtils.startAlipay(RechargeActivity.this, handler, 0x10, data);//支付宝支付
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else if ("2".equals(payType)) {
                            //微信
                            WxResponse resp = JsonUtils.parseByGson(response, WxResponse.class);
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                WxResponse.DataBean data = resp.getData();
                                WxPayUtils.WxPayReuqestParams payReuqestParams = new WxPayUtils.WxPayReuqestParams();
                                payReuqestParams.setAppid(data.getAppid());
                                payReuqestParams.setNoncestr(data.getNoncestr());
                                payReuqestParams.setPackageX(data.getPackageX());
                                payReuqestParams.setPartnerid(data.getPartnerid());
                                payReuqestParams.setPrepayid(data.getPrepayid());
                                payReuqestParams.setSign(data.getSign());
                                payReuqestParams.setTimestamp(data.getTimestamp());
                                payReuqestParams.setAppid(data.getAppid());
                                WxPayUtils.getInstance().pay(iwxapi, payReuqestParams);
                            } else {
                                showToast(resp.getMsg());
                            }
                        }
                    }
                });
    }

    //	1.支付宝
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:
                    Map<String, String> map = (Map<String, String>) msg.obj;
                    //9000支付成功  8000 正在处理中  4000 订单支付失败  5000重复请求  6001中途取消  6002网络连接出错 6004 支付结果未知  其他其他支付错误
                    if ("9000".equals(map.get("resultStatus"))) {//订单支付成功
                        Toast.makeText(RechargeActivity.this, "支付宝支付成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if ("4000".equals(map.get("resultStatus"))) {//订单支付失败
                        Toast.makeText(RechargeActivity.this, "支付宝支付失败！", Toast.LENGTH_SHORT).show();
                    } else if ("6001".equals(map.get("resultStatus"))) {//订单支付中途取消
                        Toast.makeText(RechargeActivity.this, "支付宝支付取消！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}

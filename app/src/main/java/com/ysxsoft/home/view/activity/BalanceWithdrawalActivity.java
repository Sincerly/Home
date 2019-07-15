package com.ysxsoft.home.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.CardListResponse;
import com.ysxsoft.home.response.TxResponse;
import com.ysxsoft.home.response.UserInfoResponse;
import com.ysxsoft.home.view.dialog.SingleWheelPicker;
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
@Route(path = "/main/BalanceWithdrawalActivity")
public class BalanceWithdrawalActivity extends BaseActivity {
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
    @BindView(R.id.account)
    AppCompatEditText account;
    @BindView(R.id.money)
    AppCompatEditText money;
    @BindView(R.id.allMoney)
    AppCompatTextView allMoney;
    @BindView(R.id.all)
    AppCompatTextView all;
    @BindView(R.id.alipay)
    LinearLayout alipay;
    @BindView(R.id.bankLayout)
    LinearLayout bankLayout;
    @BindView(R.id.submit)
    AppCompatButton submit;
    @BindView(R.id.bankImage)
    ImageView bankImage;
    @BindView(R.id.bankInfo)
    TextView bankInfo;
    @BindView(R.id.bindLayout)
    LinearLayout bindLayout;
    @BindView(R.id.add)
    AppCompatTextView add;
    @BindView(R.id.unbind)
    LinearLayout unbind;
    private String payType = "1";

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getBalanceWithdrawalActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        getBankList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_balance_withdrawal;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("余额提现");
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!"".equals(s.toString())) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
        getBankList();
    }

    @OnClick({R.id.backLayout, R.id.accountPicker, R.id.all, R.id.submit, R.id.add, R.id.bankLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.accountPicker:
                //选择账号
                //选择充值方式  TODO:待修改
                SingleWheelPicker singleWheelPicker = new SingleWheelPicker(this, R.style.BottomDialogStyle);
                singleWheelPicker.setData(singleWheelPicker.getTiAlipay(), 0);
                singleWheelPicker.setListener(new SingleWheelPicker.OnDialogSelectListener() {
                    @Override
                    public void OnSelect(SingleWheelPicker.Item data, int position) {
                        //Toast.makeText(RechargeActivity.this, "选择了"+data.getStr(), Toast.LENGTH_SHORT).show();
                        Drawable down = getResources().getDrawable(R.mipmap.icon_light_gray_down);
                        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());
                        if (position == 0) {
                            //支付宝
                            payType = "1";
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_pay_alipay);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            accountPicker.setCompoundDrawables(drawable, null, down, null);
                            accountPicker.setText("支付宝账户");
                            account.setHint("请输入支付宝账户");

                            alipay.setVisibility(View.VISIBLE);
                            bankLayout.setVisibility(View.GONE);
                        } else if (position == 1) {
                            //微信
                            payType = "2";
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_pay_wx);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            accountPicker.setCompoundDrawables(drawable, null, down, null);
                            accountPicker.setText("微信账户");
                            account.setHint("请输入微信账户");
                            alipay.setVisibility(View.VISIBLE);
                            bankLayout.setVisibility(View.GONE);
                        } else {
                            payType = "3";
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_pay_bank);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            accountPicker.setCompoundDrawables(drawable, null, down, null);
                            accountPicker.setText("银行卡账户");
                            alipay.setVisibility(View.GONE);
                            bankLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
                singleWheelPicker.showDialog();
                break;
            case R.id.all:
                //全部提现
                money.setText(m + "");
                break;
            case R.id.submit:
                //提现
                if ("".equals(money.getText().toString())) {
                    showToast("请输入可提现金额");
                    return;
                }
                double currentMoney = Double.valueOf(money.getText().toString());
                if (currentMoney > m) {
                    showToast("超出可提现金额");
                    return;
                }
                submit(currentMoney);
                break;
            case R.id.add:
                //添加银行卡
                BankListActivity.start();
                break;
            case R.id.bankLayout:
                //银行卡
                if(data.size()==1){
                    showToast("您只存在一张银行卡！");
                    return;
                }
                SingleWheelPicker picker = new SingleWheelPicker(this, R.style.BottomDialogStyle);
                picker.setData(picker.getCardList(data), 0);
                picker.setListener(new SingleWheelPicker.OnDialogSelectListener() {
                    @Override
                    public void OnSelect(SingleWheelPicker.Item data, int position) {
                        //Toast.makeText(RechargeActivity.this, "选择了"+data.getStr(), Toast.LENGTH_SHORT).show();
                        cardid=data.getId();
                        cardAccount=data.getAccount();
                        Glide.with(BalanceWithdrawalActivity.this).load(data.getUrl()).into(bankImage);//银行图标
                        bankInfo.setText(data.getStr());//银行卡描述
                    }
                });
                picker.showDialog();
                break;
        }
    }

    private String cardid = "";//银行卡id
    private String cardAccount = "";//银行卡账号

    private void submit(double txMoney) {
        if(m==0||m==0.0){
            showToast("可提现金额为0！");
            return;
        }

        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().TX)
                .addParams("uid", SharedPreferencesUtils.getUid(this))//用户id
                .addParams("money", txMoney + "")//提现金额
                .addParams("style", payType);//1支付宝，2微信，3银行卡
        if ("3".equals(payType)) {
            //银行卡
            builder.addParams("cardid", cardid);
            builder.addParams("code", cardAccount);
        } else {
            if ("".equals(account.getText().toString())) {
                showToast("请输入提现账户");
                return;
            }
            builder.addParams("code", account.getText().toString());
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
                        TxResponse resp = JsonUtils.parseByGson(response, TxResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                TxResponse.DataBean d=resp.getData();
                                WithdrawalSuccessActivityActivity.start(d.getTime(),d.getAccount());
                                finish();
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


    private void getUserInfo() {
        if ("".equals(SharedPreferencesUtils.getUid(BalanceWithdrawalActivity.this))) {
            return;
        }
        OkHttpUtils.post()
                .url(AppConfig.getInstance().USER_INFO)
                .addParams("uid", SharedPreferencesUtils.getUid(BalanceWithdrawalActivity.this))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserInfoResponse resp = JsonUtils.parseByGson(response, UserInfoResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                UserInfoResponse.DataBean data = resp.getData();
                                if (data.getMoney() != null && !"".equals(data.getMoney())) {
                                    m = Double.valueOf(data.getMoney());
                                    allMoney.setText("当前可提现余额为" + data.getMoney() + "元");
                                }
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            //获取个人信息失败
                        }
                    }
                });
    }

    private double m;

    /**
     * 获取列表
     */
    private void getBankList() {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().CARD_LIST)
                .addParams("uid", SharedPreferencesUtils.getUid(BalanceWithdrawalActivity.this))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CardListResponse resp = JsonUtils.parseByGson(response, CardListResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<CardListResponse.DataBean> d = resp.getData();
                                data.clear();
                                data.addAll(d);
                                if(!data.isEmpty()){
                                    cardid=data.get(0).getCid();
                                    cardAccount=data.get(0).getCode();
                                    Glide.with(BalanceWithdrawalActivity.this).load(data.get(0).getPic()).into(bankImage);//银行图标

                                    StringBuffer stringBuffer=new StringBuffer();
                                    stringBuffer.append(data.get(0).getBankid());
                                    stringBuffer.append("(");
                                    if(cardAccount.length()<=4){
                                        stringBuffer.append(cardAccount);
                                    }else{
                                        String str=cardAccount.substring(cardAccount.length()-4,cardAccount.length()-1);
                                        stringBuffer.append(str);
                                    }
                                    stringBuffer.append(")");
                                    bankInfo.setText(stringBuffer.toString());//银行卡描述
                                }

                                if (data.isEmpty()) {
                                    if(bindLayout!=null&&unbind!=null){
                                        bindLayout.setVisibility(View.GONE);
                                        unbind.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    if(bindLayout!=null&&unbind!=null){
                                        bindLayout.setVisibility(View.VISIBLE);
                                        unbind.setVisibility(View.GONE);
                                    }
                                }
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

    //卡片列表
    List<CardListResponse.DataBean> data=new ArrayList<>();
}

package com.ysxsoft.home.view.activity;

import android.os.Bundle;
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
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.GoldHistoryDetailResponse;
import com.ysxsoft.home.response.GoldProductDetailResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/GoldProductOrderDetailActivity")
public class GoldProductOrderDetailActivity extends BaseActivity {
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

    @Autowired
    String orderId;
    @Autowired
    int status;
    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.status)
    TextView statusView;
    @BindView(R.id.statusImage)
    ImageView statusImage;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.addressExtraLayout)
    LinearLayout addressExtraLayout;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.pname)
    TextView pname;
    @BindView(R.id.no1)
    TextView no1;
    @BindView(R.id.no2)
    TextView no2;
    @BindView(R.id.no3)
    TextView no3;
    @BindView(R.id.no4)
    TextView no4;
    @BindView(R.id.psize)
    TextView psize;
    @BindView(R.id.productMoney)
    TextView productMoney;
    @BindView(R.id.productNum)
    TextView productNum;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;

    public static void start(String orderId, int status) {
        ARouter.getInstance().build(ARouterPath.getGoldProductOrderDetailActivity()).withString("orderId", orderId).withInt("status", status).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        getDetail();
        //金币兑换订单  只有待发货 待收货 已完成状态
        handleIntent();
    }

    /**
     * 切换状态
     */
    private void handleIntent() {
        switch (status) {
            case 0:
                //待发货
                statusView.setText("待发货");
                submit.setText("提醒发货");
                if (bottomLayout.getVisibility() == View.GONE) {
                    bottomLayout.setVisibility(View.VISIBLE);
                }
                statusImage.setImageResource(R.mipmap.icon_dfh);
                break;
            case 1:
                //待收货
                statusView.setText("待收货");
                submit.setText("确认收货");
                if (bottomLayout.getVisibility() == View.GONE) {
                    bottomLayout.setVisibility(View.VISIBLE);
                }
                statusImage.setImageResource(R.mipmap.icon_dsh);
                break;
            case 2:
                //已完成
                statusView.setText("已完成");
                if (bottomLayout.getVisibility() == View.VISIBLE) {
                    bottomLayout.setVisibility(View.GONE);
                }
                statusImage.setImageResource(R.mipmap.icon_ywc);
                break;
        }

        if (statusView.getVisibility() == View.GONE) {
            statusView.setVisibility(View.VISIBLE);
        }
        if (statusImage.getVisibility() == View.GONE) {
            statusImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gold_product_order_detail;
    }

    @OnClick({R.id.backLayout, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.submit:
                submit();
                break;
        }
    }

    /**
     * 提交
     */
    private void submit() {
        switch (status) {
            case 0:
                //提醒发货
                //status = 1;
                notifyOrder();
                break;
            case 1:
                //确认收货
                sureOrder();
                status = 2;
                break;
        }
        handleIntent();
    }

    /**
     * 获取详情
     */
    private void getDetail() {
        showLoadingDialog("正在获取");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().GOLD_HISTORY_DETAIL)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("orderid", orderId)
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
                        GoldHistoryDetailResponse resp = JsonUtils.parseByGson(response, GoldHistoryDetailResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                GoldHistoryDetailResponse.DataBean d = resp.getData();
                                name.setText(d.getUsername());
                                phone.setText(d.getMobile());
                                address.setText(d.getAddress());
                                Glide.with(GoldProductOrderDetailActivity.this).load(d.getPic()).into(pic);
                                pname.setText(d.getGname());
                                psize.setText(d.getRule_name());
                                productMoney.setText(d.getPrice() + "金币");
                                productNum.setText("x" + d.getNum());
                                money.setText(d.getAccount() + "金币");

                                if ("1".equals(d.getStatus())) {
                                    //待发货
                                    status=0;
                                }else if("2".equals(d.getStatus())){
                                    //待收货
                                    status=1;
                                } else {
                                    //已完成
                                    status=2;
                                }
                                handleIntent();
                                no1.setText("订单编号："+d.getDsn());
                                no2.setText("订单时间："+d.getAddtime());
                                no3.setText("支付方式：在线支付");
                                no4.setText("支付时间："+d.getAddtime());
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取详情失败");
                        }
                    }
                });
    }

    /**
     * 提醒发货
     */
    private void notifyOrder(){
        OkHttpUtils.post()
                .url(AppConfig.getInstance().NOTIFY)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("orderid", orderId)
                .addParams("type", "2")//1美食，2超市（金币同超市）
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
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                showToast(resp.getMsg());
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取详情失败");
                        }
                    }
                });
    }

    /**
     * 确认订单
     */
    private void sureOrder(){
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SURE)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("orderid", orderId)
                .addParams("type", "2")//1美食，2超市（金币同超市）
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
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                getDetail();
                                EventBus.getDefault().post("","refreshGoldOrderList");
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("确认收货失败");
                        }
                    }
                });
    }
}

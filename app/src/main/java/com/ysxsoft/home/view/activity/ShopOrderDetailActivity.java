package com.ysxsoft.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.pay.alipay.AlipayUtils;
import com.ysxsoft.common_base.pay.wx.WxPayUtils;
import com.ysxsoft.common_base.pay.wx.WxPaymentManager;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.orm.ShopCart;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.AlipayResponse;
import com.ysxsoft.home.response.ShopOrderDetailResponse;
import com.ysxsoft.home.response.ShopOrderResponse;
import com.ysxsoft.home.response.WxResponse;
import com.ysxsoft.home.view.dialog.BottomPayDialog;
import com.ysxsoft.home.view.dialog.BottomPwdDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/ShopOrderDetailActivity")
public class ShopOrderDetailActivity extends BaseActivity {


    @BindView(R.id.status)
    TextView status;
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.totalCount)
    TextView totalCount;
    @BindView(R.id.coupon)
    TextView coupon;
    @BindView(R.id.orderTotalmoney)
    TextView orderTotalmoney;
    @BindView(R.id.leftMenu)
    Button leftMenu;
    @BindView(R.id.rightMenu)
    Button rightMenu;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.backWithText)
    TextView backWithText;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.rightWithIcon)
    TextView rightWithIcon;
    @BindView(R.id.bg)
    LinearLayout bg;

    @Autowired
    int orderStatus;
    @Autowired
    boolean isShop;
    @Autowired
    String orderId;
    @Autowired
    String shopId;
    @BindView(R.id.orderNo)
    TextView orderNo;
    @BindView(R.id.orderTime)
    TextView orderTime;
    @BindView(R.id.orderPayType)
    TextView orderPayType;
    @BindView(R.id.orderPayTime)
    TextView orderPayTime;

    public static void start(int orderStatus) {
        ARouter.getInstance().build(ARouterPath.getShopOrderDetailActivity()).withInt("orderStatus", orderStatus).navigation();
    }

    public static void start(String orderId, String shopId, boolean isShop) {
        ARouter.getInstance().build(ARouterPath.getShopOrderDetailActivity()).withString("orderId", orderId).withString("shopId", shopId).withBoolean("isShop", isShop).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initWx();
//        handleStatus();
//        initProductList();
        getDetail();
    }

    private void getDetail() {
        showLoadingDialog("获取订单详情");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ORDER_DETAIL)
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
                        ShopOrderDetailResponse resp = JsonUtils.parseByGson(response, ShopOrderDetailResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                ShopOrderDetailResponse.DataBean dataBean = resp.getData();
                                name.setText(dataBean.getUsername() + " " + dataBean.getMobile());
                                address.setText(dataBean.getAddress());
                                initProductList(dataBean.getProduct());
                                //配送费
                                money.setText("￥" + dataBean.getYun());
                                totalCount.setText("共" + dataBean.getProduct().size() + "件商品 总计：");
                                orderTotalmoney.setText("￥" + dataBean.getAccount());

                                orderNo.setText("订单编号：" + dataBean.getDsn());//订单编号
                                orderTime.setText("订单时间：" + dataBean.getAddtime());//订单时间
                                String payType = "在线支付";//1支付宝，2微信，3余额
                                if ("1".equals(dataBean.getPaytype())) {
                                    payType = "支付宝";
                                } else if ("2".equals(dataBean.getPaytype())) {
                                    payType = "微信";
                                } else if ("3".equals(dataBean.getPaytype())) {
                                    payType = "余额";
                                }
                                orderPayType.setText("支付方式：" + payType);//支付方式
                                orderPayTime.setText("支付时间：" + dataBean.getPaytime());//支付时间

                                boolean showPayTime = true;
                                boolean showLeft = false;
                                boolean showRight = false;
                                String str = "";
                                String leftStr = "";
                                String rightStr = "";
                                boolean showTime = false;
                                switch (dataBean.getType()) {
                                    case "1":
                                        //待付款
                                        str = "待支付";
                                        showPayTime = false;
                                        showLeft = true;//取消订单
                                        showRight = true;//去支付
                                        bottomLayout.setVisibility(View.VISIBLE);
                                        statusImage.setImageResource(R.mipmap.icon_dfk);
                                        time.setVisibility(View.VISIBLE);
                                        if (isShop) {
                                            leftStr = "";
                                            rightStr = "";
                                        } else {
                                            leftStr = "取消订单";
                                            rightStr = "立即支付";
                                        }
                                        break;
                                    case "2":
                                        //待发货
                                        str = "待发货";
                                        showLeft = true;//申请退款 仅退款
                                        showRight = true;//提醒发货
                                        bottomLayout.setVisibility(View.VISIBLE);
                                        statusImage.setImageResource(R.mipmap.icon_dfh);
                                        if (isShop) {
                                            leftStr = "";
                                            rightStr = "确认发货";
                                            showLeft = false;//
                                            showRight = true;//
                                            showTime = true;
                                            time.setText("买家已付款，等待发货");
                                        } else {
                                            leftStr = "申请退款";
                                            rightStr = "提醒发货";
                                        }
                                        break;
                                    case "3":
                                        //待收货
                                        str = "待收货";
                                        showLeft = true;//申请退款 退货退款
                                        showRight = true;//确认收货
                                        bottomLayout.setVisibility(View.VISIBLE);
                                        statusImage.setImageResource(R.mipmap.icon_dsh);
                                        if (isShop) {
                                            leftStr = "";
                                            rightStr = "";
                                            showLeft = false;
                                            showRight = false;
                                            showTime = true;
                                            time.setText("已发货，等待买家收货");
                                        } else {
                                            leftStr = "申请退款";
                                            rightStr = "确认收货";
                                        }
                                        break;
                                    case "4":
                                        //已完成
                                        str = "已完成";
                                        showLeft = true;//删除订单
                                        showRight = false;
                                        statusImage.setImageResource(R.mipmap.icon_ywc);
                                        bottomLayout.setVisibility(View.VISIBLE);
                                        if (isShop) {
                                            leftStr = "";
                                            rightStr = "";
                                        } else {
                                            leftStr = "删除订单";
                                            rightStr = "";
                                        }
                                        break;
                                    case "5":
                                        //退款中
                                        str = "待退货";
                                        showLeft = false;
                                        showRight = true;
                                        statusImage.setImageResource(R.mipmap.icon_dfh);
                                        bottomLayout.setVisibility(View.VISIBLE);
                                        if (isShop) {
                                            leftStr = "";
                                            rightStr = "";
                                            showRight=false;
                                        } else {
                                            leftStr = "";
                                            rightStr = "我已退货";
                                        }
                                        break;
                                    case "6":
                                        //申请退款中
                                        str = "申请退款中";
                                        showLeft = false;
                                        showRight = true;
                                        if (isShop) {
                                            leftStr = "拒绝退款";
                                            rightStr = "确认退款";
                                            showLeft = true;
                                            showRight = true;
                                        } else {
                                            leftStr = "";//取消申请
                                            rightStr = "查看详情";
                                        }
                                        break;
                                    case "7":
                                        //已退款
                                        str = "已退款";
                                        showLeft = false;
                                        showRight = true;
                                        if (isShop) {
                                            leftStr = "";
                                            rightStr = "";
                                            showLeft = false;
                                            showRight = false;
                                        } else {
                                            leftStr = "";
                                            rightStr = "查看详情";
                                        }
                                        break;
                                    case "8":
                                        //退款申请被拒绝
                                        str = "退款申请被拒绝";
                                        showLeft = false;
                                        showRight = true;
                                        if (isShop) {
                                            leftStr = "";
                                            rightStr = "";
                                            showRight=false;
                                        } else {
                                            leftStr = "";
                                            rightStr = "查看详情";
                                        }
                                        break;
                                }
                                status.setVisibility(View.VISIBLE);
                                statusImage.setVisibility(View.VISIBLE);
                                status.setText(str);
                                if (showTime) {
                                    time.setVisibility(View.VISIBLE);
                                } else {
                                    time.setVisibility(View.GONE);
                                }

                                if (showPayTime) {
                                    orderPayType.setVisibility(View.VISIBLE);
                                    orderPayTime.setVisibility(View.VISIBLE);
                                } else {
                                    orderPayType.setVisibility(View.GONE);
                                    orderPayTime.setVisibility(View.GONE);
                                }
                                if (dataBean.getQuan() != null) {
                                    ShopOrderDetailResponse.DataBean.ItemBean quan = dataBean.getQuan();
                                    String man = quan.getMan();
                                    String size = quan.getSize();
                                    coupon.setText("满" + man + "减" + size);
                                } else {
                                    coupon.setText("无");
                                }
                                //控制左按钮
                                if (showLeft) {
                                    leftMenu.setVisibility(View.VISIBLE);
                                } else {
                                    leftMenu.setVisibility(View.GONE);
                                }
                                //控制右按钮
                                if (showRight) {
                                    rightMenu.setVisibility(View.VISIBLE);
                                } else {
                                    rightMenu.setVisibility(View.GONE);
                                }
                                leftMenu.setText(leftStr);
                                rightMenu.setText(rightStr);
                                leftMenu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        switch (dataBean.getType()) {
                                            case "1":
                                                //待付款 取消订单
                                                if (isShop) {
                                                } else {
                                                    cancelOrder(orderId);
                                                }
                                                break;
                                            case "2":
                                                //待发货 申请退款 仅退款
                                                if (isShop) {
                                                } else {
                                                    ShopOrderbackActivity.start(ShopOrderDetailActivity.this, 0x01, true, orderId, 0);
                                                }
                                                break;
                                            case "3":
                                                //待收货 申请退款 退货退款
                                                if (isShop) {
                                                } else {
                                                    ShopOrderbackActivity.start(ShopOrderDetailActivity.this, 0x01, true, orderId, 1);
                                                }
                                                break;
                                            case "4":
                                                //已完成 删除订单
                                                deleteOrder(orderId);
                                                break;
                                            case "5":
                                                //退款中(隐藏)
                                                break;
                                            case "6":
                                                //申请退款中
                                                if (isShop) {
                                                    //拒绝退款
                                                    ShopManagerSubmitMessageActivity.start(true,orderId);
                                                } else {
                                                    //隐藏
                                                }
                                                break;
                                            case "7":
                                                //已退款 (隐藏)
                                                if(isShop){
                                                }else{
                                                    //查看详情
                                                    ShopOrderbackDetailActivity.start(orderId,false);
                                                }
                                                break;
                                            case "8":
                                                //退款申请被拒绝  (隐藏)
                                                break;
                                        }
                                    }
                                });
                                rightMenu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        switch (dataBean.getType()) {
                                            case "1": //立即支付
                                                showDialog(orderId, dataBean.getQuanid(), dataBean.getAccount());
                                                break;
                                            case "2":
                                                //待发货
                                                if (isShop) {
                                                    //确认发货
                                                    fa(orderId);
                                                } else {
                                                    //提醒发货
                                                    notifyOrder(orderId);
                                                }
                                                break;
                                            case "3":
                                                //待收货 确认收货
                                                if (isShop) {
                                                    //隐藏
                                                }else{
                                                    sureOrder(orderId);
                                                }
                                                break;
                                            case "4":
                                                //已完成
                                                break;
                                            case "5":
                                                //退款中 我已退货
                                                if(isShop){

                                                }else{
                                                    tuiOrder(orderId);
                                                }
                                                break;
                                            case "6":
                                                //申请退款中
                                                if(isShop){
                                                    //确认退款
                                                    sure(orderId);
                                                }else{
                                                    //查看详情
                                                    ShopOrderbackDetailActivity.start(orderId,false);
                                                }
                                                break;
                                            case "7":
                                                //已退款 查看详情
                                                if(isShop){
                                                    //隐藏
                                                }else{
                                                    //查看详情
                                                    ShopOrderbackDetailActivity.start(orderId,false);
                                                }
                                                break;
                                            case "8":
                                                //退款申请被拒绝  查看详情
                                                if(isShop){
                                                    //隐藏
                                                }else{
                                                    //查看详情
                                                    ShopOrderbackDetailActivity.start(orderId,false);
                                                }
                                                break;
                                        }
                                    }
                                });
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_order_detail;
    }

    @OnClick({R.id.backLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                EventBus.getDefault().post("", "refreshShopOrderList");//刷新家超市订单列表
                backToActivity();
                break;
        }
    }

    private void initProductList(List<ShopOrderDetailResponse.DataBean.ProductBean> product) {
        //填充商品
        recyclerView.setNestedScrollingEnabled(false);

        if (!product.isEmpty()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(ShopOrderDetailActivity.this));
            recyclerView.setAdapter(new RBaseAdapter<ShopOrderDetailResponse.DataBean.ProductBean>(ShopOrderDetailActivity.this, R.layout.item_shop_order_child_product, product) {

                @Override
                protected void fillItem(RViewHolder holder, ShopOrderDetailResponse.DataBean.ProductBean item, int position) {
                    ImageView pic = holder.getView(R.id.pic);
                    Glide.with(ShopOrderDetailActivity.this).load(item.getPic()).into(pic);
                    holder.setText(R.id.name, item.getGname());//商品名字
                    holder.setText(R.id.price, item.getPrice());//商品价格
                    holder.setText(R.id.num, "x" + item.getNum());//商品数量
                    holder.setText(R.id.ruleName, item.getRule_name());
                }

                @Override
                protected int getViewType(ShopOrderDetailResponse.DataBean.ProductBean item, int position) {
                    return 0;
                }
            });
        }
    }

    /**
     * 确认退货
     */
    private void sure(String orderId) {
        showLoadingDialog("正在提交");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ORDER_SURE_REFUSE)
                .addParams("orderid", orderId)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("type", "1")//1超市，2美食
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
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("提交失败");
                        }
                    }
                });
    }



    private void fa(String orderId){
        showLoadingDialog("提交中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ORDER_FAHUO)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("orderid", orderId)
                .addParams("type", "1")//1超市，2美食
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
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("发货失败");
                        }
                    }
                });
    }

    /**
     * 取消订单(超市/美食)
     */
    private void cancelOrder(String orderId) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().CANCEL)
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
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("取消订单失败");
                        }
                    }
                });
    }

    /**
     * 提醒发货(超市/美食)
     */
    private void notifyOrder(String orderId) {
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
     * 确认收货(超市/美食)
     */
    private void sureOrder(String orderId) {
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

    /**
     * 删除已完成订单(超市/美食)
     */
    private void deleteOrder(String orderId) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().DELETE)
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
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("删除订单失败");
                        }
                    }
                });
    }

    /**
     * 我已退货
     */
    private void tuiOrder(String orderId) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SUBMIT_TUI_HUO)
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
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("退货失败");
                        }
                    }
                });
    }

    IWXAPI iwxapi;

    private void initWx() {
        iwxapi = WxPayUtils.getInstance().reg(ShopOrderDetailActivity.this);
        WxPaymentManager.getInstance().attachEvent(new WxPaymentManager.OnPayResultListener() {
            @Override
            public void onCancel() {
                showToast("支付取消！");
            }

            @Override
            public void onSuccess() {
                showToast("支付成功！");
                if (dialog != null) {
                    dialog.dismiss();
                }
                ShopCart.clearByType(0);//清空超市订单
                getDetail();
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

    BottomPayDialog dialog;

    private void showDialog(String orderId, String quanid, String money) {
        dialog = new BottomPayDialog(ShopOrderDetailActivity.this, R.style.BottomDialogStyle);
        dialog.setMoneyStr(money);
        dialog.setOnDialogListener(new BottomPayDialog.OnDialogListener() {
            @Override
            public void onConfirm(int type) {
                switch (type) {
                    case 0:
                        //余额
                        if (SharedPreferencesUtils.isHasPwd(ShopOrderDetailActivity.this)) {
                            showPwdDialog(orderId, quanid);
                        } else {
                            pay(orderId, quanid, "3");
                        }
                        break;
                    case 1:
                        //支付宝
                        //showToast("Alipay支付！");
                        pay(orderId, quanid, "1");//1支付宝，2微信，3余额
                        break;
                    case 2:
                        //微信
                        //showToast("微信支付！");
                        pay(orderId, quanid, "2");
                        break;
                }
            }
        });
        dialog.showDialog();
    }

    /**
     * 支付
     */
    private void pay(String orderId, String quanid, String payType) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SHOP_PRODUCT_PAY)
                .addParams("orderid", orderId)
                .addParams("uid", SharedPreferencesUtils.getUid(ShopOrderDetailActivity.this))
                .addParams("type", payType)//1支付宝，2微信，3余额
                .addParams("quanid", quanid)//优惠券id
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if ("1".equals(payType)) {
                            //支付宝
                            AlipayResponse resp = JsonUtils.parseByGson(response, AlipayResponse.class);
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                String data = resp.getData();
                                AlipayUtils.startAlipay(ShopOrderDetailActivity.this, handler, 0x10, data);//支付宝支付
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
                        } else if ("3".equals(payType)) {
                            //余额
                            ActionResponse resp = JsonUtils.parseByGson(response, ActionResponse.class);
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                ShopCart.clearByType(0);//清空超市订单
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                    }
                                });
                                getDetail();
                            } else {
                                showToast(resp.getMsg());
                            }
                        }
//                        if (resp != null) {
//                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
//                                //请求成功
//                            } else {
//                                //请求失败
//                                showToast(resp.getMsg());
//                            }
//                        } else {
//                            showToast("修改地址信息失败");
//                        }
                    }
                });
    }

    BottomPwdDialog pwdDialog;

    private void showPwdDialog(String orderId, String quanId) {
        //InputPwdActivityActivity.start();
        pwdDialog = new BottomPwdDialog(ShopOrderDetailActivity.this, R.style.BottomPwdStyle);
        pwdDialog.setOnDialogListener(new BottomPwdDialog.OnDialogListener() {
            @Override
            public void onInput(String pwd) {
                checkPwd(orderId, quanId, pwd);
            }
        });
        pwdDialog.showDialog();
    }

    /**
     * 检验密码
     *
     * @param pwd
     */
    private void checkPwd(String orderId, String quanId, String pwd) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().CHECK_PWD)
                .addParams("uid", SharedPreferencesUtils.getUid(ShopOrderDetailActivity.this))
                .addParams("pass", pwd)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ActionResponse resp = JsonUtils.parseByGson(response, ActionResponse.class);
                        if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                            //密码检验通过
                            if (pwdDialog != null) {
                                pwdDialog.dismiss();
                            }
                            pay(orderId, quanId, "3");
                        } else {
                            showToast(resp.getMsg());
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
                        Toast.makeText(ShopOrderDetailActivity.this, "支付宝支付成功！", Toast.LENGTH_SHORT).show();
                        ShopCart.clearByType(0);//清空超市订单
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        getDetail();
                    } else if ("4000".equals(map.get("resultStatus"))) {//订单支付失败
                        Toast.makeText(ShopOrderDetailActivity.this, "支付宝支付失败！", Toast.LENGTH_SHORT).show();
                    } else if ("6001".equals(map.get("resultStatus"))) {//订单支付中途取消
                        Toast.makeText(ShopOrderDetailActivity.this, "支付宝支付取消！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    //申请完退款直接关闭页面
                    if(isShop){
                        EventBus.getDefault().post("", "refreshShopManagerOrderList");//刷新家超市管理订单列表
                    }else{
                        EventBus.getDefault().post("", "refreshShopOrderList");//刷新家超市订单列表
                    }
                    finish();
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isShop){
                EventBus.getDefault().post("", "refreshShopManagerOrderList");//刷新家超市管理订单列表
            }else{
                EventBus.getDefault().post("", "refreshShopOrderList");//刷新家超市订单列表
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

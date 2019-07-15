package com.ysxsoft.home.view.fragment.order;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.pay.alipay.AlipayUtils;
import com.ysxsoft.common_base.pay.wx.WxPayUtils;
import com.ysxsoft.common_base.pay.wx.WxPaymentManager;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.KeyBoardUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.orm.ShopCart;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.AlipayResponse;
import com.ysxsoft.home.response.ShopOrderResponse;
import com.ysxsoft.home.response.WxResponse;
import com.ysxsoft.home.view.activity.ShopOrderDetailActivity;
import com.ysxsoft.home.view.activity.ShopOrderbackActivity;
import com.ysxsoft.home.view.activity.ShopOrderbackDetailActivity;
import com.ysxsoft.home.view.dialog.BottomPayDialog;
import com.ysxsoft.home.view.dialog.BottomPwdDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/28 0028
 **/
public class ShopOrderChildFragment extends BaseFragment implements IListAdapter<ShopOrderResponse.DataBean> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    ListManager manager;
    @Autowired
    int tabPage;//0 全部订单 1 待付款 2待发货  3待收货 4退款单 5已完成

    public static ShopOrderChildFragment newInstance(int tabPage) {
        Bundle args = new Bundle();
        args.putInt("tabPage", tabPage);
        ShopOrderChildFragment fragment = new ShopOrderChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop_order_child;
    }

    @Override
    protected void doWork(View view) {
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        initList(view);
        initWx();
    }

    private void initList(View view) {
        manager = new ListManager(this);
        manager.init(view);
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopOrderResponse.DataBean d = (ShopOrderResponse.DataBean) adapter.getItem(position);
                if (tabPage == 4) {
                    //退款单
                    ShopOrderbackDetailActivity.start(d.getOrderid(), false);
                } else {
                    //普通单
                    ShopOrderDetailActivity.start(d.getOrderid(), "", false);
                }
//                switch (tabPage) {
//                    case 0:
//                        //全部
//                        if (position == 0) {
//                            //自定义状态 0待支付 1待发货 2待收货 3退款单 4已完成
//                           ShopOrderDetailActivity.start(0);
//                        } else if (position== 1) {
//                            ShopOrderDetailActivity.start(1);
//                        } else if (position== 2) {
//                            ShopOrderDetailActivity.start(2);
//                        } else if (position == 3) {
//                            //ShopOrderDetailActivity.start(0);
//                            ShopOrderbackDetailActivity.start();
//                        } else if (position == 4) {
//                            ShopOrderDetailActivity.start(4);
//                        } else {
//                            //其他点击默认已完成
//                            ShopOrderDetailActivity.start(4);
//                        }
//                        break;
//                    case 1:
//                        //待付款
//                        ShopOrderDetailActivity.start(0);
//                        break;
//                    case 2:
//                        //待发货
//                        ShopOrderDetailActivity.start(1);
//                        break;
//                    case 3:
//                        //待收货
//                        ShopOrderDetailActivity.start(2);
//                        break;
//                    case 4:
//                        //退款单
//                        ShopOrderbackDetailActivity.start();
//                        break;
//                    case 5:
//                        //已完成
//                        ShopOrderDetailActivity.start(4);
//                        break;
//                }
            }
        });
        request(1);
    }

    @Override
    public View createTitleView() {
        return null;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_fragment_shop_order_child;
    }

    @Override
    public void request(int page) {
        //int tabPage;//0 全部订单 1 待付款 2待发货  3待收货 4退款单 5已完成
        int paramsStatus = 0;
        switch (tabPage) {
            case 0:
                paramsStatus = 0;
                break;
            case 1:
                paramsStatus = 1;
                break;
            case 2:
                paramsStatus = 2;
                break;
            case 3:
                paramsStatus = 3;
                break;
            case 4:
                paramsStatus = 5;//退款单
                break;
            case 5:
                paramsStatus = 4;//已完成
                break;
        }
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SHOP_ORDER)
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
                .addParams("page", String.valueOf(page))
                .addParams("status", paramsStatus + "")//0全部，1待支付，2待发货，3待收货，4已完成，5退款订单
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingDialog();
                        manager.releaseRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        manager.releaseRefresh();
                        hideLoadingDialog();
                        ShopOrderResponse resp = JsonUtils.parseByGson(response, ShopOrderResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<ShopOrderResponse.DataBean> data = resp.getData();
                                manager.setData(data);
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
    public void fillView(BaseViewHolder helper, ShopOrderResponse.DataBean s) {
        //订单号
        helper.setText(R.id.orderNo, "订单号：" + s.getDsn());
        //状态
        String str = "";
        switch (s.getType()) {
            case "1":
                //待付款
                str = "待付款";
                break;
            case "2":
                //待发货
                str = "待发货";
                break;
            case "3":
                //待收货
                str = "待收货";
                break;
            case "4":
                //已完成
                str = "已完成";
                break;
            case "6":
                //申请退款中
                str = "申请退款中";
                break;
            case "7":
                //已退款
                str = "已退款";
                break;
            case "8":
                //退款申请被拒绝
                str = "退款申请被拒绝";
                break;
        }
        helper.setText(R.id.orderStatus, str);
        helper.setText(R.id.orderProductTotalNum, "共" + s.getNum() + "件商品 实付");//订单总数量
        helper.setText(R.id.orderProductTotalPrice, "￥" + s.getAccount());//订单总价格

        //填充商品
        RecyclerView productRecyclerView = helper.getView(R.id.productRecyclerView);
        productRecyclerView.setNestedScrollingEnabled(false);
        if (s.getProduct() != null && !s.getProduct().isEmpty()) {

            productRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            productRecyclerView.setAdapter(new RBaseAdapter<ShopOrderResponse.DataBean.ProductBean>(getActivity(), R.layout.item_shop_order_child_product, s.getProduct()) {

                @Override
                protected void fillItem(RViewHolder holder, ShopOrderResponse.DataBean.ProductBean item, int position) {
                    ImageView pic = holder.getView(R.id.pic);
                    Glide.with(getActivity()).load(item.getPic()).into(pic);
                    holder.setText(R.id.name, item.getGname());//商品名字
                    holder.setText(R.id.price, item.getPrice());//商品价格
                    holder.setText(R.id.num, "x" + item.getNum());//商品数量
                    holder.setText(R.id.ruleName, item.getRule_name());
                }

                @Override
                protected int getViewType(ShopOrderResponse.DataBean.ProductBean item, int position) {
                    return 0;
                }
            });
        }
        productRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View view = helper.getConvertView();
                return view.onTouchEvent(event);
            }
        });

        //总状态

        //左按钮
        boolean isShowLeft = false;
        Button left = helper.getView(R.id.left);
        switch (s.getType()) {
            case "1":
                //待付款
                str = "取消订单";
                isShowLeft = true;
                break;
            case "2":
                //待发货
                str = "申请退款";
                isShowLeft = true;
                break;
            case "3":
                //待收货
                str = "申请退款";
                isShowLeft = true;
                break;
            case "4":
                //已完成
                str = "删除订单";
                isShowLeft = true;
                break;
            case "6":
                //申请退款中
                str = "";
                isShowLeft = false;
                break;
            case "7":
                //已退款
                str = "";
                isShowLeft = false;
                break;
            case "8":
                //退款申请被拒绝
                str = "";
                isShowLeft = false;
                break;
        }
        left.setText(str);
        if (isShowLeft) {
            left.setVisibility(View.VISIBLE);
        } else {
            left.setVisibility(View.GONE);
        }
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件
                switch (s.getType()) {
                    case "1":
                        //代付款 取消订单
                        cancelOrder(helper.getAdapterPosition(), s.getOrderid());
                        break;
                    case "2":
                        //代发货 申请退款-仅退款
                        ShopOrderbackActivity.start(true, s.getOrderid(), 0);
                        break;
                    case "3":
                        //待收货 申请退款-退货退款
                        ShopOrderbackActivity.start(true, s.getOrderid(), 1);
                        break;
                    case "4":
                        //已完成 删除订单
                        deleteOrder(helper.getAdapterPosition(), s.getOrderid());
                        break;
                    case "6":
                        //申请退款中  隐藏
                        break;
                    case "7":
                        //已退款  隐藏
                        break;
                    case "8":
                        //退款申请被拒绝  隐藏
                        break;
                }
            }
        });
        //右按钮
        Button right = helper.getView(R.id.right);
        boolean showRight = false;
        switch (s.getType()) {
            case "1":
                //待付款
                str = "立即支付";
                showRight = true;
                break;
            case "2":
                //待发货
                str = "提醒发货";
                showRight = true;
                break;
            case "3":
                //待收货
                str = "确认收货";
                showRight = true;
                break;
            case "4":
                //已完成  隐藏
                str = "";
                showRight = false;
                break;
            case "6":
                //申请退款中
                str = "查看详情";
                showRight = true;
                break;
            case "7":
                //已退款
                str = "查看详情";
                showRight = true;
                break;
            case "8":
                //退款申请被拒绝
                str = "查看详情";
                showRight = true;
                break;
        }
        right.setText(str);
        if (showRight) {
            right.setVisibility(View.VISIBLE);
        } else {
            right.setVisibility(View.GONE);
        }

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件
                switch (s.getType()) {
                    case "1":
                        //待付款 立即支付
                        showDialog(s.getOrderid(), s.getQuanid(), s.getAccount());
                        //ShopConfirmOrderActivity.start(s.getOrderid());//立即支付
                        break;
                    case "2":
                        //待发货 提醒发货
                        notifyOrder(s.getOrderid());
                        break;
                    case "3":
                        //待收货 确认收货
                        sureOrder(helper.getAdapterPosition(), s.getOrderid());
                        break;
                    case "4":
                        //已完成  隐藏
                        break;
                    case "6":
                        //申请退款中  查看详情
                        ShopOrderbackDetailActivity.start(s.getOrderid(), false);
                        break;
                    case "7":
                        //已退款 查看详情
                        ShopOrderbackDetailActivity.start(s.getOrderid(), false);
                        break;
                    case "8":
                        //退款申请被拒绝  查看详情
                        break;
                }
            }
        });
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, ShopOrderResponse.DataBean s) {

    }

    @Override
    public void attachActivity(AppCompatActivity activity) {

    }

    @Override
    public void dettachActivity() {

    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public boolean isMuteAdapter() {
        return false;
    }

    @Override
    public int[] getMuteTypes() {
        return new int[0];
    }

    @Override
    public int[] getMuteLayouts() {
        return new int[0];
    }

    /**
     * 取消订单(超市/美食)
     */
    private void cancelOrder(int position, String orderId) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().CANCEL)
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
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
                                if (manager.getAdapter() != null) {
                                    List<ShopOrderResponse.DataBean> dataBeans = manager.getAdapter().getData();
                                    manager.getAdapter().notifyItemRemoved(position);
                                    //取消订单
                                    dataBeans.remove(position);
                                }
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
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
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
    private void sureOrder(int position, String orderId) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SURE)
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
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
                                if (manager.getAdapter() != null) {
                                    List<ShopOrderResponse.DataBean> dataBeans = manager.getAdapter().getData();
                                    manager.getAdapter().notifyItemRemoved(position);
                                    //删除订单
                                    dataBeans.remove(position);
                                }
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
    private void deleteOrder(int position, String orderId) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().DELETE)
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
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
                                if (manager.getAdapter() != null) {
                                    List<ShopOrderResponse.DataBean> dataBeans = manager.getAdapter().getData();
                                    manager.getAdapter().notifyItemRemoved(position);
                                    //删除订单
                                    dataBeans.remove(position);
                                }
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

    IWXAPI iwxapi;

    private void initWx() {
        iwxapi = WxPayUtils.getInstance().reg(getActivity());
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
//                KeyBoardUtils.hideInputMethod(getActivity());
                ShopCart.clearByType(0);//清空超市订单
                request(1);
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
        dialog = new BottomPayDialog(getActivity(), R.style.BottomDialogStyle);
        dialog.setMoneyStr(money);
        dialog.setOnDialogListener(new BottomPayDialog.OnDialogListener() {
            @Override
            public void onConfirm(int type) {
                switch (type) {
                    case 0:
                        //余额
                        if (SharedPreferencesUtils.isHasPwd(getActivity())) {
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
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
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
                                AlipayUtils.startAlipay(getActivity(), handler, 0x10, data);//支付宝支付
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

//                                KeyBoardUtils.hideInputMethod(getActivity());
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                request(1);
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
        pwdDialog = new BottomPwdDialog(getActivity(), R.style.BottomPwdStyle);
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
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
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
                                pwdDialog.closeKeyBord();
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
                        Toast.makeText(getActivity(), "支付宝支付成功！", Toast.LENGTH_SHORT).show();
                        ShopCart.clearByType(0);//清空超市订单
//                        KeyBoardUtils.hideInputMethod(getActivity());
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        request(1);
                    } else if ("4000".equals(map.get("resultStatus"))) {//订单支付失败
                        Toast.makeText(getActivity(), "支付宝支付失败！", Toast.LENGTH_SHORT).show();
                    } else if ("6001".equals(map.get("resultStatus"))) {//订单支付中途取消
                        Toast.makeText(getActivity(), "支付宝支付取消！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    @Subscriber(tag = "refreshShopOrderList")
    public void refreshShopOrderList(String result) {
        request(1);
    }
}

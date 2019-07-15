package com.ysxsoft.home.view.fragment.order.manager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.ShopManagerOrderResponse;
import com.ysxsoft.home.response.ShopOrderResponse;
import com.ysxsoft.home.view.activity.ShopManagerSubmitMessageActivity;
import com.ysxsoft.home.view.activity.ShopOrderDetailActivity;
import com.ysxsoft.home.view.activity.ShopOrderbackActivity;
import com.ysxsoft.home.view.activity.ShopOrderbackDetailActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/6/1 0001
 **/
public class ShopManagerOrderFragment extends BaseFragment implements IListAdapter<ShopManagerOrderResponse.DataBean> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    ListManager manager;
    @Autowired
    int tabPage;//0全部订单 1 待发货 2待收货  3退款单 4已完成

    public static ShopManagerOrderFragment newInstance(int tabPage) {
        Bundle args = new Bundle();
        args.putInt("tabPage", tabPage);
        ShopManagerOrderFragment fragment = new ShopManagerOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop_manager_order;
    }

    @Override
    protected void doWork(View view) {
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        initList(view);
    }

    private void initList(View view) {
        manager = new ListManager(this);
        manager.init(view);
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopManagerOrderResponse.DataBean s= (ShopManagerOrderResponse.DataBean) adapter.getItem(position);
                switch (s.getType()) {
                    case "1":
                        //待付款
                        ShopOrderDetailActivity.start(s.getOrderid(), "", true);
                        break;
                    case "2":
                        //待发货
                        ShopOrderDetailActivity.start(s.getOrderid(), "", true);
                        break;
                    case "3":
                        //待收货
                        ShopOrderDetailActivity.start(s.getOrderid(), "", true);
                        break;
                    case "4":
                        //已完成
                        ShopOrderDetailActivity.start(s.getOrderid(), "", true);
                        break;
                    case "6":
                        //退款中
                        ShopOrderbackDetailActivity.start(s.getOrderid(),true);
                        break;
                    case "7":
                        //退款成功
                        ShopOrderbackDetailActivity.start(s.getOrderid(),true);
                        break;
                    case "8":
                        //退款拒绝
                        ShopOrderbackDetailActivity.start(s.getOrderid(),true);
                        break;
                }
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
        int paramsStatus=0;
        //页码转换为接口参数  0全部订单 1 待发货 2待收货  3退款单 4已完成
        switch (tabPage){
            case 0:
                paramsStatus=0;
                break;
            case 1:
                paramsStatus=1;
                break;
            case 2:
                paramsStatus=2;
                break;
            case 3:
                paramsStatus=4;//退款单
                break;
            case 4:
                paramsStatus=3;//已完成
                break;
        }
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SHOP_MANAGER_ORDER)
//                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
                .addParams("uid",SharedPreferencesUtils.getUid(getActivity()))
                .addParams("page",String.valueOf(page))
                .addParams("status",paramsStatus+"")//0全部，1待发货，2待收货，3已完成，4退货订单
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        manager.releaseRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        manager.releaseRefresh();
                        ShopManagerOrderResponse resp = JsonUtils.parseByGson(response, ShopManagerOrderResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<ShopManagerOrderResponse.DataBean> data = resp.getData();
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
    public void fillView(BaseViewHolder helper, ShopManagerOrderResponse.DataBean s) {
        TextView tips=helper.getView(R.id.tips);

        helper.setText(R.id.orderProductTotalNum,"共"+s.getNum()+"件商品 实付");
        helper.setText(R.id.orderProductTotalPrice,"￥"+s.getAccount());
        //订单号
        helper.setText(R.id.orderNo, "订单号："+s.getDsn());
        //状态
//        type==2待发货，确认发货；type==3，待收货；type==4，已完成；type==6退款中；type==7退款成功；type==8,退款已拒绝 （type==2，仅退款；type大于2，退货退款）
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
                //退款中
                str = "退款单";
                break;
            case "7":
                //退款成功
                str = "退款单";
                break;
            case "8":
                //退款拒绝
                str = "退款单";
                break;
        }
        helper.setText(R.id.orderStatus, str);

        //填充商品
        RecyclerView productRecyclerView = helper.getView(R.id.productRecyclerView);
        productRecyclerView.setNestedScrollingEnabled(false);

        if (!s.getProduct().isEmpty()) {
            productRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            productRecyclerView.setAdapter(new RBaseAdapter<ShopManagerOrderResponse.DataBean.ProductBean>(getActivity(), R.layout.item_shop_order_child_product, s.getProduct()) {

                @Override
                protected void fillItem(RViewHolder holder, ShopManagerOrderResponse.DataBean.ProductBean item, int position) {
                    ImageView pic = holder.getView(R.id.pic);
                    Glide.with(getActivity()).load(item.getPic()).into(pic);
                    holder.setText(R.id.name,item.getGname());//商品名字
                    holder.setText(R.id.price,item.getPrice());//商品价格
                    holder.setText(R.id.num,"x"+item.getNum());//商品数量
                    holder.setText(R.id.ruleName,item.getRule_name());
                }

                @Override
                protected int getViewType(ShopManagerOrderResponse.DataBean.ProductBean item, int position) {
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

        //左按钮
        Button left = helper.getView(R.id.left);
        boolean showLeft=false;
        switch (s.getType()) {
            case "1":
                //待付款
                str = "";
                break;
            case "2":
                //待发货
                str = "";
                break;
            case "3":
                //待收货
                str = "";
                break;
            case "4":
                //已完成
                str = "";
                break;
            case "6":
                //退款中
                str = "拒绝退款";
                showLeft=true;
                break;
            case "7":
                //退款成功
                str = "拒绝退款";
                break;
            case "8":
                //退款拒绝
                str = "拒绝退款";
                break;
        }
        left.setText(str);
        if(showLeft){
            left.setVisibility(View.VISIBLE);
        }else{
            left.setVisibility(View.GONE);
        }
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (s.getType()) {
                    case "1":
                        //待付款 (隐藏)
                        break;
                    case "2":
                        //待发货 (隐藏)
                        break;
                    case "3":
                        //待收货 (隐藏)
                        break;
                    case "4":
                        //已完成 (隐藏)
                        break;
                    case "6":
                        //退款中
                        ShopManagerSubmitMessageActivity.start(true,s.getOrderid());
                        break;
                    case "7":
                        //退款成功 (隐藏)
                        break;
                    case "8":
                        //退款拒绝 (隐藏)
                        break;
                }
            }
        });
        //右按钮
        Button right = helper.getView(R.id.right);
        boolean showTips=false;
        String tipsStr="";
        String rightStr="";
        boolean showRight=false;
        switch (s.getType()) {
            case "1":
                //待付款
                str = "";
                break;
            case "2":
                //待发货
                str = "确认发货";
                showRight=true;
                break;
            case "3":
                //待收货
                str = "";
                showTips=true;//等待买家收货
                tipsStr="等待买家收货";
                break;
            case "4":
                //已完成
                str = "";
                showTips=true;//订单已完成
                tipsStr="订单已完成";
                break;
            case "6":
                //退款中
                str = "确认退款";
                showRight=true;
                break;
            case "7":
                //退款成功
                tipsStr="退款成功";
                showTips=true;
                break;
            case "8":
                //退款拒绝
                tipsStr="已拒绝退款";
                showTips=true;
                break;
        }
        right.setText(str);
        if(showRight){
            right.setVisibility(View.VISIBLE);
        }else{
            right.setVisibility(View.GONE);
        }
        //描述
        tips.setText(tipsStr);
        if(showTips){
            tips.setVisibility(View.VISIBLE);
        }else{
            tips.setVisibility(View.GONE);
        }
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (s.getType()) {
                    case "1":
                        //待付款 (隐藏)
                        break;
                    case "2":
                        //待发货
                        //确认发货
                        fa(s.getOrderid());
                        break;
                    case "3":
                        //待收货 (隐藏)
                        break;
                    case "4":
                        //已完成 (隐藏)
                        break;
                    case "6":
                        //退款中
                        //确认退款
                        sure(s.getOrderid());
                        break;
                    case "7":
                        //退款成功 (隐藏)
                        break;
                    case "8":
                        //退款拒绝 (隐藏)
                        break;
                }
            }
        });
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, ShopManagerOrderResponse.DataBean s) {
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

    private void fa(String orderId){
        showLoadingDialog("提交中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ORDER_FAHUO)
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
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
                                request(1);
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
     * 确认退货
     */
    private void sure(String orderId) {
        showLoadingDialog("正在提交");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ORDER_SURE_REFUSE)
                .addParams("orderid", orderId)
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
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
//                                request(1);
                                EventBus.getDefault().post("", "refreshShopManagerOrderList");//刷新超市管理订单列表
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("提交失败");
                        }
                    }
                });
    }

    @Subscriber(tag = "refreshShopManagerOrderList")
    public void refreshShopManagerOrderList(String result) {
        request(1);
    }
}


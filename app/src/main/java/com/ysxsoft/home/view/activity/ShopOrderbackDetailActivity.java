package com.ysxsoft.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.ShopOrderDetailResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/ShopOrderbackDetailActivity")
public class ShopOrderbackDetailActivity extends BaseActivity {
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
    @BindView(R.id.shopLayout)
    LinearLayout shopLayout;
    @BindView(R.id.left)
    Button left;
    @BindView(R.id.right)
    Button right;
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.orderBianHao)
    TextView orderBianHao;
    @BindView(R.id.orderTime)
    TextView orderTime;
    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;
    @BindView(R.id.totalProduct)
    TextView totalProduct;
    @BindView(R.id.totalMoney)
    TextView totalMoney;
    @BindView(R.id.tuikuanfangshi)
    TextView tuikuanfangshi;
    @BindView(R.id.tuikuanyuanyin)
    TextView tuikuanyuanyin;
    @BindView(R.id.tuikuanshuoming)
    TextView tuikuanshuoming;
    @BindView(R.id.kuanxiang)
    TextView kuanxiang;
    @BindView(R.id.tuikuanjine)
    TextView tuikuanjine;
    @BindView(R.id.tuihuoshuliang)
    TextView tuihuoshuliang;
    @BindView(R.id.tuihuotime)
    TextView tuihuotime;
    @BindView(R.id.tuihuoNo)
    TextView tuihuoNo;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.messageLayout)
    LinearLayout messageLayout;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.backHuoLayout)
    FrameLayout backHuoLayout;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.imageRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.id)
    TextView uid;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.layout4)
    LinearLayout layout4;
    @BindView(R.id.peijine)
    TextView peijine;

    @Autowired
    String orderId;
    @Autowired
    boolean isShop;

    public static void start(String orderId, boolean isShop) {
        ARouter.getInstance().build(ARouterPath.getShopOrderbackDetailActivity()).withString("orderId", orderId).withBoolean("isShop", isShop).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
//        initList();
//        initProductList();
        getDetail();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_orderback_detail;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("退款详情");
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

    private void initList(List<String> pic) {
        recyclerView.setNestedScrollingEnabled(false);
        if (!pic.isEmpty()) {
            int spanCount = pic.size() == 4 ? 2 : 3;//4张图片时候2:2  其他3张
            GridLayoutManager manager = new GridLayoutManager(ShopOrderbackDetailActivity.this, spanCount);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new RBaseAdapter<String>(ShopOrderbackDetailActivity.this, R.layout.item_image, pic) {

                @Override
                protected void fillItem(RViewHolder holder, String item, int position) {
                    ImageView image = holder.getView(R.id.image);
                    Glide.with(ShopOrderbackDetailActivity.this).load(item).into(image);
                }

                @Override
                protected int getViewType(String item, int position) {
                    return 0;
                }
            });
        }
    }

    private void initProductList(List<ShopOrderDetailResponse.DataBean.ProductBean> product) {
        //填充商品
        productRecyclerView.setNestedScrollingEnabled(false);

        if (!product.isEmpty()) {
            productRecyclerView.setLayoutManager(new LinearLayoutManager(ShopOrderbackDetailActivity.this));
            productRecyclerView.setAdapter(new RBaseAdapter<ShopOrderDetailResponse.DataBean.ProductBean>(ShopOrderbackDetailActivity.this, R.layout.item_shop_order_child_product, product) {

                @Override
                protected void fillItem(RViewHolder holder, ShopOrderDetailResponse.DataBean.ProductBean item, int position) {
                    ImageView pic = holder.getView(R.id.pic);
                    Glide.with(ShopOrderbackDetailActivity.this).load(item.getPic()).into(pic);
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
     * 我已退货
     */
    private void tuiOrder(String orderId) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SUBMIT_TUI_HUO)
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
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("退货失败");
                        }
                    }
                });
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
                                ShopOrderDetailResponse.DataBean s = resp.getData();
                                //初始化状态
                                boolean needShowTui = false;
                                boolean needShowJuJue = false;
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
                                    case "5":
                                        //待退货
                                        str = "待退货";
                                        needShowTui = true;
                                        break;
                                    case "6":
                                        //申请退款中 待退货
                                        str = "待退款"; //待退货
                                        needShowTui = true;
                                        break;
                                    case "7":
                                        //已退款
                                        str = "已退款";
                                        break;
                                    case "8":
                                        //退款申请被拒绝
                                        if (isShop) {
                                            str = "已拒绝退款申请";
                                        } else {
                                            str = "退款申请被拒绝";
                                        }
                                        needShowJuJue = true;
                                        break;
                                    case "9":
                                        //退款申请被拒绝
                                        str = "待退款";
                                        break;
                                }
                                String t = "";
                                switch (s.getStyle()) {
//                                    0没申请退货，1退货退款，2仅退款
                                    case "0":
                                        t = "";
                                        needShowTui = false;
                                        break;
                                    case "1":
                                        t = "退货退款";
                                        tuikuanfangshi.setText("退货退款");
                                        break;
                                    case "2":
                                        str = str.replaceAll("待退货","");
                                        t = "仅退款";
                                        tuikuanfangshi.setText("仅退款");
                                        break;
                                }
                                status.setText(t + " " + str);

                                if (isShop) {
                                    //管理端查看退款详情
                                    layout1.setVisibility(View.VISIBLE);
                                    layout2.setVisibility(View.VISIBLE);
                                    layout3.setVisibility(View.VISIBLE);
                                    layout4.setVisibility(View.VISIBLE);
                                    if (needShowTui) {
                                        //拒绝退款  确认退款
                                        shopLayout.setVisibility(View.VISIBLE);
                                        submit.setVisibility(View.GONE);
                                        left.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //拒绝退款
                                                ShopManagerSubmitMessageActivity.start(ShopOrderbackDetailActivity.this,0x02,isShop, orderId);
                                            }
                                        });

                                        right.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                sure();
                                            }
                                        });
                                    }else{
                                        shopLayout.setVisibility(View.GONE);
                                    }

                                } else {
                                    //用户端查看退款详情
                                    if (needShowTui) {
                                        //用户显示我已退货
                                        submit.setVisibility(View.VISIBLE);
                                        submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //点击我已退货
                                                tuiOrder(orderId);
                                            }
                                        });
                                    } else {
                                        submit.setVisibility(View.GONE);
                                    }
                                }
                                //显示拒绝原因
                                if (needShowJuJue) {
                                    messageLayout.setVisibility(View.VISIBLE);
                                    message.setText(s.getTuidetail().getRefuse());//退款原因
                                } else {
                                    messageLayout.setVisibility(View.GONE);
                                }
                                //初始化商品
                                initProductList(s.getProduct());
                                totalProduct.setText("共" + s.getNum() + "件商品 总计：");
                                totalMoney.setText("￥" + s.getAccount());
                                orderBianHao.setText(s.getDsn());
                                orderTime.setText(s.getPaytime());
                                //退款原因
                                tuikuanyuanyin.setText(s.getTuidetail().getReason());
                                //退款说明
                                tuikuanshuoming.setText(s.getTuidetail().getContent());
                                //上传凭证
                                initList(s.getTuidetail().getPic());
                                //扣除款项
                                kuanxiang.setText(s.getTuides());
                                //配送金额
                                peijine.setText("￥" + s.getYun());
                                //退款金额
                                tuikuanjine.setText("￥" + s.getTui_money());
                                //退货数量
                                tuihuoshuliang.setText(s.getNum());
                                //申请时间
                                tuihuotime.setText(s.getTuidetail().getAddtime());
                                //退款编号
                                tuihuoNo.setText(s.getTuidetail().getDsn());

                                //买家id
                                uid.setText(s.getUid());
                                //收货人姓名
                                name.setText(s.getUsername());
                                //收货人手机号
                                phone.setText(s.getMobile());
                                //收货人地址
                                address.setText(s.getAddress());
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

    /**
     * 确认退货
     */
    private void sure() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 0x02:
                    //拒绝退款直接关闭页面
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

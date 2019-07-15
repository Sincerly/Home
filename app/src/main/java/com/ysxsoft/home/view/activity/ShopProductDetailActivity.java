package com.ysxsoft.home.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.orm.ShopCart;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.ShopProductDetailResponse;
import com.ysxsoft.home.view.dialog.SelectFoodSpecialDialog;
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
@Route(path = "/main/ShopProductDetailActivity")
public class ShopProductDetailActivity extends BaseActivity {

    @Autowired
    String productId;
    @Autowired
    boolean isManager;
    @Autowired
    boolean isDown;//是否下架 下架为true  上架为false
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
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.rightWithIcon)
    TextView rightWithIcon;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.store)
    TextView store;
    @BindView(R.id.sales)
    TextView sales;
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.label)
    TextView label;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.shopSpecNum)
    TextView shopSpecNum;
    @BindView(R.id.layout1)
    FrameLayout layout1;
    @BindView(R.id.reduce)
    ImageButton reduce;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.add)
    ImageButton add;
    @BindView(R.id.layout2)
    LinearLayout layout2; @BindView(R.id.pricelayout)
    LinearLayout pricelayout;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.totalMoney)
    TextView totalMoney;
    @BindView(R.id.statusView2)
    LinearLayout statusView2;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @BindView(R.id.bottom)
    ConstraintLayout bottom;
    @BindView(R.id.submit2)
    Button submit2;
    @BindView(R.id.bottomLayoutParent)
    ConstraintLayout bottomLayoutParent;

    public static void start(String productId, boolean isManager, Activity activity, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getShopProductDetailActivity()).withString("productId", productId).withBoolean("isManager", isManager).navigation(activity, requestCode);
    }

    public static void start(String productId, boolean isManager, boolean isDown, Activity activity, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getShopProductDetailActivity()).withString("productId", productId).withBoolean("isManager", isManager).withBoolean("isDown", isDown).navigation(activity, requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        getDetail();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_product_detail;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("详情");
    }

    @OnClick({R.id.backLayout, R.id.pic, R.id.submit, R.id.submit2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.pic:
                //浏览大图
                break;
            case R.id.submit:
                //选好了
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.submit2:
                //上下架
                if (IntentUtils.detectionForDoubleClick()) {
                    down(productId);
                }
                break;
        }
    }

    List<ShopProductDetailResponse.DataBean.RuleBean> ruleBeans = new ArrayList<>();

    private void fillView(ShopProductDetailResponse.DataBean dataBean) {
        ShopProductDetailResponse.DataBean.ProductBean productBean = dataBean.getProduct();
        name.setText(productBean.getName());//商品名称
        time.setText(productBean.getCateid());//商品分类
        store.setText("库存：" + productBean.getStore());//库存
        sales.setText("销量：" + productBean.getSold());//销量
        Glide.with(this).load(productBean.getPic()).into(pic);
        label.setText(productBean.getContent());//商品详情
        price.setText("" + productBean.getPrice());//商品单价
        ruleBeans.clear();
        List<ShopProductDetailResponse.DataBean.RuleBean> rules = dataBean.getRule();
        ruleBeans.addAll(rules);
        boolean hasRule = false;
        if ("1".equals(dataBean.getIsrule())) {//1有规格，0无规格
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
        } else {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        }

        if (isManager) {
            //来自超市管理端
            bottom.setVisibility(View.GONE);
            submit2.setVisibility(View.VISIBLE);
            if (isDown) {
                submit2.setText("上架");
            } else {
                submit2.setText("下架");
            }
            pricelayout.setVisibility(View.GONE);//管理端隐藏价格
            store.setVisibility(View.VISIBLE);
        } else {
            //来自用户端
            bottom.setVisibility(View.VISIBLE);
            submit2.setVisibility(View.GONE);
            pricelayout.setVisibility(View.VISIBLE);//用户显示价格
            store.setVisibility(View.GONE);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(SharedPreferencesUtils.getUid(ShopProductDetailActivity.this))){
                    showToast("请登录！");
                    toLogin();
                    return;
                }

                ShopCart bean = new ShopCart();
                bean.setProductId(productId);//商品id
                bean.setNum(1);//当前购物车数量
                bean.setPrice(productBean.getPrice());
                bean.setCate1(productBean.getC_cateid());//1级分类
                bean.setCate2(productBean.getCid());//2级分类
                bean.setRuleId("0");//无规格的ruleid
                bean.setIsRuleProduct(1);//0是多规格(选规格) 1单规格(加减数量)
                bean.setProductName(productBean.getName());//商品名称
                bean.setCartType(0);//0超市  1美食
                ShopCart.addToCart(bean, 0);
                refresh();
            }
        });
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(SharedPreferencesUtils.getUid(ShopProductDetailActivity.this))){
                    showToast("请登录！");
                    toLogin();
                    return;
                }

                //减少购物车
                ShopCart.reduceCartNum(productId, 0);
                refresh();
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpec(productBean.getC_cateid(), productBean.getCateid(), 0, productId, productBean.getName(), dataBean.getRule());
            }
        });
        refresh();
    }

    /**
     * 显示规格
     *
     * @param data
     */
    private void showSpec(String cate1, String cate2, int position, String productId, String productName, List<ShopProductDetailResponse.DataBean.RuleBean> data) {
        List<SelectFoodSpecialDialog.Data> d = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ShopProductDetailResponse.DataBean.RuleBean bean = data.get(i);
            SelectFoodSpecialDialog.Data item = new SelectFoodSpecialDialog.Data();
            item.setId(bean.getRuleid() + "");//规格id
            item.setName(bean.getRule_name());
            item.setPrice(bean.getPrice());
            item.setSelected(i == 0);
            d.add(item);
        }
        SelectFoodSpecialDialog specialDialog = new SelectFoodSpecialDialog(ShopProductDetailActivity.this, R.style.CenterDialogStyle);
        specialDialog.setData(d);
        specialDialog.setName("商品");
        specialDialog.setProductId(productId);//设置商品id
        specialDialog.setListPosition(position);//设置在列表中的位置
        specialDialog.setCate(cate1, cate2);//设置分类一级二级id
        specialDialog.setProductName(productName);//设置商品名称
        specialDialog.setListener(new SelectFoodSpecialDialog.OnDialogClickListener() {
            @Override
            public void OnSureClick(List<SelectFoodSpecialDialog.Data> selectData) {
            }
        });
        specialDialog.setValueChangeListener(new SelectFoodSpecialDialog.OnValueChangeListener() {
            @Override
            public void OnChanged(String productId, int position, String cate1, String cate2) {
                if("".equals(SharedPreferencesUtils.getUid(ShopProductDetailActivity.this))){
                    showToast("请登录！");
                    toLogin();
                    return;
                }

                refresh();
            }
        });
        specialDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //消失监听
            }
        });
        specialDialog.showDialog();
    }

    private void refresh() {
        shopSpecNum.setText("" + ShopCart.getCartNumByProductId(productId, 0));//刷新角标
        num.setText("" + ShopCart.getCartNumByProductId(productId, 0));//刷新角标
        total.setText("共" + ShopCart.getCartNumByProductId(productId, 0) + "件商品，总计：");//商品详情
        totalMoney.setText(ShopCart.getCartMoney(productId, 0) + "");//总价格
    }

    private void getDetail() {
        showLoadingDialog("正在注册");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SHOP_PRODUCT_DETAIL)
                .addParams("productid", productId)
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
                        ShopProductDetailResponse resp = JsonUtils.parseByGson(response, ShopProductDetailResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                fillView(resp.getData());
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("注册失败");
                        }
                    }
                });
    }

    private void down(String productId) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MANAGER_EDIT)
                .addParams("type", "1")//1超市，2美食
                .addParams("productid", productId)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ActionResponse resp = JsonUtils.parseByGson(response, ActionResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //商品列表
                                if (isDown) {
                                    isDown=false;
                                    submit2.setText("下架");
                                } else {
                                    isDown=true;
                                    submit2.setText("上架");
                                }
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("操作失败！");
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

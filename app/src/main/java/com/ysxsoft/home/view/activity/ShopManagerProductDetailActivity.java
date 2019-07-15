package com.ysxsoft.home.view.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.config.BaseConfig;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.response.ProductDetailResponse;
import com.ysxsoft.home.view.dialog.BottomRuleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/ShopManagerProductDetailActivity")
public class ShopManagerProductDetailActivity extends BaseActivity {


    @BindView(R.id.banner)
    BGABanner banner;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    @BindView(R.id.imageLayout)
    ConstraintLayout imageLayout;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.sales)
    TextView sales;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.selectRule)
    TextView selectRule;
    @BindView(R.id.evaluateAll)
    TextView evaluateAll;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.viewPager)
    NestedScrollView viewPager;
    @BindView(R.id.backLayout)
    FrameLayout backLayout;
    @BindView(R.id.submit)
    Button submit;
    @Autowired
    String id;

    public static void start(String id) {
        ARouter.getInstance().build(ARouterPath.getShopManagerProductDetailActivity()).withString("id", id).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initBanner(banner, new ArrayList<>());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_manager_product_detail;
    }

    @OnClick({R.id.all, R.id.evaluateAll, R.id.backLayout, R.id.submit, R.id.selectRule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all:
                //查看全部规格
                break;
            case R.id.evaluateAll:
                //查看全部评论
                break;
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.submit:
                //立即兑换
                //showRuleDialog();
                break;
            case R.id.selectRule:
                //showRuleDialog();
                break;
        }
    }

    private void initBanner(BGABanner banner, List<String> list) {
        if (BaseConfig.DEBUG) {
            //debug模式
            List<String> bannerBeans = new ArrayList<>();
            bannerBeans.add("http://192.168.1.185:8080/icon_menu_1.png");
            bannerBeans.add("http://192.168.1.185:8080/icon_menu_2.png");
            bannerBeans.add("http://192.168.1.185:8080/icon_menu_3.png");
            banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, String item, int position) {
                    Glide.with(ShopManagerProductDetailActivity.this)
                            .load(R.mipmap.temp_min_banner).apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(itemView);
                }
            });
            banner.setData(bannerBeans, new ArrayList<String>());
        } else {
            banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, String item, int position) {
                    Glide.with(ShopManagerProductDetailActivity.this)
                            .load(item).apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(itemView);
                }
            });
            banner.setData(list, new ArrayList<>());
        }
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                selectIndicator(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        addIndicator();
    }

    private String productPic = "";
    private String productPrice = "100";
    private String productRule = "请选择 颜色 尺码";
    private String productKuCun = "库存123件";
    private String productId = "1";
    private String productGroupId = "";
    private List<ProductDetailResponse.DataBean.RuleBean> ruleBeans;
    private String selectedRule = "";

    /**
     * 选择规格
     */
    private void showRuleDialog() {
        ruleBeans = new ArrayList<>();
        ProductDetailResponse.DataBean.RuleBean rule = new ProductDetailResponse.DataBean.RuleBean();
        rule.setRuleid("1");
        rule.setTitle("颜色");
        List<ProductDetailResponse.DataBean.RuleBean.SubBean> childs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductDetailResponse.DataBean.RuleBean.SubBean c = new ProductDetailResponse.DataBean.RuleBean.SubBean();
            c.setRuleid("" + i);
            c.setSelected(i == 0);
            if (i == 0) {
                c.setTitle("白色" + i);
            } else if (i == 1) {
                c.setTitle("卡其色" + i);
            } else if (i == 2) {
                c.setTitle("土色" + i);
            } else if (i == 3) {
                c.setTitle("红色" + i);
            } else if (i == 4) {
                c.setTitle("黄色" + i);
            } else if (i == 5) {
                c.setTitle("橙橙橙橙色" + i);
            } else {
                c.setTitle("颜色" + i);
            }
            childs.add(c);
        }
        rule.setSub(childs);
        ruleBeans.add(rule);

        ProductDetailResponse.DataBean.RuleBean rule2 = new ProductDetailResponse.DataBean.RuleBean();
        rule2.setRuleid("2");
        rule2.setTitle("尺寸");
        List<ProductDetailResponse.DataBean.RuleBean.SubBean> childs2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductDetailResponse.DataBean.RuleBean.SubBean c = new ProductDetailResponse.DataBean.RuleBean.SubBean();
            c.setRuleid("" + i);
            c.setSelected(i == 0);
            if (i == 0) {
                c.setTitle("白色尺寸" + i);
            } else if (i == 1) {
                c.setTitle("卡其色尺寸" + i);
            } else if (i == 2) {
                c.setTitle("土色尺寸" + i);
            } else if (i == 3) {
                c.setTitle("尺寸" + i);
            } else if (i == 4) {
                c.setTitle("黄色尺寸" + i);
            } else if (i == 5) {
                c.setTitle("橙橙橙橙色尺寸" + i);
            } else {
                c.setTitle("尺寸" + i);
            }
            childs2.add(c);
        }
        rule2.setSub(childs2);
        ruleBeans.add(rule2);

        if (ruleBeans == null) {
            showToast("规格未初始化！");
            return;
        }
        //立即购买
        BottomRuleDialog dialog = new BottomRuleDialog(this, R.style.BottomDialogStyle);
        dialog.setData(ruleBeans);
        dialog.setTopData(productPic, productPrice, productRule, productKuCun, productId, productGroupId);
        dialog.setListener(new BottomRuleDialog.OnDialogClickListener() {
            @Override
            public void OnClick() {
                //立即购买
            }

            @Override
            public void OnSelected(String ruleIds, String ruleName) {
                selectedRule = ruleIds;
            }

            @Override
            public void OnCreateOrderSuccess(String orderId, int n) {
                //下单成功 跳转至结算页面
                //ARouter.getInstance().build(ArouterPaths.getInstance().getSettlementActivity()).withString("orderId", orderId).withInt("n", n).navigation();
                GoldProductConfirmOrderActivity.start("",1);
            }
        });
        dialog.showDialog();
    }

    /**
     * 添加指示器
     */
    private void addIndicator() {
        indicator.removeAllViews();
        for (int i = 0; i < 3; i++) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(DisplayUtils.dp2px(this,10),DisplayUtils.dp2px(this,10));
            layoutParams.leftMargin=DisplayUtils.dp2px(this,10);
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getResources().getDrawable(R.drawable.bg_gold_banner_selector));
            indicator.addView(textView);
        }
        selectIndicator(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(banner!=null){
            banner.stopAutoPlay();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(banner!=null){
            banner.startAutoPlay();
        }
    }

    private void selectIndicator(int p){
        if(indicator!=null){
            for (int i = 0; i <3; i++) {
                TextView t= (TextView) indicator.getChildAt(i);
                t.setSelected(i==p);
            }
        }

    }
}

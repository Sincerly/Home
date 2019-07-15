package com.ysxsoft.home.view.activity;

import android.os.Bundle;
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
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.common_base.utils.WebViewUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.GoldProductDetailResponse;
import com.ysxsoft.home.response.ProductDetailResponse;
import com.ysxsoft.home.view.dialog.BottomRuleDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/GoldProductDetailActivity")
public class GoldProductDetailActivity extends BaseActivity {


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
    @BindView(R.id.kucun)
    TextView kucun;
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
    String productId;

    public static void start(String productId) {
        ARouter.getInstance().build(ARouterPath.getGoldProductDetailActivity()).withString("productId", productId).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
//        initEvaluate();
        getDetail();
    }

    /**
     * 初始化评论
     */
    private void initEvaluate() {
        List<String> data = new ArrayList<>();
        data.add("11");
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RBaseAdapter<String>(this, R.layout.item_food2_evaluate, data) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                RecyclerView recyclerView = holder.getView(R.id.imageRecyclerView);
                recyclerView.setNestedScrollingEnabled(false);
                List<String> imgUrls = new ArrayList<>();
                int size = position + 1;
                for (int i = 0; i < size; i++) {
                    imgUrls.add("http://125.46.19.110:18080/uploads/201904251801545703920190425/0233055c758774d2996e7204afbc1eeb.png");
                }
                if (imgUrls != null && !imgUrls.isEmpty()) {
                    int spanCount = imgUrls.size() == 4 ? 2 : 3;//4张图片时候2:2  其他3张
                    GridLayoutManager manager = new GridLayoutManager(GoldProductDetailActivity.this, spanCount);
                    manager.setOrientation(GridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(new RBaseAdapter<String>(GoldProductDetailActivity.this, R.layout.item_image, imgUrls) {

                        @Override
                        protected void fillItem(RViewHolder holder, String item, int position) {
                            ImageView image = holder.getView(R.id.image);
                            Glide.with(GoldProductDetailActivity.this).load(item).into(image);
                        }

                        @Override
                        protected int getViewType(String item, int position) {
                            return 0;
                        }
                    });
                }
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gold_product_detail;
    }

    @OnClick({R.id.all, R.id.evaluateAll, R.id.backLayout, R.id.submit, R.id.selectRule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.evaluateAll:
                //已隐藏评论
                break;
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.submit:
                //立即兑换
                showRuleDialog();
                break;
            case R.id.selectRule:
            case R.id.all:
                //查看全部规格
                showRuleDialog();
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
                    Glide.with(GoldProductDetailActivity.this)
                            .load(R.mipmap.temp_min_banner).apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(itemView);
                }
            });
            banner.setData(bannerBeans, new ArrayList<String>());
        } else {
            banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, String item, int position) {
                    Glide.with(GoldProductDetailActivity.this)
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

        addIndicator(list.size());
    }

    private String productPic = "";
    private String productPrice = "100";
    private String productRule = "请选择 颜色 尺码";
    private String productKuCun = "库存123件";
    private String productGroupId = "";
    private List<ProductDetailResponse.DataBean.RuleBean> ruleBeans;
    private String selectedRule = "";

    /**
     * 选择规格
     */
    private void showRuleDialog() {
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
            public void OnCreateOrderSuccess(String groupId, int n) {
                //下单成功 跳转至结算页面
                //ARouter.getInstance().build(ArouterPaths.getInstance().getSettlementActivity()).withString("orderId", orderId).withInt("n", n).navigation();
                GoldProductConfirmOrderActivity.start(groupId,n);
            }
        });
        dialog.showDialog();
    }

    /**
     * 添加指示器
     */
    private void addIndicator(int listSize) {
        indicator.removeAllViews();
        textViews.clear();
        for (int i = 0; i < listSize; i++) {
            View view = View.inflate(this, R.layout.view_gold_product, null);
            TextView textView = view.findViewById(R.id.tv);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtils.dp2px(this, 5), DisplayUtils.dp2px(this, 5));
            params.leftMargin=DisplayUtils.dp2px(this,5);
            view.setLayoutParams(params);
            textViews.add(textView);
            indicator.addView(view);
        }
        selectIndicator(0);
    }

    private List<TextView> textViews = new ArrayList<>();

    @Override
    protected void onPause() {
        super.onPause();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    private void selectIndicator(int p) {
        if (textViews != null) {
            for (int i = 0; i < textViews.size(); i++) {
                TextView t = textViews.get(i);
                t.setSelected(i == p);
            }
        }
    }

    /**
     * 获取详情
     */
    private void getDetail() {
        if (productId == null) {
            ToastUtils.shortToast(this, "未找到该商品");
            return;
        }
        showLoadingDialog("加载中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().GOLD_PRODUCT_DETAIL)
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
                        GoldProductDetailResponse resp = JsonUtils.parseByGson(response, GoldProductDetailResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                GoldProductDetailResponse.DataBean d = resp.getData();
                                GoldProductDetailResponse.DataBean.ProductBean detail = d.getProduct();
                                List<GoldProductDetailResponse.DataBean.RuleBean> ruleBeanList = d.getRule();
                                //基本信息
                                if (detail.getPic() != null) {
                                    initBanner(banner, detail.getPic());
                                }
                                money.setText(detail.getPrice() + "金币");
                                sales.setText("销量:" + detail.getSold());
                                kucun.setText("库存:" + detail.getStore());
                                name.setText(detail.getName());
                                WebViewUtils.setH5Data(webView, detail.getContent());
                                //规格
                                productGroupId=detail.getGroup_id();//TODO：记录默认规格id
                                productPic = detail.getMin_pic();//选规格小图片
                                productPrice = detail.getPrice();//默认价格
                                productKuCun = "库存" + detail.getStore() + "件";
                                selectedRule = detail.getP_ruleid();//默认选择的ruleid
                                if (ruleBeans == null) {
                                    ruleBeans = new ArrayList<>();
                                }
                                ruleBeans.clear();
                                for (int j = 0; j < ruleBeanList.size(); j++) {
                                    //一级
                                    ProductDetailResponse.DataBean.RuleBean rule = new ProductDetailResponse.DataBean.RuleBean();
                                    GoldProductDetailResponse.DataBean.RuleBean ruleBean = ruleBeanList.get(j);
                                    rule.setRuleid(ruleBean.getRuleid());
                                    rule.setTitle(ruleBean.getTitle());
                                    List<ProductDetailResponse.DataBean.RuleBean.SubBean> childs = new ArrayList<>();
                                    if (ruleBean.getSub() == null) {
                                        continue;
                                    }
                                    //二级
                                    List<GoldProductDetailResponse.DataBean.RuleBean.SubBean> childBean = ruleBean.getSub();
                                    for (int i = 0; i < childBean.size(); i++) {
                                        ProductDetailResponse.DataBean.RuleBean.SubBean c = new ProductDetailResponse.DataBean.RuleBean.SubBean();
                                        GoldProductDetailResponse.DataBean.RuleBean.SubBean c2 = childBean.get(i);
                                        c.setRuleid(c2.getRuleid());
                                        c.setTitle(c2.getTitle());
                                        c.setSelected(i == 0);
                                        childs.add(c);
                                    }
                                    rule.setSub(childs);
                                    ruleBeans.add(rule);
                                }
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取商品详情失败");
                        }
                    }
                });
    }
}

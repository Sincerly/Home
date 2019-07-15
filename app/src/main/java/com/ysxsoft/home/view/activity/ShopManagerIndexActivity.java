package com.ysxsoft.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.orm.ShopCart;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.MyShopResponse;
import com.ysxsoft.home.response.ShopCategoryResponse;
import com.ysxsoft.home.response.ShopOrderResponse;
import com.ysxsoft.home.response.ShopProductResponse;
import com.ysxsoft.home.view.dialog.BottomCartDialog;
import com.ysxsoft.home.view.dialog.JinHuoDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/17 0017
 **/
@Route(path = "/main/ShopManagerIndexActivity")
public class ShopManagerIndexActivity extends BaseActivity {
    @Autowired
    String shopId;
    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.rightMenu)
    TextView rightMenu;
    @BindView(R.id.titleLayout)
    LinearLayout titleLayout;
    @BindView(R.id.shopLogo)
    CircleImageView shopLogo;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.shopPhone)
    TextView shopPhone;
    @BindView(R.id.shopAddress)
    TextView shopAddress;
    @BindView(R.id.shopMenu)
    TextView shopMenu;
    @BindView(R.id.topLayout)
    ConstraintLayout topLayout;
    @BindView(R.id.search)
    LinearLayout search;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.menuLayout)
    RecyclerView menuRecyclerView;
    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;

    private int selectPosition = 0;
    private BottomCartDialog dialog;
    private boolean isShowing = false;
    private String cate1 = "";
    private String cate2 = "";
    List<ShopCategoryResponse.DataBean.SubBean> subBeans = new ArrayList<>();
    RBaseAdapter subAdapter;//二级菜单适配器
    RBaseAdapter adapter;//商品适配器
    List<ShopProductResponse.DataBean> productList = new ArrayList<>();

    List<ShopCategoryResponse.DataBean> data = new ArrayList<>();

    public static void start(String shopId) {
        ARouter.getInstance().build(ARouterPath.getShopManagerIndexActivity()).withString("shopId", shopId).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        rightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //商家订单
                ShopManagerOrderActivity.start();
            }
        });
//        getInfo();
//        getShopMenu(shopId);
//        initTabLayout();
//        initMenuLayout();
//        initProductList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInfo();
        getShopMenu(shopId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_manager_index;
    }

    private void initProductList() {
        if (adapter == null) {
            adapter = new RBaseAdapter<ShopProductResponse.DataBean>(ShopManagerIndexActivity.this, R.layout.item_shop_manager_product, productList) {
                @Override
                protected void fillItem(RViewHolder holder, ShopProductResponse.DataBean item, int position) {
                    RoundImageView logo = holder.getView(R.id.logo);
                    TextView name = holder.getView(R.id.name);
                    TextView price = holder.getView(R.id.price);
                    TextView kucun = holder.getView(R.id.kucun);//库存
                    TextView add = holder.getView(R.id.add);
                    TextView down = holder.getView(R.id.down);
                    TextView status = holder.getView(R.id.status);

                    name.setText(item.getName());
                    price.setText(item.getPrice());
                    kucun.setText("库存：" + item.getStore());
                    Glide.with(ShopManagerIndexActivity.this).load(item.getPic()).into(logo);

                    //0仓库中，1上架中，2已售罄
                    if ("0".equals(item.getStatus())) {
                        //仓库中
                        status.setText("仓库中");
                        down.setText("上架");
                    } else {
                        if ("1".equals(item.getStatus())) {
                            //上架中
                            status.setText("上架中");
                            down.setText("下架");
                        } else {
                            //已售
                            status.setText("已售罄");
                            down.setText("下架");
                        }
                    }
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //进货
//                            JinHuoDialog.showDialog(ShopManagerIndexActivity.this, "", new JinHuoDialog.OnDialogClickListener() {
//                                @Override
//                                public void sure() {
//                                }
//                            });
                        }
                    });
                    down.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //上下架
                            if ("0".equals(item.getStatus())) {
                                //目前仓库中  上架操作
                                down(item.getProductid(), position);
                                down.setText("下架");
                            } else if ("1".equals(item.getStatus())) {
                                //目前上架中  下架操作
                                down(item.getProductid(), position);
                                down.setText("上架");
                            } else if ("2".equals(item.getStatus())) {
                                //目前已售完  下架操作
                                down(item.getProductid(), position);
                                down.setText("上架");
                            }
                        }
                    });
//                    if(item.getStore()!=null&&!"".equals(item.getStore())){
//                        int store= Integer.parseInt(item.getStore());
//                        if(store>0){
//                        }else{
//                            down.setText("已售罄");
//                        }
//                    }
                }

                @Override
                protected int getViewType(ShopProductResponse.DataBean item, int position) {
                    return 0;
                }
            };
            adapter.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RViewHolder holder, View view, int position) {
                    ShopProductResponse.DataBean item = productList.get(position);
                    boolean isDown = false;
                    if ("0".equals(item.getStatus())) {
                        //目前仓库中  上架操作
                        isDown = true;
                    } else {
                        isDown = false;
                    }
                    ShopProductDetailActivity.start(item.getProductid(), true, isDown, ShopManagerIndexActivity.this, 0x03);
                }
            });
            productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            productRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.backLayout, R.id.rightMenu, R.id.infoLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.rightMenu:
                //商家订单
                break;
            case R.id.infoLayout:
                //跳转超市信息详情
                ShopManagerInfoActivity.start(shopId);
                break;
        }
    }

    private void initView(String n, String p, String d) {
        shopName.setText(n);//
        shopPhone.setText(p);
        shopAddress.setText(p);
    }

    private void getShopMenu(String shopId) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SHOP_OPTION)
                .addParams("superid", shopId)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ShopCategoryResponse resp = JsonUtils.parseByGson(response, ShopCategoryResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //一级分类
                                List<ShopCategoryResponse.DataBean> d = resp.getData();
                                data.clear();
                                data.addAll(d);
                                initTabLayout();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取店铺分类失败");
                        }
                    }
                });
    }

    private void initTabLayout() {
        tabLayout.removeAllTabs();
        List<String> tabs = new ArrayList<>();
        List<String> titleBeans = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ShopCategoryResponse.DataBean item = data.get(i);
            tabs.add(item.getTitle());
            titleBeans.add(item.getTitle());
        }
        for (int i = 0; i < titleBeans.size(); i++) {
            tabs.add(titleBeans.get(i));
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(R.layout.view_shop_tab);
            TextView textView = tab.getCustomView().findViewById(R.id.tab);
            View redView = tab.getCustomView().findViewById(R.id.redView);
            redView.setVisibility(View.GONE);
            textView.setSelected(i == 0);
            textView.setText(titleBeans.get(i));
            textView.setTextSize(15);
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() == null) {
                    return;
                }
                TextView tv = tab.getCustomView().findViewById(R.id.tab);
                tv.setSelected(true);
                if (data.size() > tab.getPosition()) {
                    //初始化二级菜单
                    subBeans.clear();
                    subBeans.addAll(data.get(tab.getPosition()).getSub());
                    cate1 = data.get(tab.getPosition()).getCid();//一级id
                    initMenuLayout();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() == null) {
                    return;
                }
                TextView tv = tab.getCustomView().findViewById(R.id.tab);
                tv.setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        if (data.size() > 0) {
            //默认选中第一个二级
            subBeans.clear();
            subBeans.addAll(data.get(0).getSub());
            cate1 = data.get(0).getCid();//一级id
            initMenuLayout();
        }
    }

    /**
     * 初始化二级菜单
     */
    private void initMenuLayout() {
        if (subBeans == null) {
            subBeans = new ArrayList<>();
        }
        if (subBeans.size() > 0) {
            for (int i = 0; i < subBeans.size(); i++) {
                ShopCategoryResponse.DataBean.SubBean c2 = subBeans.get(i);
                cate2 = c2.getCid();
                subBeans.get(i).setSelected(i == 0);
                subBeans.get(i).setCarNum(ShopCart.getCartNumByCate2(0, c2.getCid()));
            }
        }
        subAdapter = new RBaseAdapter<ShopCategoryResponse.DataBean.SubBean>(this, R.layout.item_shop_menu, subBeans) {
            @Override
            protected void fillItem(RViewHolder holder, ShopCategoryResponse.DataBean.SubBean item, int position) {
                TextView textView = holder.getView(R.id.content);
                textView.setText(item.getTitle());
                TextView numTextView = holder.getView(R.id.shopCardNum);
                if (item.isSelected()) {
                    textView.setSelected(true);
                } else {
                    textView.setSelected(false);
                }
                if (item.getCarNum() > 0) {
                    //购物车数量选中
                    numTextView.setVisibility(View.GONE);
//                    numTextView.setText(String.valueOf(item.getCarNum()));
                } else {
                    numTextView.setVisibility(View.GONE);
//                    numTextView.setText("0");
                }
            }

            @Override
            protected int getViewType(ShopCategoryResponse.DataBean.SubBean item, int position) {
                return 0;
            }
        };
        subAdapter.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
                //刷新蓝色背景
                for (int i = 0; i < subBeans.size(); i++) {
                    ShopCategoryResponse.DataBean.SubBean d = subBeans.get(i);
                    d.setSelected(i == position);
                }
                subAdapter.notifyDataSetChanged();
                ShopCategoryResponse.DataBean.SubBean d = (ShopCategoryResponse.DataBean.SubBean) subAdapter.getItemData(position);
                cate2 = d.getCid();//初始化二级id
                getProduct(d.getCid());
            }
        });
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerView.setAdapter(subAdapter);
        if (subBeans.size() > 0) {
            //根据二级id
            cate2 = subBeans.get(0).getCid();//初始化二级id
            getProduct(subBeans.get(0).getCid());
        } else {
            //二级分类为空
        }
    }

    /**
     * 获取商品
     *
     * @param cid
     */
    private void getProduct(String cid) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SHOP_PRODUCT_LIST)
                .addParams("superid", shopId)
                .addParams("cid", cid)
                .addParams("type", "1")//商品管理传1
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ShopProductResponse resp = JsonUtils.parseByGson(response, ShopProductResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //商品列表
                                List<ShopProductResponse.DataBean> d = resp.getData();
                                productList.clear();
                                productList.addAll(d);
                                initProductList();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取商品失败");
                        }
                    }
                });
    }

    /**
     * 商品下架
     *
     * @param productId
     * @param position
     */
    private void down(String productId, int position) {
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
                                if (productList.size() > position) {
                                    ShopProductResponse.DataBean d = productList.get(position);
                                    if ("0".equals(d.getStatus())) {
                                        //目前仓库中  上架操作
                                        d.setStatus("1");
                                    } else if ("1".equals(d.getStatus())) {
                                        //目前上架中  下架操作
                                        d.setStatus("0");
                                    }
                                    adapter.notifyDataSetChanged();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x03:
                    //跳转详情
                    getProduct(cate2);
                    break;
            }
        }
    }

    /**
     * 获取我的超市
     */
    private void getInfo() {
        showLoadingDialog("获取订单详情");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_SHOP)
                .addParams("superid", shopId)
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
                        MyShopResponse resp = JsonUtils.parseByGson(response, MyShopResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                MyShopResponse.DataBean d = resp.getData();
                                Glide.with(ShopManagerIndexActivity.this).load(d.getPic()).into(shopLogo);

                                shopName.setText(d.getName());
                                shopPhone.setText(d.getMobile());
                                shopAddress.setText(d.getAddress());
                                shopMenu.setText(d.getDetail());
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
}

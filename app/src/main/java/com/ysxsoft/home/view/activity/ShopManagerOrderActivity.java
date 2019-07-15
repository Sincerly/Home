package com.ysxsoft.home.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.ViewPagerFragmentAdapter;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.view.fragment.order.ShopOrderChildFragment;
import com.ysxsoft.home.view.fragment.order.manager.ShopManagerOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * create by Sincerly on 2019/6/1 0001
 **/
@Route(path = "/main/ShopManagerOrderActivity")
public class ShopManagerOrderActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
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

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getShopManagerOrderActivity()).navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_manager_order;
    }

    @Override
    public void doWork() {
        super.doWork();
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("商家订单");
        initViewPager();
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        tabLayout.removeAllTabs();
        List<String> tabs = new ArrayList<>();
        List<String> titleBeans = new ArrayList<>();
        tabs.add("全部订单");
        tabs.add("待发货");
        tabs.add("待收货");
        tabs.add("退款单");
        tabs.add("已完成");

        titleBeans.add("全部订单");
        titleBeans.add("待发货");
        titleBeans.add("待收货");
        titleBeans.add("退款单");
        titleBeans.add("已完成");

        for (int i = 0; i < tabs.size(); i++) {
            fragmentList.add(ShopManagerOrderFragment.newInstance(i));
        }
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList, tabs));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(fragmentList.size());

        for (int i = 0; i < titleBeans.size(); i++) {
            tabs.add(titleBeans.get(i));
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.view_tab);
            TextView textView = tab.getCustomView().findViewById(R.id.tab);
            textView.setSelected(i == 0);
            textView.setText(titleBeans.get(i));
            textView.setTextSize(15);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() == null) {
                    return;
                }
                TextView tv = tab.getCustomView().findViewById(R.id.tab);
                tv.setSelected(true);
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
    }

    @OnClick(R.id.backLayout)
    public void onViewClicked() {
        backToActivity();
    }
}

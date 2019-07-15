package com.ysxsoft.home.view.fragment.order;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.ViewPagerFragmentAdapter;
import com.ysxsoft.home.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * create by Sincerly on 2019/5/28 0028
 **/
public class FoodOrderFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_food_order;
    }

    @Override
    protected void doWork(View view) {
        initViewPager();
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        tabLayout.removeAllTabs();
        List<String> tabs = new ArrayList<>();
        List<String> titleBeans = new ArrayList<>();
        tabs.add("全部订单");
        tabs.add("待付款");
        tabs.add("待发货");
        tabs.add("待收货");
        tabs.add("待评价");
        tabs.add("退款中");
        tabs.add("已完成");
        titleBeans.add("全部订单");
        titleBeans.add("待付款");
        titleBeans.add("待发货");
        titleBeans.add("待收货");
        titleBeans.add("待评价");
        titleBeans.add("退款中");
        titleBeans.add("已完成");

        for (int i = 0; i < tabs.size(); i++) {
            fragmentList.add(FoodOrderChildFragment.newInstance(i));
        }
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(), fragmentList, tabs));
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
}

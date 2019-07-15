package com.ysxsoft.home.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.ViewPagerFragmentAdapter;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.UserInfoResponse;
import com.ysxsoft.home.view.fragment.balance.BalanceFragment;
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
@Route(path = "/main/BalanceInfoActivity")
public class BalanceInfoActivity extends BaseActivity {


    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.rightWithIcon)
    TextView rightWithIcon;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.money)
    TextView m;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @Autowired
    String money;
    @Autowired
    String dMoney;
    @BindView(R.id.dongMoney)
    TextView dongMoney;
    @BindView(R.id.dongLayout)
    LinearLayout dongLayout;


    public static void start(String money) {
        ARouter.getInstance().build(ARouterPath.getBalanceInfoActivity()).withString("money", money).navigation();
    }

    public static void start(String money, String dMoney) {
        ARouter.getInstance().build(ARouterPath.getBalanceInfoActivity()).withString("money", money).withString("dMoney", dMoney).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        if ("".equals(dMoney) || dMoney == null) {
            dongLayout.setVisibility(View.GONE);
        } else {
            dongLayout.setVisibility(View.VISIBLE);
            dongMoney.setText("冻结金额: ¥" + (dMoney == null ? "" : dMoney));
        }
        if ("".equals(money) || money == null) {
            m.setVisibility(View.GONE);
        } else {
            m.setVisibility(View.VISIBLE);
            m.setText(money == null ? "" : money);
        }
        initTitle();
        initViewPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banlance_info;
    }

    private void getUserInfo() {
        if ("".equals(SharedPreferencesUtils.getUid(BalanceInfoActivity.this))) {
            return;
        }
        OkHttpUtils.post()
                .url(AppConfig.getInstance().USER_INFO)
                .addParams("uid", SharedPreferencesUtils.getUid(BalanceInfoActivity.this))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserInfoResponse resp = JsonUtils.parseByGson(response, UserInfoResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                UserInfoResponse.DataBean data = resp.getData();
                                dongMoney.setText("冻结金额: ¥" + data.getFreeze());
                                m.setText(data.getMoney());
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            //获取个人信息失败
                        }
                    }
                });
    }

    private void initTitle() {
    }

    @OnClick({R.id.backLayout, R.id.rightWithIcon, R.id.button1, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.rightWithIcon:
                //我的卡包
                BankListActivity.start();
                break;
            case R.id.button1:
                //充值
                RechargeActivity.start();
                break;
            case R.id.button2:
                //提现
                BalanceWithdrawalActivity.start();
                break;
        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        tabLayout.removeAllTabs();
        //1.初始化首页
        List<String> tabs = new ArrayList<>();
        //2.初始化顶部分类
        List<String> titleBeans = new ArrayList<>();

        tabs.add("全部");
        titleBeans.add("全部");
        tabs.add("收入");
        titleBeans.add("收入");
        tabs.add("支出");
        titleBeans.add("支出");

        fragmentList.add(BalanceFragment.newInstance(3));//1收入，2支出，3全部
        fragmentList.add(BalanceFragment.newInstance(1));
        fragmentList.add(BalanceFragment.newInstance(2));

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
}

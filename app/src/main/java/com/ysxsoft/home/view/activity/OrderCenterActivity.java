package com.ysxsoft.home.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.ViewPagerFragmentAdapter;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.view.fragment.job.JobFragment1;
import com.ysxsoft.home.view.fragment.job.JobFragment2;
import com.ysxsoft.home.view.fragment.order.FoodOrderFragment;
import com.ysxsoft.home.view.fragment.order.ShopOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/OrderCenterActivity")
public class OrderCenterActivity extends BaseActivity {


    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.leftMenu)
    TextView leftMenu;
    @BindView(R.id.rightMenu)
    TextView rightMenu;
    @BindView(R.id.centerMenuLayout)
    LinearLayout centerMenuLayout;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private boolean isLeftMenuSelected = true;

    @Autowired
    boolean showFooded;

    public static void start(boolean showFooded) {
        ARouter.getInstance().build(ARouterPath.getOrderCenterActivity()).withBoolean("showFooded", showFooded).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        leftMenu.setSelected(true);
        rightMenu.setSelected(false);
        initTitle();
        initViewPager();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_center;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
    }

    @OnClick({R.id.backLayout, R.id.leftMenu, R.id.rightMenu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.leftMenu:
                //商超
                toogleLeft(true);
                break;
            case R.id.rightMenu:
                //美食
                toogleLeft(false);
                break;
        }
    }

    private void initViewPager() {
        List<Fragment> job = new ArrayList<>();
        job.add(new ShopOrderFragment());
        job.add(new FoodOrderFragment());
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), job, new ArrayList<>()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                toogleLeft(i == 0 ? true : false);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        if(showFooded){
            viewPager.setCurrentItem(1);
        }else{
            viewPager.setCurrentItem(0);
        }
    }

    private void toogleLeft(boolean status) {
        leftMenu.setSelected(status ? true : false);
        rightMenu.setSelected(status ? false : true);
        isLeftMenuSelected = status;
        if (status) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }
    }
}

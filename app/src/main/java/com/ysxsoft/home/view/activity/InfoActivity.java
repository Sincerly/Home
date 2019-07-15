package com.ysxsoft.home.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/InfoActivity")
public class InfoActivity extends BaseActivity {
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
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.menu1)
    TextView menu1;
    @BindView(R.id.menu2)
    TextView menu2;
    @BindView(R.id.menu3)
    TextView menu3;
    @BindView(R.id.menu4)
    TextView menu4;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getInfoActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("信息");
    }

    @OnClick({R.id.backLayout, R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.menu1:
                //订单
                OrderCenterActivity.start(false);
                break;
            case R.id.menu2:
                //我的求职
                MyJobActivity.start();
                break;
            case R.id.menu3:
                //人找车
                MyPeopleFindCarActivity.start();
                break;
            case R.id.menu4:
                //我的帖子
                MyArticleActivity.start();
                break;
        }
    }
}

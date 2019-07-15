package com.ysxsoft.home.view.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
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
@Route(path = "/main/WithdrawalSuccessActivityActivity")
public class WithdrawalSuccessActivityActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView t;
    @BindView(R.id.money)
    TextView m;
    @BindView(R.id.submit)
    AppCompatButton submit;

    @Autowired
    String money;
    @Autowired
    String time;

    public static void start(String time,String money) {
        ARouter.getInstance().build(ARouterPath.getWithdrawalSuccessActivityActivity()).withString("money",money).withString("time",time).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        t.setText(time);
        m.setText("￥"+money);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawal_success;
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        finish();
    }
}

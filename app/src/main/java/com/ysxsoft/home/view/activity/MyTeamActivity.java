package com.ysxsoft.home.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.zxing.util.ZxingUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.MyShopResponse;
import com.ysxsoft.home.response.TeamResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/MyTeamActivity")
public class MyTeamActivity extends BaseActivity {
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
    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.seeMing)
    Button seeMing;
    @BindView(R.id.toplayout)
    ConstraintLayout toplayout;
    @BindView(R.id.tiplayout)
    LinearLayout tiplayout;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.user)
    TextView user;
    @BindView(R.id.codeUrl)
    TextView codeUrl;
    @BindView(R.id.codeStr)
    TextView codeStr;
    @BindView(R.id.code)
    ImageView code;
    @BindView(R.id.codelayout)
    FrameLayout codelayout;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getMyTeamActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_team;
    }

    private void initTitle() {
        backLayout.setVisibility(View.VISIBLE);
        bg.setBackgroundColor(Color.parseColor("#00000000"));
        back.setImageResource(R.mipmap.icon_white_back);
        title.setText("我的家人");
        title.setTextColor(Color.WHITE);
        getTeamInfo();
    }

    @OnClick({R.id.backLayout, R.id.seeMing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.seeMing:
                //查看明细
                if (IntentUtils.detectionForDoubleClick()) {
                    TeamMoneyHistoryActivity.start();
                }
                break;
        }
    }

    /**
     * 获取我的团队
     */
    private void getTeamInfo() {
        showLoadingDialog("获取中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_TEAM)
                .addParams("uid", SharedPreferencesUtils.getUid(MyTeamActivity.this))
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
                        TeamResponse resp = JsonUtils.parseByGson(response, TeamResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                TeamResponse.DataBean d = resp.getData();
                                code.setImageBitmap(ZxingUtils.create2DCode(d.getUrl(), 800, 800));
                                codeUrl.setText(resp.getData().getUrl());
                                codeStr.setText("邀请码：" + d.getCode());
                                user.setText("已邀请" + d.getCount() + "人，获得" + d.getAccount() + "金币");
                                codeUrl.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        copy(resp.getData().getUrl());
                                        return false;
                                    }
                                });
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

    /**
     * 复制至剪贴板
     * @param url
     */
    private void copy(String url){
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, url));
        showToast("已复制！");
    }
}

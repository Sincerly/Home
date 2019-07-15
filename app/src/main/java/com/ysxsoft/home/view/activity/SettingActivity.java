package com.ysxsoft.home.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.allenliu.versionchecklib.v.AllenVersionChecker;
import com.allenliu.versionchecklib.v.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v.builder.UIData;
import com.allenliu.versionchecklib.v.callback.ForceUpdateListener;
import com.allenliu.versionchecklib.v.callback.RequestVersionListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.utils.ApplicationUtils;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.view.activity.SettingActivity;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.EditionResponse;
import com.ysxsoft.home.view.dialog.CenterTipsDialog;
import com.ysxsoft.home.view.dialog.EditionDialog;

import java.io.File;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/SettingActivity")
public class SettingActivity extends BaseActivity {
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
    @BindView(R.id.exitButton)
    AppCompatButton exitButton;
    DownloadBuilder builder;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getSettingActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("设置");
    }

    @OnClick({R.id.backLayout, R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4, R.id.exitButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.menu1:
                //意见反馈
                FeedBackActivity.start();
                break;
            case R.id.menu2:
                //支付密码设置
                SetPayPwdActivity.start();
                break;
            case R.id.menu3:
                //登录密码设置
                SetLoginPwdActivity.start();
                break;
            case R.id.menu4:
                //版本更新
                if(IntentUtils.detectionForDoubleClick()){
                    update();
                }
                break;
            case R.id.exitButton:
                CenterTipsDialog tipsDialog=new CenterTipsDialog(this,R.style.CenterDialogStyle);
                tipsDialog.setListener(new CenterTipsDialog.OnDialogClickListener() {
                    @Override
                    public void sure() {
                        toLogin();
                        finish();
                    }
                });
                tipsDialog.initContent("确定退出登录吗？");
                tipsDialog.showDialog();
                break;
        }
    }

    private void update() {
        File file=new File(AppConfig.APK_URL);
        if(file.exists()){
            file.delete();
        }
        builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl(AppConfig.getInstance().EDITION)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        Type type = new TypeToken<EditionResponse>() {
                        }.getType();
                        EditionResponse response = new Gson().fromJson(result, type);
                        if (response != null) {
                            EditionResponse.DataBean dataBean = response.getData();
                            int code = ApplicationUtils.getVersionCode(SettingActivity.this);
                            if (dataBean.getVercode() > code) {
                                return crateUIData(dataBean.getPath(), dataBean.getContent());
                            }else{
                                EditionDialog editionDialog=new EditionDialog(SettingActivity.this,R.style.CenterDialogStyle);
                                editionDialog.setData("您已是最新版!");
                                editionDialog.setVersion("v"+ApplicationUtils.getVersionName(SettingActivity.this));
                                editionDialog.setListener(new EditionDialog.OnDialogClickListener() {
                                    @Override
                                    public void OnSureClick() {
                                    }
                                });
                                editionDialog.showDialog();
                            }
                        }
                        return null;
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                        Toast.makeText(SettingActivity.this, "request failed", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setForceUpdateListener(new ForceUpdateListener() {
            @Override
            public void onShouldForceUpdate() {
                forceUpdate();
            }
        });
        builder.setDownloadAPKPath(AppConfig.APK_URL);
        builder.excuteMission(this);
    }

    private UIData crateUIData(String apkUrl, String content) {
        UIData uiData = UIData.create();
        uiData.setTitle("版本更新");
        uiData.setDownloadUrl(apkUrl);
        uiData.setContent(content);
        return uiData;
    }

    private void forceUpdate() {
        finish();
    }
}

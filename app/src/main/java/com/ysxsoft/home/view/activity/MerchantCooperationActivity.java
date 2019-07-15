package com.ysxsoft.home.view.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.view.dialog.SinglePicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
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
@Route(path = "/main/MerchantCooperationActivity")
public class MerchantCooperationActivity extends BaseActivity {
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
    @BindView(R.id.picker)
    AppCompatTextView picker;
    @BindView(R.id.name)
    AppCompatEditText name;
    @BindView(R.id.phone)
    AppCompatEditText phone;
    @BindView(R.id.submit)
    Button submit;
    private String selectId="";//1超市，2美食

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getMerchantCooperationActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_merchant_cooperation;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("商户合作");
    }

    @OnClick({R.id.backLayout, R.id.picker, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.picker:
                //选择合作意向
                List<String> list = new ArrayList<>();
                list.add("超市");
                list.add("美食");
                list.add("车辆行程信息");
                list.add("招聘");
                list.add("房屋");
                SinglePicker p = new SinglePicker(this, R.style.BottomDialogStyle);
                p.setData(list, 0);
                p.setListener(new SinglePicker.OnDialogSelectListener() {
                    @Override
                    public void OnSelect(String data, int p) {
                        selectId=(++p)+"";
                        picker.setText(data);
                    }
                });
                p.showDialog();
                break;
            case R.id.submit:
                //提交
                submit.setEnabled(false);
                submit();
                break;
        }
    }

    private void submit() {
        if (selectId == null || "".equals(selectId)) {
            showToast("请选择合作类型！");
            submit.setEnabled(true);
            return;
        }
        if ("".equals(name.getText().toString())) {
            showToast("请填写姓名");
            submit.setEnabled(true);
            return;
        }
        if ("".equals(phone.getText().toString())) {
            showToast("请填写电话！");
            submit.setEnabled(true);
            return;
        }
        showLoadingDialog("正在添加");
        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().TEAM)
                .addParams("uid", SharedPreferencesUtils.getUid(MerchantCooperationActivity.this))
                .addParams("cateid", selectId)
                .addParams("name", name.getText().toString())
                .addParams("mobile", phone.getText().toString());
        builder.tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        submit.setEnabled(true);
                        hideLoadingDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        submit.setEnabled(true);
                        hideLoadingDialog();
                        ActionResponse resp = JsonUtils.parseByGson(response, ActionResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                showToast("提交成功");
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        }else{
                            showToast("操作失败");
                        }
                    }
                });
    }
}

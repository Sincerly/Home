package com.ysxsoft.home.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/ShopManagerSubmitMessageActivity")
public class ShopManagerSubmitMessageActivity extends BaseActivity {
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
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @Autowired
    boolean isShop;//true超市   false美食
    @Autowired
    String orderId;

    public static void start(boolean isShop, String orderId) {
        ARouter.getInstance().build(ARouterPath.getShopManagerSubmitMessageActivity()).withBoolean("isShop", isShop).withString("orderId", orderId).navigation();
    }

    public static void start(Activity activity,int requestCode,boolean isShop, String orderId) {
        ARouter.getInstance().build(ARouterPath.getShopManagerSubmitMessageActivity()).withBoolean("isShop", isShop).withString("orderId", orderId).navigation(activity,requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_manager_submit_message;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("拒绝退款原因");
    }

    @OnClick({R.id.backLayout, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.submit:
                if (IntentUtils.detectionForDoubleClick()) {
                    submit();
                }
                break;
        }
    }

    /**
     * 注册
     */
    private void submit() {
        showLoadingDialog("正在提交");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ORDER_AGREE_REFUSE)
                .addParams("orderid", orderId)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("refuse", editText.getText().toString())
                .addParams("type", isShop ? "1" : "2")//1超市，2美食
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
                        ActionResponse resp = JsonUtils.parseByGson(response, ActionResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                showToast("提交成功");
                                if(isShop){
                                    EventBus.getDefault().post("", "refreshShopManagerOrderList");//刷新家超市管理订单列表
                                }else{
                                    EventBus.getDefault().post("", "refreshFoodManagerOrderList");//刷新家美食管理订单列表
                                }
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("提交失败");
                        }
                    }
                });
    }
}

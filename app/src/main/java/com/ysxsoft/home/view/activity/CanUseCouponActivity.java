package com.ysxsoft.home.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.ysxsoft.common_base.pay.wx.WxPayUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.MyCanuseCoupnResponse;
import com.ysxsoft.home.response.MyCouponResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/CanUseCouponActivity")
public class CanUseCouponActivity extends BaseActivity
        implements IListAdapter<MyCanuseCoupnResponse.DataBean> {
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    ListManager manager;
    @Autowired
    String money;

    public static void start(Activity activity, int requestCode, String money) {
        ARouter.getInstance().build(ARouterPath.getCanUseCouponActivity()).withString("money", money).navigation(activity, requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("可用券");
    }

    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击了我的优惠券
                MyCanuseCoupnResponse.DataBean item= (MyCanuseCoupnResponse.DataBean) adapter.getItem(position);
                Intent intent=new Intent();
                intent.putExtra("couponId",item.getQuanid());
                intent.putExtra("couponDesc","满"+item.getMan()+"减"+item.getSize());
                intent.putExtra("size",item.getSize());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        manager.getAdapter().setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                request(manager.nextPage());
            }
        }, recyclerView);
        request(1);
    }

    @OnClick({R.id.backLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
        }
    }

    @Override
    public View createTitleView() {
        return null;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_activity_my_coupon_list;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_AVAILABLE_COUPON)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("account", money)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        manager.releaseRefresh();
                        hideLoadingDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        manager.releaseRefresh();
                        hideLoadingDialog();
                        MyCanuseCoupnResponse resp = JsonUtils.parseByGson(response, MyCanuseCoupnResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<MyCanuseCoupnResponse.DataBean> data = resp.getData();
                                manager.setData(data);
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取主页轮播失败");
                        }
                    }
                });
    }

    @Override
    public void fillView(BaseViewHolder helper, MyCanuseCoupnResponse.DataBean s) {
        helper.setText(R.id.m, s.getSize());
        helper.setText(R.id.time, "有效期至" + s.getTime());
        helper.setText(R.id.money, "满" + s.getMan() + "元可使用");
        helper.setText(R.id.allTime,"剩余"+s.getDay()+"天");

        //0未使用，1已用,2已过期
        ImageView pic = helper.getView(R.id.pic);
        if ("0".equals(s.getStatus())) {
            pic.setImageResource(R.mipmap.icon_coupon_blue);
        } else if ("1".equals(s.getStatus())) {
            pic.setImageResource(R.mipmap.icon_coupon_gray);
        } else {
            pic.setImageResource(R.mipmap.icon_coupon_gray);
        }
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, MyCanuseCoupnResponse.DataBean s) {
    }

    @Override
    public void attachActivity(AppCompatActivity activity) {

    }

    @Override
    public void dettachActivity() {

    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public boolean isMuteAdapter() {
        return false;
    }

    @Override
    public int[] getMuteTypes() {
        return new int[0];
    }

    @Override
    public int[] getMuteLayouts() {
        return new int[0];
    }
}

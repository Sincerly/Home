package com.ysxsoft.home.view.activity;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ShopManagerOrderResponse;
import com.ysxsoft.home.response.TeamMoneyHistoryResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/TeamMoneyHistoryActivity")
public class TeamMoneyHistoryActivity extends BaseActivity
        implements IListAdapter<TeamMoneyHistoryResponse.DataBean> {
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

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getTeamMoneyHistoryActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_money_history;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("团队明细");
    }

    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
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
        return R.layout.item_activity_team_money_history_list;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().TEAM_HISTORY)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("page",String.valueOf(page))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingDialog();
                        manager.releaseRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        manager.releaseRefresh();
                        hideLoadingDialog();
                        TeamMoneyHistoryResponse resp = JsonUtils.parseByGson(response, TeamMoneyHistoryResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<TeamMoneyHistoryResponse.DataBean> data = resp.getData();
                                manager.setData(data);
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

    @Override
    public void fillView(BaseViewHolder helper, TeamMoneyHistoryResponse.DataBean s) {
        TextView money = helper.getView(R.id.money);
        TextView name = helper.getView(R.id.name);
        TextView time = helper.getView(R.id.time);
        money.setSelected(true);
        money.setText("+"+s.getAccount()+"金币");

        name.setText(s.getNickname()+" "+s.getMobile());
        time.setText(s.getCreate_time());
//        if(helper.getAdapterPosition()%2==0){
//            money.setSelected(true);
//            money.setText("+¥666.00");
//        }else{
//            money.setSelected(false);
//            money.setText("-¥666.00");
//        }
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, TeamMoneyHistoryResponse.DataBean s) {

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

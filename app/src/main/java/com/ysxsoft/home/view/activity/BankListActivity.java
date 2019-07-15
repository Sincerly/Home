package com.ysxsoft.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.BankListResponse;
import com.ysxsoft.home.response.CardListResponse;
import com.ysxsoft.home.response.JobListResponse;
import com.ysxsoft.home.view.dialog.CenterTipsDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/BankListActivity")
public class BankListActivity extends BaseActivity
        implements IListAdapter<CardListResponse.DataBean> {
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
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.empty)
    LinearLayout empty;
    private ListManager manager;
    private boolean isEmpty = true;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getBankListActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_list;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("卡包");
    }

    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //返回选中的银行卡
                CardListResponse.DataBean d = (CardListResponse.DataBean) adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("name", d.getName());
                intent.putExtra("cid", d.getCid());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        manager.getAdapter().setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                CardListResponse.DataBean d = (CardListResponse.DataBean) adapter.getItem(position);
                showDeleteTipsDialog(position, d.getCid());
                return true;
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

    @OnClick({R.id.backLayout, R.id.empty, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.empty:
            case R.id.submit:
                //新增银行卡按钮
                AddBankActivity.start(this, 0x01);
                break;
        }
    }

    @Override
    public View createTitleView() {
        return null;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_activity_bank_list_list;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().CARD_LIST)
                .addParams("uid", SharedPreferencesUtils.getUid(BankListActivity.this))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        manager.releaseRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        manager.releaseRefresh();
                        CardListResponse resp = JsonUtils.parseByGson(response, CardListResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<CardListResponse.DataBean> data = resp.getData();
                                if (data.isEmpty()) {
                                    manager.getAdapter().setNewData(new ArrayList());
                                    if (submit.getVisibility() == View.VISIBLE) {
                                        submit.setVisibility(View.GONE);
                                    }
                                    empty.setVisibility(View.VISIBLE);
                                } else {
                                    manager.setData(data);
                                    if (submit.getVisibility() == View.GONE) {
                                        submit.setVisibility(View.VISIBLE);
                                        empty.setVisibility(View.GONE);
                                    }
                                }
                                manager.getAdapter().setEnableLoadMore(false);
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
    public void fillView(BaseViewHolder helper, CardListResponse.DataBean s) {
        helper.setText(R.id.bankName, s.getBank());
        helper.setText(R.id.bankUserName, s.getName());
        helper.setText(R.id.bankNo, s.getCode());

        TextView edit = helper.getView(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑
                Bundle bundle = new Bundle();
                bundle.putString("id", s.getCid());
                bundle.putString("username", s.getName());//持卡人
                bundle.putString("bankName", s.getBankid());//银行名称
                bundle.putString("bankDetailName", s.getBank());//开户行
                bundle.putString("bankNo", s.getCode());
                bundle.putString("bankids", s.getBankids());
                AddBankActivity.start(BankListActivity.this, bundle, 0x01);
            }
        });
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, CardListResponse.DataBean s) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isEmpty = false;
            request(1);
        }
    }


    private void showDeleteTipsDialog(int position, String nid) {
        CenterTipsDialog tipsDialog = new CenterTipsDialog(this, R.style.CenterDialogStyle);
        tipsDialog.initContent("是否删除该银行卡吗？");
        tipsDialog.setListener(new CenterTipsDialog.OnDialogClickListener() {
            @Override
            public void sure() {
                delete(position, nid);
            }
        });
        tipsDialog.showDialog();
    }

    private void delete(int position, String cid) {
        showLoadingDialog("正在删除");
        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().CARD_DELETE)
                .addParams("uid", SharedPreferencesUtils.getUid(BankListActivity.this))
                .addParams("cid", cid);

        builder.tag(this)
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
                                //请求成功
                                List<CardListResponse> list=manager.getAdapter().getData();
                                manager.getAdapter().notifyItemRemoved(position);
                                list.remove(position);
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("操作失败");
                        }
                    }
                });
    }
}

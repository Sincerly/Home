package com.ysxsoft.home.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
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
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.DeleteCarpoolResponse;
import com.ysxsoft.home.response.MyHousingResponse;
import com.ysxsoft.home.response.MyJobResponse;
import com.ysxsoft.home.view.dialog.CenterTipsDialog;
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
@Route(path = "/main/MyHouseActivity")
public class MyHouseActivity extends BaseActivity
        implements IListAdapter<MyHousingResponse.DataBean> {
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
        ARouter.getInstance().build(ARouterPath.getMyHouseActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_house;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("我的房源");
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_blue_edit);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if(SharedPreferencesUtils.hasHouse(this)) {
            rightWithIcon.setVisibility(View.VISIBLE);
        }else{
            rightWithIcon.setVisibility(View.GONE);
        }
        rightWithIcon.setCompoundDrawables(null, null, drawable, null);
        rightWithIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布房源
                AddHousingTransactionActivity.start(MyHouseActivity.this, 0x01);
            }
        });
    }

    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转房产详情
                MyHousingResponse.DataBean d= (MyHousingResponse.DataBean) adapter.getItem(position);
                HousingTransacationDetailActivity.start(d.getNid());
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
        return R.layout.item_activity_my_house_list;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_HOUSING)
                .addParams("uid", SharedPreferencesUtils.getUid(MyHouseActivity.this))
                .addParams("page", page + "")
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
                        MyHousingResponse resp = JsonUtils.parseByGson(response, MyHousingResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<MyHousingResponse.DataBean> data = resp.getData();
                                manager.setData(data);
                                if (page == 1) {
                                    manager.getAdapter().notifyDataSetChanged();
                                }
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
    public void fillView(BaseViewHolder helper, MyHousingResponse.DataBean s) {
        RoundImageView imageView = helper.getView(R.id.logo);
        Glide.with(this).load(s.getPic()).into(imageView);
        helper.setText(R.id.name, s.getTitle());
        helper.setText(R.id.subname, s.getHuxing() + "|" + s.getSize() + "m²|" + s.getCateid());
        helper.setText(R.id.address, s.getAddress() + s.getAddressid());
        helper.setText(R.id.money, "￥" + s.getPrice());

        TextView status = helper.getView(R.id.status);
        TextView edit = helper.getView(R.id.edit);
        TextView down = helper.getView(R.id.down);
        TextView delete = helper.getView(R.id.delete);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转编辑求职页面
                AddHousingTransactionActivity.start(MyHouseActivity.this,0x01,s.getNid());
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上下架
                String tips = "";
                if ("0".equals(s.getStatus())) {
                    //0下架
                    tips = "是否上架该房源?";
                } else {
                    //1上架
                    tips = "是否下架该房源?";
                }
                showTipsDialog(helper.getAdapterPosition(),s.getNid(), tips);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteTipsDialog(helper.getAdapterPosition(), s.getNid());
            }
        });
        if ("0".equals(s.getStatus())) {
            //0下架
            down.setText("上架");
            status.setText("已下架");
        } else {
            //1上架
            down.setText("下架");
            status.setText("已上架");
        }
    }

    private void showTipsDialog(int position,String nid, String content) {
        CenterTipsDialog tipsDialog = new CenterTipsDialog(this, R.style.CenterDialogStyle);
        tipsDialog.initContent(content);
        tipsDialog.setListener(new CenterTipsDialog.OnDialogClickListener() {
            @Override
            public void sure() {
                down(position,nid);
            }
        });
        tipsDialog.showDialog();
    }

    private void showDeleteTipsDialog(int position, String nid) {
        CenterTipsDialog tipsDialog = new CenterTipsDialog(this, R.style.CenterDialogStyle);
        tipsDialog.initContent("是否删除该房源吗？");
        tipsDialog.setListener(new CenterTipsDialog.OnDialogClickListener() {
            @Override
            public void sure() {
                delete(position, nid);
            }
        });
        tipsDialog.showDialog();
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, MyHousingResponse.DataBean s) {

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

    /**
     * 上下架
     *
     * @param nid
     */
    private void down(int position,String nid) {
        showLoadingDialog("操作中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_HOUSING_DOWN)
                .addParams("uid", SharedPreferencesUtils.getUid(MyHouseActivity.this))
                .addParams("nid", nid)
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
                        DeleteCarpoolResponse resp = JsonUtils.parseByGson(response, DeleteCarpoolResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                MyHousingResponse.DataBean d= (MyHousingResponse.DataBean) manager.getAdapter().getItem(position);
                                if("0".equals(d.getStatus())){
                                    d.setStatus("1");
                                }else{
                                    d.setStatus("0");
                                }
                                manager.getAdapter().notifyDataSetChanged();
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

    private void delete(int position, String nid) {
        showLoadingDialog("删除中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_HOUSING_DELETE)
                .addParams("uid", SharedPreferencesUtils.getUid(MyHouseActivity.this))
                .addParams("nid", nid)
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
                                //请求成功
                                if (manager != null) {
                                    List<MyHousingResponse.DataBean> data = manager.getAdapter().getData();
                                    manager.getAdapter().notifyItemRemoved(position);
                                    data.remove(position);
                                }
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("删除失败");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    request(1);
                    break;
                default:
                    break;
            }
        }
    }
}

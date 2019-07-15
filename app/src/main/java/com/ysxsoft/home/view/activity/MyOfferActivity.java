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
import com.ysxsoft.common_base.utils.ImageUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.FlowLayout;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.CarpoolListResponse;
import com.ysxsoft.home.response.DeleteCarpoolResponse;
import com.ysxsoft.home.response.MyJobResponse;
import com.ysxsoft.home.view.dialog.CenterTipsDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/MyOfferActivity")
public class MyOfferActivity extends BaseActivity
        implements IListAdapter<MyJobResponse.DataBean> {
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
        ARouter.getInstance().build(ARouterPath.getMyOfferActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_offer;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("我的招聘");
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_blue_edit);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if(SharedPreferencesUtils.hasJob(MyOfferActivity.this)) {
            rightWithIcon.setVisibility(View.VISIBLE);
        }else{
            rightWithIcon.setVisibility(View.GONE);
        }
        rightWithIcon.setCompoundDrawables(null, null, drawable, null);
        rightWithIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布招聘职位
                    AddJobOfferActivity.start();
            }
        });
    }

    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转至offer详情
                MyJobResponse.DataBean d= (MyJobResponse.DataBean) adapter.getItem(position);
                OfferDetailActivity.start(d.getNid());
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
        return R.layout.item_activity_my_offer_list;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_JOB)
                .addParams("uid", SharedPreferencesUtils.getUid(MyOfferActivity.this))
                .addParams("type", "1")//0求职，1招聘
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
                        try {
                            MyJobResponse resp = JsonUtils.parseByGson(response, MyJobResponse.class);
                            if (resp != null) {
                                if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                    //请求成功
                                    List<MyJobResponse.DataBean> data = resp.getData();
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
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void fillView(BaseViewHolder helper, MyJobResponse.DataBean s) {
        helper.setText(R.id.typeName, s.getIndustry());//职位行业名字
        helper.setText(R.id.name, s.getPosition());//职位名字
        helper.setText(R.id.money, s.getWageid());//薪资
        helper.setText(R.id.info, s.getAddressid()+" | "+s.getAddress()+" | "+s.getExperid()+" | "+s.getEducationid());//城市
        helper.setText(R.id.bossName, s.getCompany());//公司名字

        FlowLayout flowLayout = helper.getView(R.id.flowLayout);
        createView(flowLayout, s.getWelfareid());

        TextView status = helper.getView(R.id.status);//已下架 已上架
        TextView edit = helper.getView(R.id.edit);
        TextView down = helper.getView(R.id.down);
        TextView delete = helper.getView(R.id.delete);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转编辑招聘页面
                AddJobOfferActivity.start(MyOfferActivity.this,s.getNid(),0x01);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上下架
                String tips="";
                if("0".equals(s.getStatus())){
                    //0下架
                    tips="是否上架该招聘?";
                }else{
                    //1上架
                    tips="是否下架该招聘?";
                }
                showTipsDialog(helper.getAdapterPosition(),s.getNid(),tips);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteTipsDialog(helper.getAdapterPosition(), s.getNid());
            }
        });
        if("0".equals(s.getStatus())){
            //0下架
            down.setText("上架");
            status.setText("已下架");
        }else{
            //1上架
            down.setText("下架");
            status.setText("已上架");
        }
    }

    private void showTipsDialog(int position,String nid,String content) {
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
        tipsDialog.initContent("是否删除该招聘？");
        tipsDialog.setListener(new CenterTipsDialog.OnDialogClickListener() {
            @Override
            public void sure() {
                delete(position, nid);
            }
        });
        tipsDialog.showDialog();
    }

    private void createView(FlowLayout flowLayout, List<String> list) {
        flowLayout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            View v = View.inflate(this, R.layout.item_tab_job, null);
            TextView tab = v.findViewById(R.id.tab);
            tab.setText(list.get(i));
            flowLayout.addView(v);
        }
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, MyJobResponse.DataBean s) {

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

    /**
     * 上下架
     * @param nid
     */
    private void down(int position,String nid) {
        showLoadingDialog("操作中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_JOB_DOWN)
                .addParams("uid", SharedPreferencesUtils.getUid(MyOfferActivity.this))
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
                                MyJobResponse.DataBean d= (MyJobResponse.DataBean) manager.getAdapter().getItem(position);
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
                .url(AppConfig.getInstance().MY_JOB_DELETE)
                .addParams("uid", SharedPreferencesUtils.getUid(MyOfferActivity.this))
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
                                //请求成功
                                if (manager != null) {
                                    List<MyJobResponse.DataBean> data = manager.getAdapter().getData();
                                    manager.getAdapter().notifyItemRemoved(position);
                                    if(data.size()>position){
                                        data.remove(position);
                                    }
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

}

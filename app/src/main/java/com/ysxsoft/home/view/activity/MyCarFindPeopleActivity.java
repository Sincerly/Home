package com.ysxsoft.home.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.ysxsoft.home.response.DeleteCarpoolResponse;
import com.ysxsoft.home.response.CarpoolListResponse;
import com.ysxsoft.home.response.MyJobResponse;
import com.ysxsoft.home.view.dialog.CenterTipsDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/MyCarFindPeopleActivity")
public class MyCarFindPeopleActivity extends BaseActivity
        implements IListAdapter<CarpoolListResponse.DataBean> {
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
        ARouter.getInstance().build(ARouterPath.getMyCarFindPeopleActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_car_find_people;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("我的行程");
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_blue_edit);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if(SharedPreferencesUtils.hasCar(MyCarFindPeopleActivity.this)) {
            rightWithIcon.setVisibility(View.VISIBLE);
        }else{
            rightWithIcon.setVisibility(View.GONE);
        }
        rightWithIcon.setCompoundDrawables(null, null, drawable, null);
        rightWithIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布车找人
                AddCarFindPeopleActivity.start(MyCarFindPeopleActivity.this, 0x01);
            }
        });
    }

    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转车找人详情
                CarpoolListResponse.DataBean d= (CarpoolListResponse.DataBean) adapter.getItem(position);
                CarpoolCarForPeopleDetailActivity.start(d.getNid());
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
        return R.layout.item_activity_my_car_find_people_list;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_CARPOOL)
                .addParams("uid", SharedPreferencesUtils.getUid(MyCarFindPeopleActivity.this))
                .addParams("type", "2")//1人找车，2车找人
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
                        CarpoolListResponse resp = JsonUtils.parseByGson(response, CarpoolListResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<CarpoolListResponse.DataBean> data = resp.getData();
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
    public void fillView(BaseViewHolder helper, CarpoolListResponse.DataBean s) {
        helper.setText(R.id.start, s.getStart());//起始地址
        helper.setText(R.id.end, s.getEnd());//结束地址
        helper.setText(R.id.num, s.getNum());//剩余空位
        helper.setText(R.id.time, "发车时间：" + s.getTime());//发车时间
        helper.setText(R.id.carName, "车型：" + s.getCar());//车型号
        if ("0".equals(s.getStatus())) {//0进行中，1已过期,2已完成
            helper.setText(R.id.status, "进行中");
            helper.setTextColor(R.id.status, getResources().getColor(R.color.theme_blue_color));
        } else if ("1".equals(s.getStatus())) {
            helper.setText(R.id.status, "已过期");
            helper.setTextColor(R.id.status, getResources().getColor(R.color.color_e70d0d));
        } else {
            helper.setText(R.id.status, "已完成");
        }
        TextView edit = helper.getView(R.id.edit);
        TextView down = helper.getView(R.id.down);
        TextView delete = helper.getView(R.id.delete);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转车找人发布页面
                AddCarFindPeopleActivity.start(MyCarFindPeopleActivity.this,s, 0x02);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tips="";
                if("0".equals(s.getStyle())){
                    //0下架
                    tips="是否上架该行程?";
                }else{
                    //1上架
                    tips="是否下架该行程?";
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
        if("0".equals(s.getStyle())){
            //0下架
            down.setText("上架");
        }else{
            //1上架
            down.setText("下架");
        }
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, CarpoolListResponse.DataBean s) {
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

    private void down(int position, String nid) {
        showLoadingDialog("操作中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_CARPOOL_DOWN)
                .addParams("uid", SharedPreferencesUtils.getUid(MyCarFindPeopleActivity.this))
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
                                CarpoolListResponse.DataBean d= (CarpoolListResponse.DataBean) manager.getAdapter().getItem(position);
                                if("0".equals(d.getStyle())){
                                    d.setStyle("1");
                                }else{
                                    d.setStyle("0");

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

    private void showDeleteTipsDialog(int position, String nid) {
        CenterTipsDialog tipsDialog = new CenterTipsDialog(this, R.style.CenterDialogStyle);
        tipsDialog.initContent("是否删除该行程？");
        tipsDialog.setListener(new CenterTipsDialog.OnDialogClickListener() {
            @Override
            public void sure() {
                delete(position, nid);
            }
        });
        tipsDialog.showDialog();
    }

    private void delete(int position, String nid) {
        showLoadingDialog("删除中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_CARPOOL_DELETE)
                .addParams("uid", SharedPreferencesUtils.getUid(MyCarFindPeopleActivity.this))
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
                                    List<CarpoolListResponse.DataBean> data = manager.getAdapter().getData();
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    //0x01发布车找人
                    request(1);
                    break;
                case 0x02:
                    //0x02编辑车找人
                    request(1);
                    break;
            }
        }
    }
}

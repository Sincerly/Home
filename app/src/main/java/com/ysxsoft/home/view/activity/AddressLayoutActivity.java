package com.ysxsoft.home.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ysxsoft.home.MainActivity;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.AddressListResponse;
import com.ysxsoft.home.response.MyJobResponse;
import com.ysxsoft.home.view.dialog.CenterTipsDialog;
import com.zhy.http.okhttp.OkHttpUtils;
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
@Route(path = "/main/AddressLayoutActivity")
public class AddressLayoutActivity extends BaseActivity
        implements IListAdapter<AddressListResponse.DataBean> {
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
    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.listLayout)
    LinearLayout listLayout;
    @BindView(R.id.submit)
    AppCompatButton submit;
    @BindView(R.id.submit2)
    AppCompatButton submit2;
    @BindView(R.id.empty)
    LinearLayout empty;
    private boolean isEmpty = true;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getAddressLayoutActivity()).navigation();
    }

    public static void start(Activity activity, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getAddressLayoutActivity()).navigation(activity, requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_layout;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("收货地址");
    }

    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                AddressListResponse.DataBean s= (AddressListResponse.DataBean) adapter.getItem(position);
                intent.putExtra("id", s.getAddressid());
                intent.putExtra("username", s.getName());//联系人地址
                intent.putExtra("phone", s.getMobile());//手机号
                intent.putExtra("ems", s.getCode());//邮编
                intent.putExtra("addressDetail", s.getDetail());
                intent.putExtra("p", s.getProvince());
                intent.putExtra("c", s.getCity());
                intent.putExtra("d", s.getCounty());
                //剩余数据大小
                List list=adapter.getData();
                int size=list.size();
                if(list!=null){
                    intent.putExtra("dataSize",size);
                }
                setResult(RESULT_OK, intent);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(manager!=null&&manager.getAdapter()!=null){
                List list=manager.getAdapter().getData();
                int size=list.size();
                Intent intent = new Intent();
                if(list!=null){
                    intent.putExtra("dataSize",size);
                }
                setResult(RESULT_OK,intent);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public View createTitleView() {
        return null;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_activity_address_layout_list;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ADDRESS_LIST)
                .addParams("uid", SharedPreferencesUtils.getUid(AddressLayoutActivity.this))
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
                        AddressListResponse resp = JsonUtils.parseByGson(response, AddressListResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                List<AddressListResponse.DataBean> d = resp.getData();
                                if (page == 1) {
                                    if (!d.isEmpty()) {
                                        if (submit.getVisibility() == View.GONE) {
                                            submit.setVisibility(View.VISIBLE);
                                            empty.setVisibility(View.GONE);
                                        }
                                        listLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                                manager.setData(d);
                            } else if (HttpResponse.EMPTY.equals(resp.getCode())) {
                                //显示empty
                                manager.getAdapter().setNewData(new ArrayList());
                                if (submit.getVisibility() == View.VISIBLE) {
                                    submit.setVisibility(View.GONE);
                                }
                                empty.setVisibility(View.VISIBLE);
                                listLayout.setVisibility(View.GONE);
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取地址失败");
                        }
                    }
                });
    }

    @Override
    public void fillView(BaseViewHolder helper, AddressListResponse.DataBean s) {
        helper.setText(R.id.addressName, s.getName());
        helper.setText(R.id.addressPhone, s.getMobile());
        helper.setText(R.id.address, s.getProvince() + s.getCity() + s.getCounty() + s.getDetail());
//        helper.setText(R.id.status,s);//是否是设为默认

        TextView status = helper.getView(R.id.status);
        status.setSelected("1".equals(s.getStatus()) ? true : false);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefault(helper.getAdapterPosition(), s.getAddressid());
            }
        });
        TextView edit = helper.getView(R.id.edit);
        TextView delete = helper.getView(R.id.delete);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑
                Bundle bundle = new Bundle();
                bundle.putString("id", s.getAddressid());
                bundle.putString("username", s.getName());//持卡人
                bundle.putString("phone", s.getMobile());//银行名称
                bundle.putString("ems", s.getCode());//开户行
                bundle.putString("addressDetail", s.getDetail());
                bundle.putString("p", s.getProvince());
                bundle.putString("c", s.getCity());
                bundle.putString("d", s.getCounty());
                AddAddressActivity.start(AddressLayoutActivity.this, bundle, 0x01);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteTipsDialog(helper.getAdapterPosition(), s.getAddressid());
            }
        });
    }

    private void showDeleteTipsDialog(int position, String nid) {
        CenterTipsDialog tipsDialog = new CenterTipsDialog(this, R.style.CenterDialogStyle);
        tipsDialog.initContent("是否删除该地址?");
        tipsDialog.setListener(new CenterTipsDialog.OnDialogClickListener() {
            @Override
            public void sure() {
                delete(position, nid);
            }
        });
        tipsDialog.showDialog();
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, AddressListResponse.DataBean s) {

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

    @OnClick({R.id.submit, R.id.submit2, R.id.backLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                if(manager!=null&&manager.getAdapter().getData()!=null){
                    List list=manager.getAdapter().getData();
                    int size=list.size();
                    Intent intent = new Intent();
                    if(list!=null){
                        intent.putExtra("dataSize",size);
                    }
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    setResult(RESULT_OK);
                    finish();
                }
                break;
            case R.id.submit:
            case R.id.submit2:
                //新增地址
                AddAddressActivity.start(this, 0x01);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //新增成功
            isEmpty = false;
            request(1);
        }
    }

    /**
     * 删除
     */
    private void delete(int position, String addressId) {
        showLoadingDialog("正在删除");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().DELETE_ADDRESS)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("addressid", addressId)
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
                                if (manager != null) {
                                    List<MyJobResponse.DataBean> data = manager.getAdapter().getData();
                                    manager.getAdapter().notifyItemRemoved(position);
                                    if (data.size() > position) {
                                        data.remove(position);
                                    }
                                    if (data.size() == 0) {
                                        //显示empty
                                        manager.getAdapter().setNewData(new ArrayList());
                                        if (submit.getVisibility() == View.VISIBLE) {
                                            submit.setVisibility(View.GONE);
                                        }
                                        empty.setVisibility(View.VISIBLE);
                                        listLayout.setVisibility(View.GONE);
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

    /**
     * 设置默认
     */
    private void setDefault(int position, String addressId) {
        showLoadingDialog("设置默认");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SET_ADDRESS)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("addressid", addressId)
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
                                List<AddressListResponse.DataBean> dataBeans = manager.getAdapter().getData();
                                for (int i = 0; i < dataBeans.size(); i++) {
                                    if (i == position) {
                                        dataBeans.get(i).setStatus("1");
                                    } else {
                                        dataBeans.get(i).setStatus("0");
                                    }
                                }
                                manager.getAdapter().notifyDataSetChanged();
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("注册失败");
                        }
                    }
                });
    }
}

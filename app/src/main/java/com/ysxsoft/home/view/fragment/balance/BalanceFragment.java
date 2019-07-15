package com.ysxsoft.home.view.fragment.balance;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.BalanceInfoResponse;
import com.ysxsoft.home.response.ShopOrderResponse;
import com.ysxsoft.home.view.fragment.CountryFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/27 0027
 **/
public class BalanceFragment extends BaseFragment implements IListAdapter<BalanceInfoResponse.DataBean> {
    private ListManager manager;
    private int id;

    public static BalanceFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        BalanceFragment fragment = new BalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_balance;
    }

    @Override
    protected void doWork(View view) {
        id=getArguments().getInt("id",0);
        manager = new ListManager(this);
        manager.init(view);
        request(1);
    }

    @Override
    public View createTitleView() {
        return null;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_fragment_balance;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().YUE_HISTORY)
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
                .addParams("type",id+"")//1收入，2支出，3全部
                .addParams("page",page+"")//分页
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
                        BalanceInfoResponse resp = JsonUtils.parseByGson(response, BalanceInfoResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<BalanceInfoResponse.DataBean> data = resp.getData();
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
    public void fillView(BaseViewHolder helper, BalanceInfoResponse.DataBean s) {
        TextView money = helper.getView(R.id.money);
        if(s.getType()==0){
            money.setSelected(true);
            money.setText(s.getNum());
        }else{
            money.setSelected(false);
            money.setText(s.getNum());
        }
        helper.setText(R.id.name,s.getDescr());
        helper.setText(R.id.time,s.getAddtime());
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, BalanceInfoResponse.DataBean s) {

    }

    @Override
    public void attachActivity(AppCompatActivity activity) {

    }

    @Override
    public void dettachActivity() {

    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
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

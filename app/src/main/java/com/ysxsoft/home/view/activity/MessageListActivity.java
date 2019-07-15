package com.ysxsoft.home.view.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.ysxsoft.common_base.utils.WebViewUtils;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.CardListResponse;
import com.ysxsoft.home.response.MessageDetailResponse;
import com.ysxsoft.home.response.MessageListResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static org.apache.commons.lang3.CharSetUtils.delete;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/MessageListActivity")
public class MessageListActivity extends BaseActivity
        implements IListAdapter<MessageListResponse.DataBean> {
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
    PopupWindow actionPopupWindow;
    private TextView actionMenu1, actionMenu2;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getMessageListActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("消息列表");
    }

    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageListResponse.DataBean da = (MessageListResponse.DataBean) adapter.getItem(position);
                if("0".equals(da.getStyle())){
                    da.setStatus("1");
                    adapter.notifyDataSetChanged();
                    MessageDetailActivity.start(da.getMid());
                }
            }
        });
        manager.getAdapter().setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                showActionPopupWindow(view, position,(MessageListResponse.DataBean) adapter.getData().get(position));
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
        return R.layout.item_activity_message_list_list;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MSG_LIST)
                .addParams("uid", SharedPreferencesUtils.getUid(MessageListActivity.this))
                .addParams("page", String.valueOf(page))
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
                        MessageListResponse resp = JsonUtils.parseByGson(response, MessageListResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<MessageListResponse.DataBean> data = resp.getData();
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
    public void fillView(BaseViewHolder helper, MessageListResponse.DataBean s) {
        TextView point = helper.getView(R.id.point);
        helper.setText(R.id.name, s.getType());
        helper.setText(R.id.time, s.getCreate_time());
        helper.setText(R.id.content, s.getDescr());
        if ("0".equals(s.getStatus())) {
            //未读
            point.setVisibility(View.VISIBLE);
        } else {
            //已读
            point.setVisibility(View.GONE);
        }
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, MessageListResponse.DataBean s) {
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

    private void showActionPopupWindow(View itemView,int p, MessageListResponse.DataBean data) {
        int[] a = new int[2];
        itemView.getLocationOnScreen(a);
        itemView.measure(0, 0);
        View convertView = View.inflate(this, R.layout.pop_message_action, null);
        actionMenu1 = convertView.findViewById(R.id.actionMenu1);
        actionMenu2 = convertView.findViewById(R.id.actionMenu2);
        actionMenu1.setOnClickListener(new ReadClickListener(data));
        actionMenu2.setOnClickListener(new DeleteClickListener(p,data));
        actionPopupWindow = new PopupWindow(convertView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        actionPopupWindow.setOutsideTouchable(true);
        actionPopupWindow.setFocusable(true);
        actionPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置好参数之后再show
        actionPopupWindow.showAtLocation(itemView, Gravity.TOP | Gravity.START, itemView.getWidth() / 2, a[1] + itemView.getHeight() / 2);
    }

    class ReadClickListener implements View.OnClickListener {
        private MessageListResponse.DataBean c;

        public ReadClickListener(MessageListResponse.DataBean c) {
            this.c = c;
        }

        @Override
        public void onClick(View v) {
            //标记为已读
            read(c);
            actionPopupWindow.dismiss();
        }
    }

    class DeleteClickListener implements View.OnClickListener {
        private MessageListResponse.DataBean c;
        private int p;
        public DeleteClickListener(int p,MessageListResponse.DataBean c) {
            this.p=p;
            this.c = c;
        }

        @Override
        public void onClick(View v) {
            //标记为已读
            deleteMessage(p,c);
            actionPopupWindow.dismiss();
        }
    }

    /**
     * 删除消息
     */
    private void deleteMessage(int p,MessageListResponse.DataBean c) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MSG_EDIT)
                .addParams("uid", SharedPreferencesUtils.getUid(MessageListActivity.this))
                .addParams("mid", c.getMid())
                .addParams("status", "2")//	1标记已读，2删除
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
//                                if(manager.getAdapter().getData()!=null&&manager.getAdapter().getData().size()>p){
//                                    manager.getAdapter().notifyItemRemoved(p);
//                                }
                                request(1);
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("删除消息失败");
                        }
                    }
                });
    }

    /**
     * 标记为已读
     */
    private void read(MessageListResponse.DataBean c) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MSG_EDIT)
                .addParams("uid", SharedPreferencesUtils.getUid(MessageListActivity.this))
                .addParams("mid", c.getMid())
                .addParams("status", "1")//	1标记已读，2删除
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
                                showToast(resp.getMsg());

                                c.setStatus("1");
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
}

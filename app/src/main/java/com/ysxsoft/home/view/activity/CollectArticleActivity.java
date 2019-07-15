package com.ysxsoft.home.view.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.ArticleLikeResponse;
import com.ysxsoft.home.response.MyCollectResponse;
import com.ysxsoft.home.response.MyCollectResponse;
import com.ysxsoft.home.response.MyCollectResponse;
import com.ysxsoft.home.response.MyCollectResponse;
import com.ysxsoft.home.view.NineImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/CollectArticleActivity")
public class CollectArticleActivity extends BaseActivity
        implements IListAdapter<MyCollectResponse.DataBean> {
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
    @BindView(R.id.list)
    LinearLayout list;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    private boolean isEditMode = false;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getCollectArticleActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect_article;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("帖子收藏");
        rightWithIcon.setVisibility(View.VISIBLE);
        rightWithIcon.setText("编辑");
        rightWithIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑
                if (isEditMode) {
                    isEditMode = false;
                    rightWithIcon.setText("编辑");
                    rightWithIcon.setTextColor(getResources().getColor(R.color.color_686868));
                    bottomLayout.setVisibility(View.GONE);
                    //开始删除操作
                    deleteSelect();

                    manager.getAdapter().notifyDataSetChanged();
                } else {
                    isEditMode = true;
                    rightWithIcon.setText("完成");
                    rightWithIcon.setTextColor(getResources().getColor(R.color.theme_blue_color));
                    bottomLayout.setVisibility(View.VISIBLE);

                    manager.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    private void deleteSelect() {
        //删除选中的帖子
    }

    private void initList() {
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (all.isSelected()) {
                    all.setSelected(false);
                } else {
                    all.setSelected(true);
                }
                List<MyCollectResponse.DataBean> list = manager.getAdapter().getData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setChecked(all.isSelected());
                }
                manager.getAdapter().notifyDataSetChanged();
            }
        });

        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isEditMode) {
                    //编辑模式
                    MyCollectResponse.DataBean d = (MyCollectResponse.DataBean) adapter.getData().get(position);
                    d.setChecked(d.isChecked());
                    refreshButtonUI();//刷新全选按钮
                    manager.getAdapter().notifyDataSetChanged();
                } else {
                    //跳转帖子详情
                }
            }
        });
        manager.getAdapter().setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                request(manager.nextPage());
            }
        }, recyclerView);
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyCollectResponse.DataBean item= (MyCollectResponse.DataBean) adapter.getItem(position);
                if("1".equals(item.getStyle())){
                    //有视频的
                    if("2".equals(item.getStyle())){
                        ARouter.getInstance().build(ARouterPath.getPlayActivity()).withString("nid", ""+item.getNid()).withBoolean("isFriendCircle",true).navigation();
                    }else{
                        ARouter.getInstance().build(ARouterPath.getPlayActivity()).withString("nid", ""+item.getNid()).withBoolean("isFriendCircle",false).navigation();
                    }
                }else{
                    //无视频的
                    if("2".equals(item.getStyle())){
                        ARouter.getInstance().build(ARouterPath.getArticleDetailActivity()).withString("nid", ""+item.getNid()).withString("tname","帖子").withBoolean("isFriendCircle",true).navigation();
                    }else{
                        ARouter.getInstance().build(ARouterPath.getArticleDetailActivity()).withString("nid", ""+item.getNid()).withString("tname","帖子").withBoolean("isFriendCircle",false).navigation();
                    }
                }
            }
        });
        request(1);
    }

    @OnClick({R.id.backLayout, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                if (isEditMode) {
                    isEditMode = false;
                    manager.getAdapter().notifyDataSetChanged();
                    return;
                }
                backToActivity();
                break;
            case R.id.delete:
                //删除收藏的帖子
                if(IntentUtils.detectionForDoubleClick()){
                    delete();
                }
                break;
        }
    }

    @Override
    public View createTitleView() {
        return null;
    }

    @Override
    public int getItemLayoutId() {
        return 0;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_ARTICLE)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
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
                        MyCollectResponse resp = JsonUtils.parseByGson(response, MyCollectResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<MyCollectResponse.DataBean> data = resp.getData();
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
    public void fillView(BaseViewHolder helper, MyCollectResponse.DataBean s) {

    }

    @Override
    public void fillMuteView(BaseViewHolder helper, MyCollectResponse.DataBean s) {
        TextView content = helper.getView(R.id.content);
        CircleImageView logo = helper.getView(R.id.logo);
        TextView name = helper.getView(R.id.name);
        TextView say = helper.getView(R.id.say);
        TextView like = helper.getView(R.id.like);
        TextView address = helper.getView(R.id.address);
        content.setText(s.getTitle());//内容
        Glide.with(CollectArticleActivity.this).load(s.getAvatar()).apply(new RequestOptions().error(R.mipmap.icon_app_logo)).into(logo);//发表人头像
        name.setText(s.getNickname() + " " + s.getAddtime());//发表人昵称+时间
        say.setText(s.getDiscuss_num());//评论数量
        like.setText(s.getZan_num());
        address.setText(s.getAddress());

        if ("0".equals(s.getIszan())) {
            like.setSelected(false);
        } else {
            like.setSelected(true);
        }
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(helper.getAdapterPosition(), s.getNid());
            }
        });

        //填充
        switch (helper.getItemViewType()) {
            case MyCollectResponse.DataBean.LAYOUT1:
                NineImageView photoLayout = helper.getView(R.id.photoLayout);
                photoLayout.setData((ArrayList<String>) s.getImg());
                photoLayout.setDelegate(new NineImageView.Delegate() {
                    @Override
                    public void onClickNinePhotoItem(NineImageView ninePhotoLayout, View view, int position, String model, List<String> models) {
                        //点击了图片
                    }
                });
                photoLayout.setTouchDelegate(new TouchDelegate(new Rect(), recyclerView));
                break;
            case MyCollectResponse.DataBean.LAYOUT2:
                ImageView image = helper.getView(R.id.image1);
                List<String> imgs = s.getImg();
                if (imgs.size() > 0) {
                    Glide.with(CollectArticleActivity.this).load(imgs.get(0)).apply(new RequestOptions().error(R.mipmap.icon_app_logo)).into(image);
                }
                break;
        }

        LinearLayout checkBoxLayout = helper.getView(R.id.checkBoxLayout);
        CheckBox checkBox = helper.getView(R.id.checkbox);
        checkBox.setChecked(s.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                s.setChecked(isChecked);
                refreshButtonUI();
            }
        });
        if (isEditMode) {
            checkBoxLayout.setVisibility(View.VISIBLE);
            LinearLayout layout = helper.getView(R.id.contentLayout);
            layout.setPadding(0, 0, DisplayUtils.dp2px(this, -40), 0);
        } else {
            checkBoxLayout.setVisibility(View.GONE);
            LinearLayout layout = helper.getView(R.id.contentLayout);
            layout.setPadding(0, 0, DisplayUtils.dp2px(this, 0), 0);
        }
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
        return true;
    }

    @Override
    public int[] getMuteTypes() {
        return new int[]{MyCollectResponse.DataBean.LAYOUT1, MyCollectResponse.DataBean.LAYOUT2};
    }

    @Override
    public int[] getMuteLayouts() {
        return new int[]{R.layout.item_home_collect_layout1, R.layout.item_home_collect_layout2};
    }

    private void refreshButtonUI() {
        List<MyCollectResponse.DataBean> list = manager.getAdapter().getData();
        boolean isChecked = true;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isChecked()) {
                isChecked = false;
            }
        }
        all.setSelected(isChecked);
    }

    /**
     * 点赞
     */
    private void like(int position, String nid) {
        showLoadingDialog("点赞中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ARTICLE_LIKE)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
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
                        ArticleLikeResponse resp = JsonUtils.parseByGson(response, ArticleLikeResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                MyCollectResponse.DataBean d = (MyCollectResponse.DataBean) manager.getAdapter().getItem(position);
                                Pattern pattern = Pattern.compile("^\\d+$");
                                if ("1".equals(d.getIszan())) {
                                    //取消点赞
                                    if (pattern.matcher(d.getZan_num()).matches()) {
                                        int num = Integer.parseInt(d.getZan_num());
                                        d.setZan_num(String.valueOf(--num));
                                        d.setIszan("0");
                                    }
                                } else {
                                    //点赞
                                    if (pattern.matcher(d.getZan_num()).matches()) {
                                        int num = Integer.parseInt(d.getZan_num());
                                        d.setZan_num(String.valueOf(++num));
                                        d.setIszan("1");
                                    }
                                }
                                manager.getAdapter().notifyDataSetChanged();
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("点赞失败");
                        }
                    }
                });
    }

    private void delete() {
        List<String> ids = new ArrayList<>();
        List<MyCollectResponse.DataBean> data = manager.getAdapter().getData();
        for (int i = 0; i < data.size(); i++) {
            MyCollectResponse.DataBean d = data.get(i);
            if (d.isChecked()) {
                ids.add(d.getNid());
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            if (i == ids.size() - 1) {
                stringBuffer.append(ids.get(i));
            } else {
                stringBuffer.append(ids.get(i) + ",");
            }
        }
        if ("".equals(stringBuffer.toString())) {
            showToast("请选择删除的收藏帖子");
            return;
        }
        showLoadingDialog("正在删除..");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ARTICLE_DELETE_COLLECT)
                .addParams("uid", SharedPreferencesUtils.getUid(CollectArticleActivity.this))
                .addParams("sid", stringBuffer.toString())
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
                        if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                            showToast(resp.getMsg());
                            request(1);
                        } else {
                            showToast(resp.getMsg());
                        }
                    }
                });
    }

}

package com.ysxsoft.home.view.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ArticleLikeResponse;
import com.ysxsoft.home.response.CarpoolListResponse;
import com.ysxsoft.home.response.CollectArticleListResponse;
import com.ysxsoft.home.response.HomeArticleResponse;
import com.ysxsoft.home.view.NineImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/MyArticleActivity")
public class MyArticleActivity extends BaseActivity implements IListAdapter<CollectArticleListResponse.DataBean> {
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
        ARouter.getInstance().build(ARouterPath.getMyArticleActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_article;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("我的帖子");
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_blue_edit);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightWithIcon.setCompoundDrawables(null, null, drawable, null);
        rightWithIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发表帖子
                AddArticleActivity.start(MyArticleActivity.this,0x01);
            }
        });
        rightWithIcon.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化列表
     */
    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CollectArticleListResponse.DataBean item = (CollectArticleListResponse.DataBean) adapter.getItem(position);
                if ("1".equals(item.getStyle())) {
                    //有视频的
                    ARouter.getInstance().build(ARouterPath.getPlayActivity()).withString("nid", "" + item.getNid()).withBoolean("isFriendCircle", true).navigation();
                } else {
                    //无视频的
                    ARouter.getInstance().build(ARouterPath.getArticleDetailActivity()).withString("nid", "" + item.getNid()).withString("tname", "帖子").withBoolean("isFriendCircle", true).navigation();
                }
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
        return 0;
    }

    @Override
    public void request(int page) {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().MY_ARTICLE_LIST)
                .addParams("uid", SharedPreferencesUtils.getUid(MyArticleActivity.this))
                .addParams("type", "1")
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
                        CollectArticleListResponse resp = JsonUtils.parseByGson(response, CollectArticleListResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                List<CollectArticleListResponse.DataBean> data = resp.getData();
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
    public void fillView(BaseViewHolder helper, CollectArticleListResponse.DataBean s) {
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, CollectArticleListResponse.DataBean s) {
        TextView content = helper.getView(R.id.content);
        CircleImageView logo = helper.getView(R.id.logo);
        TextView name = helper.getView(R.id.name);
        TextView say = helper.getView(R.id.say);
        TextView like = helper.getView(R.id.like);
        TextView edit = helper.getView(R.id.edit);
        content.setText(s.getTitle());//内容
        Glide.with(MyArticleActivity.this).load(s.getAvatar()).apply(new RequestOptions().error(R.mipmap.icon_app_logo)).into(logo);//发表人头像
        name.setText(s.getNickname() + " " + s.getAddtime());//发表人昵称+时间
        say.setText(s.getDiscuss_num());//评论数量
        like.setText(s.getZan_num());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddArticleActivity.start(MyArticleActivity.this,0x01,s);
            }
        });
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
            case CollectArticleListResponse.DataBean.LAYOUT1:
                NineImageView photoLayout = helper.getView(R.id.photoLayout);
                photoLayout.setData((ArrayList<String>) s.getImg());
                photoLayout.setDelegate(new NineImageView.Delegate() {
                    @Override
                    public void onClickNinePhotoItem(NineImageView ninePhotoLayout, View view, int position, String model, List<String> models) {
                    }
                });
                photoLayout.setTouchDelegate(new TouchDelegate(new Rect(), recyclerView));
                break;
            case CollectArticleListResponse.DataBean.LAYOUT2:
                ImageView image = helper.getView(R.id.image1);
                List<String> imgs = s.getImg();
                if (imgs.size() > 0) {
                    Glide.with(MyArticleActivity.this).load(imgs.get(0)).apply(new RequestOptions().error(R.mipmap.icon_app_logo)).into(image);
                }
                break;
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
        return new int[]{CollectArticleListResponse.DataBean.LAYOUT1, CollectArticleListResponse.DataBean.LAYOUT2};
    }

    @Override
    public int[] getMuteLayouts() {
        return new int[]{R.layout.item_my_article_layout1, R.layout.item_my_article_layout2};
    }

    /**
     * 点赞
     */
    private void like(int position, String nid) {
        showLoadingDialog("点赞中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().ARTICLE_LIKE)
                .addParams("uid", SharedPreferencesUtils.getUid(MyArticleActivity.this))
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
                                CollectArticleListResponse.DataBean d = (CollectArticleListResponse.DataBean) manager.getAdapter().getItem(position);
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
                                EventBus.getDefault().post("", "refreshHome");//刷新家
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("点赞失败");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            switch (requestCode){
                case 0x01:
                    //添加/编辑
                    request(1);
                    break;
            }
        }
    }
}

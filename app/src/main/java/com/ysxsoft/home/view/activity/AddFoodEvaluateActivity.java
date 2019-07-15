package com.ysxsoft.home.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.ImageUtils;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.widgets.ERatingBar;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.FoodPingListResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/AddFoodEvaluateActivity")
public class AddFoodEvaluateActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate {

    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.backWithText)
    TextView backWithText;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.rightWithIcon)
    TextView rightWithIcon;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.ratingbar)
    ERatingBar ratingbar;
    @BindView(R.id.nickPhotoLayout)
    BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.loginButton)
    AppCompatButton loginButton;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    @Autowired
    String productId;

    public static void start(String productId, Activity activity, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getAddFoodEvaluateActivity()).withString("productId", productId).navigation(activity, requestCode);
    }

    float count;

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        initSnpl();
        ratingbar.setOnRatingChangeListener(new ERatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                count = RatingCount;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_food_evaluate;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("评价");
    }

    @OnClick({R.id.backLayout, R.id.loginButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.loginButton:
                //提交评价
                if (IntentUtils.detectionForDoubleClick()) {
                    submit();
                }
                break;
        }
    }

    /**
     * 初始化九宫格图片控件
     */
    private void initSnpl() {
        //设置最大选择张数
        mPhotosSnpl.setMaxItemCount(9);
        //设置是否可编辑
        mPhotosSnpl.setEditable(true);
        //显示九图控件的加号按钮
        mPhotosSnpl.setPlusEnable(true);
        //是否可拖拽排序
        mPhotosSnpl.setSortable(true);
        //设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
    }

    /**
     * 九宫格控件
     * 点击添加加号按钮执行的方法
     *
     * @param sortableNinePhotoLayout
     * @param view
     * @param position
     * @param models
     */
    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    /**
     * 九宫格控件
     * 点击删除按钮执行的方法
     *
     * @param sortableNinePhotoLayout
     * @param view
     * @param position
     * @param model
     * @param models
     */
    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    /**
     * 九宫格控件
     * 点击图片进入预览界面
     *
     * @param sortableNinePhotoLayout
     * @param view
     * @param position
     * @param model
     * @param models
     */
    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    /**
     * 九宫格控件
     * 拖拽排序发生变法执行的方法
     *
     * @param sortableNinePhotoLayout
     * @param fromPosition
     * @param toPosition
     * @param models
     */
    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
        //排序发生变化
    }

    /**
     * 九宫格控件
     * 图片选择对调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(data.getStringArrayListExtra("EXTRA_SELECTED_PHOTOS"));
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(data.getStringArrayListExtra("EXTRA_SELECTED_PHOTOS"));
        }
    }

    /**
     * 九宫格控件
     * 添加图片，申请权限
     */
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();
        startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
    }

    /**
     * 添加评论
     */
    public void submit() {
        if ("".equals(content.getText().toString())) {
            showToast("请输入评价内容");
            return;
        }
        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().FOOD_EVALUATE)
                .addParams("uid", SharedPreferencesUtils.getUid(this))//超市或店铺id
                .addParams("orderid", productId == null ? "" : productId)//商品id （店铺评论列表不传或0）
                .addParams("content", content.getText().toString())//	评价内容
                .addParams("score", count+"")//评分
                .addParams("type", "1");//0超市，1美食

        ArrayList<String> data = mPhotosSnpl.getData();
        for (int i = 0; i < data.size(); i++) {
            String path = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(data.get(i)), AppConfig.PHOTO_PATH);
            File f = new File(path);
            builder.addFile("pic["+i+"]", f.getName(), f);
        }

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
                                EventBus.getDefault().post("", "refreshFoodOrderList");//刷新家美食订单列表
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("评论失败");
                        }
                    }
                });
    }
}

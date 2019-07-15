package com.ysxsoft.home.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.ImageUtils;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.FoodOrderDetailResponse;
import com.ysxsoft.home.response.ShopOrderDetailResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/FoodOrderbackActivity")
public class FoodOrderbackActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate {
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
    @BindView(R.id.statusBar)
    View statusBar;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;
    @BindView(R.id.peiMoney)
    TextView peiMoney;
    @BindView(R.id.totalMoney)
    TextView totalMoney;
    @BindView(R.id.tuikuanfangshi)
    TextView tuikuanfangshi;
    @BindView(R.id.kuanxiang)
    TextView kuanxiang;
    @BindView(R.id.tuikuanyuanyin)
    EditText tuikuanyuanyin;
    @BindView(R.id.tuikuanjine)
    TextView tuikuanjine;
    @BindView(R.id.tuikuanshuoming)
    EditText tuikuanshuoming;
    @BindView(R.id.nickPhotoLayout)
    BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.orderBianHao)
    TextView orderBianHao;
    @BindView(R.id.totalCount)
    TextView totalCount;
    @BindView(R.id.orderTime)
    TextView orderTime;
    @BindView(R.id.orderPayType)
    TextView orderPayType;
    @BindView(R.id.orderPayTime)
    TextView orderPayTime;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.submit)
    Button submit;

    @Autowired
    int type;
    @Autowired
    String orderId;
    @Autowired
    boolean isShop;

    public static void start(boolean isShop, String orderId, int type) {
        ARouter.getInstance().build(ARouterPath.getFoodOrderbackActivity()).withInt("type", type).withBoolean("isShop", isShop).withString("orderId", orderId).navigation();
    }

    public static void start(Activity activity, int requestCode, boolean isShop, String orderId, int type) {
        ARouter.getInstance().build(ARouterPath.getFoodOrderbackActivity()).withInt("type", type).withBoolean("isShop", isShop).withString("orderId", orderId).navigation(activity,requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        initSnpl();
//        initProduct();
        if(type==0){
            tuikuanfangshi.setText("仅退款");
        }else{
            tuikuanfangshi.setText("退货退款");
        }
        getOrderDetail();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_orderback;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("申请退款");
    }

    @OnClick({R.id.backLayout, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.submit:
                //申请退款
                if(IntentUtils.detectionForDoubleClick()){
                    submit();
                }
                break;
        }
    }

   private void initProduct(List<FoodOrderDetailResponse.DataBean.ProductBean> products){
       //填充商品
       productRecyclerView.setNestedScrollingEnabled(false);
       if (!products.isEmpty()) {
           productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
           productRecyclerView.setAdapter(new RBaseAdapter<FoodOrderDetailResponse.DataBean.ProductBean>(this, R.layout.item_shop_order_child_product, products) {

               @Override
               protected void fillItem(RViewHolder holder, FoodOrderDetailResponse.DataBean.ProductBean item, int position) {
                   ImageView pic = holder.getView(R.id.pic);
                   Glide.with(FoodOrderbackActivity.this).load(item.getPic()).into(pic);
                   holder.setText(R.id.name, item.getGname());//商品名字
                   holder.setText(R.id.price, item.getPrice());//商品价格
                   holder.setText(R.id.num, "x" + item.getNum());//商品数量
                   holder.setText(R.id.ruleName, item.getRule_name());
               }

               @Override
               protected int getViewType(FoodOrderDetailResponse.DataBean.ProductBean item, int position) {
                   return 0;
               }
           });
       }
   }

    ///////////////////////////////////////////////////////////////////////////
    //图片选择器
    ///////////////////////////////////////////////////////////////////////////
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
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

    private void getOrderDetail() {
        showLoadingDialog("获取订单详情");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().FOOD_ORDER_BACK_DETAIL)
                .addParams("orderid", orderId)
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
                        FoodOrderDetailResponse resp = JsonUtils.parseByGson(response, FoodOrderDetailResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                FoodOrderDetailResponse.DataBean dataBean = resp.getData();
                                initProduct(dataBean.getProduct());
                                //配送费
                                peiMoney.setText("￥" + dataBean.getYun());
                                totalCount.setText("共" + dataBean.getNum() + "件商品 总计：");
                                totalMoney.setText("￥"+dataBean.getAccount());
                                kuanxiang.setText(dataBean.getTuides());//扣除款项
                                tuikuanjine.setText("￥"+dataBean.getAccount());//退款金额

                                orderBianHao.setText("订单编号：" + dataBean.getDsn());//订单编号
                                orderTime.setText("订单时间：" + dataBean.getAddtime());//订单时间
                                String payType = "在线支付";//1支付宝，2微信，3余额
                                if ("1".equals(dataBean.getPaytype())) {
                                    payType = "支付宝";
                                } else if ("2".equals(dataBean.getPaytype())) {
                                    payType = "微信";
                                } else if ("3".equals(dataBean.getPaytype())) {
                                    payType = "余额";
                                }
                                orderPayType.setText("支付方式：" + payType);//支付方式
                                orderPayTime.setText("支付时间：" + dataBean.getPaytime());//支付时间
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

    private void submit() {
        if ("".equals(tuikuanyuanyin.getText().toString())) {
            ToastUtils.shortToast(this, "请输入退款原因");
            return;
        }
        if ("".equals(tuikuanyuanyin.getText().toString())) {
            ToastUtils.shortToast(this, "请输入退款说明");
            return;
        }
        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().ORDER_REFUSE)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("orderid", orderId)
                .addParams("reason", tuikuanyuanyin.getText().toString())
                .addParams("content", tuikuanshuoming.getText().toString())
                .addParams("type", "2");//1超市，2美食

        ArrayList<String> data=mPhotosSnpl.getData();
        for (int i = 0; i <data.size() ; i++) {
            String path = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(data.get(i)), AppConfig.PHOTO_PATH);
            File f = new File(path);
            builder.addFile("pic["+i+"]", f.getName(), f);
        }
        showLoadingDialog("发布中");
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
                            showToast("获取失败");
                        }
                    }
                });
    }

}

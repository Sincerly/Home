package com.ysxsoft.home.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.ImageUtils;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.dialog.BaseInputCenterDialog;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.FoodShopInfoResponse;
import com.ysxsoft.home.response.ShopManagerInfoResponse;
import com.ysxsoft.home.view.dialog.FourTimePicker;
import com.ysxsoft.home.view.dialog.SinglePicker;
import com.ysxsoft.home.view.dialog.ThreePicker;
import com.ysxsoft.home.view.picker.CitySelectPicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import io.reactivex.functions.Consumer;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/18 0018
 **/
@Route(path = "/main/ShopManagerInfoActivity")
public class ShopManagerInfoActivity extends BaseActivity {
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
    @BindView(R.id.logo)
    CircleImageView logo;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.shopType)
    TextView shopType;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.shopAddress)
    TextView shopAddress;
    @BindView(R.id.shopDetailAddress)
    TextView shopDetailAddress;
    @BindView(R.id.shopHeaderImage)
    ImageView shopHeaderImage;
    @BindView(R.id.shopQualificationImage)
    ImageView shopQualificationImage;
    @BindView(R.id.shopQualificationImage2)
    ImageView shopQualificationImage2;
    private RxPermissions r;
    private BGAPhotoHelper mPhotoHelper;
    private static final int RC_CHOOSE_PHOTO = 0x01;
    private static final int RC_CHOOSE_SINGLE_PHOTO = 0x03;
    public static final int REQUEST_CODE_CROP = 0x02;

    @Autowired
    String did;

    public static void start(String did) {
        ARouter.getInstance().build(ARouterPath.getShopManagerInfoActivity()).withString("did", did).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        initPhotoHelper();
        getInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_manager_info;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("商家中心");
    }

    private void initPhotoHelper() {
        r = new RxPermissions(this);
        if (r.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) && r.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            File takePhotoDir = new File(AppConfig.PHOTO_CACHE_PATH);
            mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
        } else {
            r.requestEach(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        File takePhotoDir = new File(AppConfig.PHOTO_CACHE_PATH);
                        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
                    }
                }
            });
        }
    }

    private void getInfo() {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().SHOP_MANAGER_INFO)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ShopManagerInfoResponse resp = JsonUtils.parseByGson(response, ShopManagerInfoResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                ShopManagerInfoResponse.DataBean d = resp.getData();
                                Glide.with(ShopManagerInfoActivity.this).load(d.getAvatar()).into(logo);
                                name.setText(d.getMobile());//电话
                                time.setText(d.getStart_time() + "~" + d.getEnd_time());
                                if ("1".equals(d.getStyle())) {
                                    status.setText("营业中");
                                } else {
                                    status.setText("打烊中");
                                }
                                shopType.setText("超市");
                                shopName.setText(d.getName());
                                username.setText(d.getUsername());
                                shopAddress.setText(d.getProvince() + d.getCity() + d.getCounty());
                                shopDetailAddress.setText(d.getDetail());

                                Glide.with(ShopManagerInfoActivity.this).load(d.getPic()).into(shopHeaderImage);
                                Glide.with(ShopManagerInfoActivity.this).load(d.getCerficate()).into(shopQualificationImage);
                                Glide.with(ShopManagerInfoActivity.this).load(d.getCerficate()).into(shopQualificationImage2);

                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("失败");
                        }
                    }
                });
    }

    @OnClick({R.id.backLayout, R.id.logo, R.id.name, R.id.time, R.id.status, R.id.shopType, R.id.shopName, R.id.username, R.id.shopAddress, R.id.shopDetailAddress, R.id.shopHeaderImage, R.id.shopQualificationImage, R.id.shopQualificationImage2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logo:
                if (IntentUtils.detectionForDoubleClick()) {
                    style = "1";//1.头像 2门头照 3营业执照
                    choiceSinglePhotoWrapper();
                }
                break;
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.name:
                //修改电话
                BaseInputCenterDialog dialog = new BaseInputCenterDialog(this, R.style.CenterDialogStyle);
                dialog.initTitle("修改电话");
                dialog.initTips("请输入电话");
                dialog.initContent(name.getText().toString());
                dialog.setListener(new BaseInputCenterDialog.OnDialogClickListener() {
                    @Override
                    public void sure(String nickname) {
                        //1.修改店铺名称 2.修改店铺姓名 3.修改店铺省市区 4.修改详细地址  5.修改电话 6.修改营业时间  开始营业时间  关门营业时间 7.修改店铺状态
                        submit(5,nickname,"","");
                    }
                });
                dialog.showDialog();
                break;
            case R.id.time:
                //修改营业时间
                initFourPicker(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
                break;
            case R.id.status:
                //修改店铺状态
                List<String> list = new ArrayList<>();
                list.add("营业中");
                list.add("打烊中");
                SinglePicker picker = new SinglePicker(this, R.style.BottomDialogStyle);
                picker.setData(list,0);
                picker.setListener(new SinglePicker.OnDialogSelectListener() {
                    @Override
                    public void OnSelect(String d, int p) {
                        if(p==0){
                            //营业中
                            //1.修改店铺名称 2.修改店铺姓名 3.修改店铺省市区 4.修改详细地址  5.修改电话 6.修改营业时间  开始营业时间  关门营业时间 7.修改店铺状态
                            submit(7,"1","","");
                        }else{
                            //打烊
                            //1.修改店铺名称 2.修改店铺姓名 3.修改店铺省市区 4.修改详细地址  5.修改电话 6.修改营业时间  开始营业时间  关门营业时间 7.修改店铺状态
                            submit(7,"2","","");
                        }
                    }
                });
                picker.showDialog();
                break;
            case R.id.shopType:
                break;
            case R.id.shopName:
                //修改店铺名字
                BaseInputCenterDialog dialog2 = new BaseInputCenterDialog(this, R.style.CenterDialogStyle);
                dialog2.initTitle("修改店铺名称");
                dialog2.initTips("请输入店铺名称");
                dialog2.initContent(shopName.getText().toString());
                dialog2.setListener(new BaseInputCenterDialog.OnDialogClickListener() {
                    @Override
                    public void sure(String nickname) {
                        //1.修改店铺名称 2.修改店铺姓名 3.修改店铺省市区 4.修改详细地址  5.修改电话 6.修改营业时间  开始营业时间  关门营业时间 7.修改店铺状态
                        submit(1,nickname,"","");
                    }
                });
                dialog2.showDialog();
                break;
            case R.id.username:
                //修改姓名
                BaseInputCenterDialog dialog3 = new BaseInputCenterDialog(this, R.style.CenterDialogStyle);
                dialog3.initTitle("修改姓名");
                dialog3.initTips("请输入姓名");
                dialog3.initContent(username.getText().toString());
                dialog3.setListener(new BaseInputCenterDialog.OnDialogClickListener() {
                    @Override
                    public void sure(String nickname) {
                        //1.修改店铺名称 2.修改店铺姓名 3.修改店铺省市区 4.修改详细地址  5.修改电话 6.修改营业时间  开始营业时间  关门营业时间 7.修改店铺状态
                        submit(2,nickname,"","");
                    }
                });
                dialog3.showDialog();
                break;
            case R.id.shopAddress:
                //修改省市区
                CitySelectPicker cityPicker = new CitySelectPicker();
                cityPicker.initData(this);
                cityPicker.setListener(new CitySelectPicker.OnCityPickerClickListener() {
                    @Override
                    public void onSelect(String p, String c, String d) {
                        //1.修改店铺名称 2.修改店铺姓名 3.修改店铺省市区 4.修改详细地址  5.修改电话 6.修改营业时间  开始营业时间  关门营业时间 7.修改店铺状态
                        submit(3,p,c,d);
                    }
                });
                cityPicker.show();
                break;
            case R.id.shopDetailAddress:
                //修改详情
                BaseInputCenterDialog dialog4 = new BaseInputCenterDialog(this, R.style.CenterDialogStyle);
                dialog4.initTitle("修改地址详情");
                dialog4.initTips("地址详情");
                dialog4.initContent(shopDetailAddress.getText().toString());
                dialog4.setListener(new BaseInputCenterDialog.OnDialogClickListener() {
                    @Override
                    public void sure(String nickname) {
                        //1.修改店铺名称 2.修改店铺姓名 3.修改店铺省市区 4.修改详细地址  5.修改电话 6.修改营业时间  开始营业时间  关门营业时间 7.修改店铺状态
                        submit(4,nickname,"","");
                    }
                });
                dialog4.showDialog();
                break;
            case R.id.shopHeaderImage:
                if (IntentUtils.detectionForDoubleClick()) {
                    style = "2";//1.头像 2门头照 3营业执照
                    choiceSinglePhotoWrapper();
                }
                break;
            case R.id.shopQualificationImage:
                if (IntentUtils.detectionForDoubleClick()) {
                    style = "3";//1.头像 2门头照 3营业执照
                    choiceSinglePhotoWrapper();
                }
                break;
            case R.id.shopQualificationImage2:
//                if (IntentUtils.detectionForDoubleClick()) {
//                    style = "3";//1.头像 2门头照 3营业执照
//                    choiceSinglePhotoWrapper();
//                }
                break;
        }
    }

    private void initFourPicker(List<String> shi, List<String> ting, List<String> wei,List<String> time4) {
        int initP1 = 0;
        int initP2 = 0;
        int initP3 = 0;
        int initP4 = 0;
//        for (int i = 0; i < shi.size(); i++) {
//            if (defaultShi.equals(shi.get(i))) {
//                initP1 = i;
//            }
//        }
//        for (int i = 0; i < ting.size(); i++) {
//            if (defaultTing.equals(ting.get(i))) {
//                initP2 = i;
//            }
//        }
//        for (int i = 0; i < wei.size(); i++) {
//            if (defaultWei.equals(wei.get(i))) {
//                initP3 = i;
//            }
//        }
        FourTimePicker picker = new FourTimePicker(this, R.style.BottomDialogStyle);
        picker.setData(shi, ting, wei,time4, initP1, initP2, initP3,initP4);
        picker.setListener(new FourTimePicker.OnDialogSelectListener() {
            @Override
            public void OnSelect(String data1, int position1, String data2, int position2, String data3, int position3, String data4, int position4) {
                submit(6,data1+":"+data2,data3+":"+data4,"");
            }
        });
        picker.showDialog();
    }

    public void submit(int p, String params1, String params2, String params3) {
        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().UPDATE_MANAGER_INFO)
                .addParams("did", did)
                .addParams("type", "1");//1超市，2美食 （必传）
        switch (p) {
            case 1:
                //修改店铺名称
                builder.addParams("name", params1);
                break;
            case 2:
                //修改店铺姓名
                builder.addParams("username", params1);
                break;
            case 3:
                //修改店铺省市区
                builder.addParams("province", params1);
                builder.addParams("city", params2);
                builder.addParams("county", params3);
                break;
            case 4:
                //修改详细地址
                builder.addParams("detail", params1);
                break;
            case 5:
                //修改电话
                builder.addParams("mobile", params1);
                break;
            case 6:
                //修改营业时间
                builder.addParams("start_time", params1);
                builder.addParams("end_time", params2);
                break;
            case 7:
                //修改1营业，2停业
                builder.addParams("style", params1);
                break;

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
                                getInfo();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("修改失败");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RC_CHOOSE_PHOTO:
                    if (data != null) {
                        List<String> selectedPhotos = BGAPhotoPickerActivity.getSelectedPhotos(data);
                        if (selectedPhotos != null && selectedPhotos.size() > 0) {
                            //选择图片返回
                            try {
                                //startActivityForResult(mPhotoHelper.getCropIntent(selectedPhotos.get(0), 200, 200), REQUEST_CODE_CROP);
                                List<String> image = new ArrayList<>();
                                for (int i = 0; i < selectedPhotos.size(); i++) {
                                    String path = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(selectedPhotos.get(i)), AppConfig.PHOTO_PATH);
                                    image.add(path);
                                }
                                uploadProductPic(image);
                            } catch (Exception e) {
                                mPhotoHelper.deleteCameraFile();
                                mPhotoHelper.deleteCropFile();
                                BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case RC_CHOOSE_SINGLE_PHOTO:
                    if (data != null) {
                        List<String> selectedPhotos = BGAPhotoPickerActivity.getSelectedPhotos(data);
                        if (selectedPhotos != null && selectedPhotos.size() > 0) {
                            //选择图片返回
                            try {
                                //startActivityForResult(mPhotoHelper.getCropIntent(selectedPhotos.get(0), 200, 200), REQUEST_CODE_CROP);
                                if (selectedPhotos.size() > 0) {
                                    String path = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(selectedPhotos.get(0)), AppConfig.PHOTO_PATH);
                                    uploadSinglePic(path);
                                }
                            } catch (Exception e) {
                                mPhotoHelper.deleteCameraFile();
                                mPhotoHelper.deleteCropFile();
                                BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private void choicePhotoWrapper() {
        r.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                    File takePhotoDir = new File(AppConfig.PHOTO_PATH, "space");
                    File f = new File(AppConfig.PHOTO_PATH);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(ShopManagerInfoActivity.this)
                            .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                            .maxChooseCount(9) // 图片选择张数的最大值
                            .selectedPhotos(null) // 当前已选中的图片路径集合
                            .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                            .build();
                    startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
                }
            }
        });
    }

    private void choiceSinglePhotoWrapper() {
        r.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                    File takePhotoDir = new File(AppConfig.PHOTO_PATH, "space");
                    File f = new File(AppConfig.PHOTO_PATH);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(ShopManagerInfoActivity.this)
                            .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                            .maxChooseCount(1) // 图片选择张数的最大值
                            .selectedPhotos(null) // 当前已选中的图片路径集合
                            .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                            .build();
                    startActivityForResult(photoPickerIntent, RC_CHOOSE_SINGLE_PHOTO);
                }
            }
        });
    }

    private String style = "";//1.头像 2门头照 3营业执照

    private void uploadSinglePic(String images) {
        if (images.isEmpty()) {
            showToast("请上传图片");
            return;
        }
        showLoadingDialog("上传中");
        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().UPDATE_MANAGER_PIC)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("did", did)
                .addParams("style", style)//1头像，2门头照，3营业执照，4图片集
                .addParams("type", "1");//美食
        File file = new File(images);
        if ("1".equals(style)) {
            //1.头像
            builder.addFile("avatar", file.getName(), file);
        } else if ("2".equals(style)) {
            //2.门头照
            builder.addFile("pic", file.getName(), file);
        } else if ("3".equals(style)) {
            //3.营业执照
            builder.addFile("cerficate", file.getName(), file);
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
                                getInfo();
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

    /**
     * 上传商品图片
     */
    private void uploadProductPic(List<String> images) {
        if (images.isEmpty()) {
            showToast("请上传图片");
            return;
        }
        showLoadingDialog("上传中");
        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().UPDATE_MANAGER_PIC)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("did", did)
                .addParams("style", "4")//1头像，2门头照，3营业执照，4图片集
                .addParams("type", "1");//美食
        for (int i = 0; i < images.size(); i++) {
            File file = new File(images.get(i));
            builder.addFile("img[" + i + "]", file.getName(), file);
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
                                getInfo();
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

    /**
     * 删除美食图片
     */
    private void delete(String imgid) {
        showLoadingDialog("删除中");
        OkHttpUtils.post()
                .url(AppConfig.getInstance().DELETE_MANAGER_PIC)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("did", did)
                .addParams("imgid", imgid)
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
                                getInfo();
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

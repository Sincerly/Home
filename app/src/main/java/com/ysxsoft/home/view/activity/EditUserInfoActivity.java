package com.ysxsoft.home.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.dialog.BaseInputCenterDialog;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ActionResponse;
import com.ysxsoft.home.response.UserInfoResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
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
@Route(path = "/main/EditUserInfoActivity")
public class EditUserInfoActivity extends BaseActivity {
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
    @BindView(R.id.menu1)
    TextView menu1;
    @BindView(R.id.logo)
    CircleImageView logo;
    @BindView(R.id.menu2)
    TextView menu2;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.idcard)
    TextView idcard;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.menu5)
    TextView menu5;
    @BindView(R.id.women)
    RadioButton women;
    @BindView(R.id.men)
    RadioButton men;
    @BindView(R.id.level)
    ImageView level;
    @BindView(R.id.levelValue)
    TextView levelValue;
    @BindView(R.id.levelExp)
    TextView levelExp;
    @BindView(R.id.addressLayout)
    LinearLayout addressLayout;
    private BGAPhotoHelper mPhotoHelper;
    private RxPermissions r;
    private static final int RC_CHOOSE_PHOTO = 0x01;
    public static final int REQUEST_CODE_CROP = 0x02;

    @Autowired
    String p1;
    @Autowired
    String p2;
    @Autowired
    String p3;
    @Autowired
    String p4;
    @Autowired
    String p5;
    @Autowired
    String p6;
    @Autowired
    String p7;
    @Autowired
    String p8;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getEditUserInfoActivity()).navigation();
    }

    public static void start(String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8) {
        ARouter.getInstance().build(ARouterPath.getEditUserInfoActivity()).withString("p1", p1).withString("p2", p2).withString("p3", p3).withString("p4", p4).withString("p5", p5).withString("p6", p6).withString("p7", p7).withString("p8", p8).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        if (p1 != null) {
            Glide.with(this).load(p1).into(logo);
        }
        if (p2 != null) {
            nickname.setText(p2);
        }
        if (p3 != null) {
            username.setText(p3);
        }
        if (p4 != null) {
            idcard.setText(p4);
        }
        if (p5 != null) {
            phone.setText(p5);
        }
        if (p6 != null) {
            if ("1".equals(p6)) {
                men.setChecked(true);
                women.setChecked(false);
            } else {
                men.setChecked(false);
                women.setChecked(true);
            }
        }
        if (p7 != null) {
            level.setSelected(true);
            levelValue.setText(p7);
        }
        if (p8 != null) {
            levelExp.setText("(" + p8 + "经验值)");
        }
        initTitle();
        initPhotoHelper();
        women.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edit(null,"",null,null,null);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_user_info;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText("修改资料");
    }

    @OnClick({R.id.backLayout, R.id.logo, R.id.nickname, R.id.username, R.id.idcard, R.id.phone, R.id.addressLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.logo:
                choicePhotoWrapper();
                break;
            case R.id.nickname:
                //修改昵称
                BaseInputCenterDialog dialog = new BaseInputCenterDialog(this, R.style.CenterDialogStyle);
                dialog.initTitle("修改昵称");
                dialog.initTips("请输入昵称");
                dialog.initContent(nickname.getText().toString());
                dialog.setListener(new BaseInputCenterDialog.OnDialogClickListener() {
                    @Override
                    public void sure(String nickname) {
                        //点击了确定
                        edit(nickname,null,null,null,null);
                    }
                });
                dialog.showDialog();
                break;
            case R.id.username:
                //修改姓名
                BaseInputCenterDialog dialog2 = new BaseInputCenterDialog(this, R.style.CenterDialogStyle);
                dialog2.initTitle("修改姓名");
                dialog2.initTips("请输入姓名");
                dialog2.initContent(username.getText().toString());
                dialog2.setListener(new BaseInputCenterDialog.OnDialogClickListener() {
                    @Override
                    public void sure(String nickname) {
                        //点击了确定
                        edit(null,null,null,null,nickname);
                    }
                });
                dialog2.showDialog();
                break;
            case R.id.idcard:
                //修改身份证号
                BaseInputCenterDialog dialog3 = new BaseInputCenterDialog(this, R.style.CenterDialogStyle);
                dialog3.initTitle("修改身份证号");
                dialog3.initTips("请输入身份证号");
                dialog3.initContent(idcard.getText().toString());
                dialog3.setListener(new BaseInputCenterDialog.OnDialogClickListener() {
                    @Override
                    public void sure(String nickname) {
                        //点击了确定
                        edit(null,null,null,nickname,null);
                    }
                });
                dialog3.showDialog();
                break;
            case R.id.phone:
                //修改手机号
                if(IntentUtils.detectionForDoubleClick()){
                    UpdatePhoneActivity.start(mobile);
                }
                break;
            case R.id.addressLayout:
                //收货地址
                AddressLayoutActivity.start();
                break;
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
                    Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(EditUserInfoActivity.this)
                            .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                            .maxChooseCount(1) // 图片选择张数的最大值
                            .selectedPhotos(null) // 当前已选中的图片路径集合
                            .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                            .build();
                    startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
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
                            //拍照返回
                            try {
                                startActivityForResult(mPhotoHelper.getCropIntent(selectedPhotos.get(0), 200, 200), REQUEST_CODE_CROP);
                            } catch (Exception e) {
                                mPhotoHelper.deleteCameraFile();
                                mPhotoHelper.deleteCropFile();
                                BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case REQUEST_CODE_CROP:
                    String cropPath = mPhotoHelper.getCropFilePath();
                    String path = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(cropPath), AppConfig.PHOTO_PATH);
                    //裁剪后的
                    Glide.with(EditUserInfoActivity.this).load(new File(path)).into(logo);
                    //uploadLogo(path);
                    edit(null,null,path,null,null);
                    break;
                default:
                    break;
            }
        }
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

    private void edit(String nickname, String sex, String avatar, String certify, String username) {
        showLoadingDialog("正在修改");
        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().EDID_INFO)
                .addParams("uid", SharedPreferencesUtils.getUid(this));
        if (nickname != null) {
            builder.addParams("nickname", nickname);
        }
        if (sex != null) {
            builder.addParams("sex", women.isChecked() ? "2" : "1");
        }
        if (avatar != null) {
            File file = new File(avatar);
            builder.addFile("avatar", file.getName(), file);
        }
        if (certify != null) {
            builder.addParams("certify", certify);
        }
        if (username != null) {
            builder.addParams("username", username);
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
                               getUserInfo();
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("修改失败");
                        }
                    }
                });
    }

    private void getUserInfo() {
        if ("".equals(SharedPreferencesUtils.getUid(this))) {
            return;
        }
        OkHttpUtils.post()
                .url(AppConfig.getInstance().USER_INFO)
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
                        hideLoadingDialog();
                        UserInfoResponse resp = JsonUtils.parseByGson(response, UserInfoResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                UserInfoResponse.DataBean data = resp.getData();
                                //用户头像
                                Glide.with(EditUserInfoActivity.this).load(data.getAvatar_img()).into(logo);
                                nickname.setText(data.getNickname());
                                username.setText(data.getUsername());
                                idcard.setText(data.getCertify());
                                phone.setText(data.getMobile());
                                mobile=data.getMobile();
                                if ("1".equals(data.getSex())) {
                                    men.setChecked(true);
                                    women.setChecked(false);
                                } else {
                                    men.setChecked(false);
                                    women.setChecked(true);
                                }
                                level.setSelected(true);
                                levelValue.setText(data.getLevel());
                                levelExp.setText("(" + data.getScore() + "经验值)");
                            } else {
                                showToast(resp.getMsg());
                            }
                        } else {
                            //获取个人信息失败
                        }
                    }
                });
    }
    private String mobile="";
}

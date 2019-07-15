package com.ysxsoft.home.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.ApplicationUtils;
import com.ysxsoft.common_base.utils.BitmapUtils;
import com.ysxsoft.common_base.utils.FileUtils;
import com.ysxsoft.common_base.utils.ImageUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.KeyBoardUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.common_base.utils.WebViewUtils;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.common_base.view.dialog.CameraVideoDialog;
import com.ysxsoft.home.ARouterPath;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ArticleDetailResponse;
import com.ysxsoft.home.response.CollectArticleListResponse;
import com.ysxsoft.home.response.CountryArticleListResponse;
import com.ysxsoft.home.response.HomeCategoryResponse;
import com.ysxsoft.home.view.dialog.SinglePicker;
import com.ysxsoft.home.view.picker.CitySelectPicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Route(path = "/main/AddArticleActivity")
public class AddArticleActivity extends BaseActivity {
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
    @BindView(R.id.typePicker)
    AppCompatTextView typePicker;
    @BindView(R.id.articleTitle)
    AppCompatEditText articleTitle;
    @BindView(R.id.articleContent)
    AppCompatEditText articleContent;
    @BindView(R.id.citySelectPicker)
    TextView citySelectPicker;
    @BindView(R.id.image)
    ImageView videoImageView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> image = new ArrayList<>();
    private EAdapter adapter;
    private BGAPhotoHelper mPhotoHelper;
    private RxPermissions r;
    private static final int RC_CHOOSE_PHOTO = 0x01;
    public static final int REQUEST_CODE_CROP = 0x02;
    private String cateid = "";
    private boolean isUploadVideo = false;
    List<HomeCategoryResponse.DataBean> data;
    private String province = "";
    private String city = "";
    private String district = "";
    private String nid = "";
    @Autowired
    CollectArticleListResponse.DataBean d;
    private boolean isEdited = false;
    Map<String, String> picMap = new HashMap<String, String>();//记录网络图片和id

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getAddArticleActivity()).navigation();
    }

    public static void start(Activity activity, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getAddArticleActivity()).navigation(activity, requestCode);
    }

    /**
     * 编辑
     *
     * @param activity
     * @param requestCode
     * @param d
     */
    public static void start(Activity activity, int requestCode, CollectArticleListResponse.DataBean d) {
        ARouter.getInstance().build(ARouterPath.getAddArticleActivity()).withSerializable("d", d).navigation(activity, requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        initPhotoHelper();
        getHomeCategory();
        city = SharedPreferencesUtils.getSelectCity(this);
        district =  SharedPreferencesUtils.getSelectDistrict(this);
        address = city + "-" + district;

        if (d != null) {
            initDetail();
            isEdited = true;
            picMap.clear();

            List<CollectArticleListResponse.DataBean.PicBean> pic = d.getPic();
            for (int i = 0; i < pic.size(); i++) {
                CollectArticleListResponse.DataBean.PicBean item = pic.get(i);
                String picId = item.getPicid();
                picMap.put(item.getPic(), picId);
            }

            typePicker.setText(d.getCate());
            articleTitle.setText(d.getTitle());
            articleContent.setText(d.getContent());
            citySelectPicker.setText(d.getAddress());

            cateid = d.getCateid();
            address = d.getAddress();

            city =d.getCity();
            district = d.getCounty();
            address = city + "-" + district;

            if ("1".equals(d.getStyle())){//1有视频的，2无视频的
                image.addAll(d.getImg());
                isUploadVideo=true;
            }else{
                image.addAll(d.getImg());
                isUploadVideo=false;
            }
            nid=d.getNid();//帖子id
        }
        initRecyclerView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_article;
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        rightWithIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(cateid)) {
                    ToastUtils.shortToast(AddArticleActivity.this, "请选择帖子类目");
                    return;
                }
                if ("".equals(articleTitle.getText().toString())) {
                    ToastUtils.shortToast(AddArticleActivity.this, "请输入帖子标题");
                    return;
                }
                if (isUploadVideo) {
                    //发布视频帖子
                    addArticle(true);
                } else {
                    //发布图片帖子
                    addArticle(false);
                }
            }
        });
    }

    @OnClick({R.id.backLayout, R.id.typePicker, R.id.citySelectPicker})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                EventBus.getDefault().post("", "refreshHome");//刷新家
                backToActivity();
                break;
            case R.id.typePicker:
                //选择帖子类目
                KeyBoardUtils.hideInputMethod(this);
                List<String> list = new ArrayList<>();
                int p = 0;
                if (data != null) {
                    for (int i = 0; i < data.size(); i++) {
                        HomeCategoryResponse.DataBean d = data.get(i);
                        int cid = d.getCid();
                        String title = d.getTitle();
                        list.add(title);
                        if (cateid != null && cateid.equals(cid)) {
                            p = i;
                        }
                    }
                }
                SinglePicker picker = new SinglePicker(this, R.style.BottomDialogStyle);
                picker.setData(list, p);
                picker.setListener(new SinglePicker.OnDialogSelectListener() {
                    @Override
                    public void OnSelect(String d, int p) {
                        if (data != null && data.size() > p) {
                            cateid = data.get(p).getCid() + "";
                            typePicker.setText(data.get(p).getTitle());
                        }
                    }
                });
                picker.showDialog();
                break;
            case R.id.citySelectPicker:
                KeyBoardUtils.hideInputMethod(this);
                CitySelectPicker cityPicker = new CitySelectPicker();
                cityPicker.initData(this);
                cityPicker.setListener(new CitySelectPicker.OnCityPickerClickListener() {
                    @Override
                    public void onSelect(String p, String c, String d) {
                        citySelectPicker.setText(c + d);
                        city = c;
                        province = p;
                        district = d;
                        address = city + "-" + district;
                    }
                });
                cityPicker.show();
                break;
        }
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new EAdapter(R.layout.item_article_image);
        recyclerView.setAdapter(adapter);
        adapter.addFooterView(createFooterView());
        adapter.setFooterViewAsFlow(true);
        adapter.setNewData(image);
    }

    private View createFooterView() {
        View view = View.inflate(this, R.layout.footer_article, null);
        ImageView add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });
        return view;
    }

    private void showChooseDialog() {
        CameraVideoDialog dialog = new CameraVideoDialog(this, R.style.BottomDialogStyle);
        dialog.setListener(new CameraVideoDialog.OnDialogClickListener() {
            @Override
            public void cameraVideo() {
                //拍摄视频
                isUploadVideo = true;
                requestPermissions();
            }

            @Override
            public void album() {
                isUploadVideo = false;
                //拍摄照片/选择相册
                choicePhotoWrapper();
            }
        });
        dialog.showDialog();
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
                    Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(AddArticleActivity.this)
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

    private class EAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public EAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            RoundImageView img = helper.getView(R.id.image);
            if (item.contains("http://") || item.contains("uploads/apifile")) {
                Glide.with(AddArticleActivity.this).load(item).into(img);
            } else {
                Glide.with(AddArticleActivity.this).load(new File(item)).into(img);
            }
            ImageView close = helper.getView(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEdited) {
                        if (item.startsWith("http")) {
                            if (picMap != null && picMap.containsKey(item)) {
                                picMap.remove(item);//移除
                            }
                        }
                    }
                    if (isUploadVideo) {
                        //上传视频删除 显示加号
                        adapter.removeAllFooterView();
                        image.clear();
                        adapter.addFooterView(createFooterView());
                        adapter.notifyDataSetChanged();
                        isUploadVideo = false;
                    } else {
                        adapter.remove(helper.getAdapterPosition());
                        adapter.notifyDataSetChanged();
                    }
                }
            });
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
                                for (int i = 0; i < selectedPhotos.size(); i++) {
                                    String path = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(selectedPhotos.get(i)), AppConfig.PHOTO_PATH);
                                    image.add(path);
                                    adapter.notifyDataSetChanged();
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
                case REQUEST_CODE_CROP:
                    String cropPath = mPhotoHelper.getCropFilePath();
                    String path = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(cropPath), AppConfig.PHOTO_PATH);
                    //裁剪后的
                    break;
                case REQUEST_CODE_RECORD_VIDEO:
                    //录制视频成功
                    if (data != null) {
                        Uri uri = data.getData();
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(AddArticleActivity.this, uri);
                            mediaPlayer.prepare();
                            int duration = mediaPlayer.getDuration() / 1000;

                            MediaMetadataRetriever media = new MediaMetadataRetriever();
                            media.setDataSource(videoPath);
                            Bitmap bitmap = media.getFrameAtTime();//获取第一帧图片

                            String p = AppConfig.PHOTO_PATH;
                            String imgPath = System.currentTimeMillis() + ".png";
                            if (!new File(p).exists()) {
                                new File(p).mkdirs();
                            }
                            BitmapUtils.saveBitmap(bitmap, p + imgPath);

                            File file = new File(p + imgPath);
                            String videoFirstImagePath = file.getPath();
                            videoFirstImagePath = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(videoFirstImagePath), AppConfig.PHOTO_PATH);
                            image.clear();
                            image.add(videoFirstImagePath);
                            //去除加号
                            adapter.setNewData(image);
                            adapter.removeAllFooterView();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static final int REQUEST_CODE_RECORD_VIDEO = 0x03;
    String videoPath = "";

    /**
     * 录制视频
     */
    private void recordingVideo() {
        videoPath = AppConfig.PHOTO_PATH + "/recording_" + System.currentTimeMillis() + ".mp4";   // 保存路径
        File file = new File(videoPath);
        Uri uri = null;// 将路径转换为Uri对象
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            String authority = "com.ysxsoft.home.provider";//需要和配置清单文件里边一致
            uri = FileProvider.getUriForFile(AddArticleActivity.this, authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);  // 表示跳转至相机的录视频界面
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);    // MediaStore.EXTRA_VIDEO_QUALITY 表示录制视频的质量，从 0-1，越大表示质量越好，同时视频也越大
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);    // 表示录制完后保存的录制，如果不写，则会保存到默认的路径，在onActivityResult()的回调，通过intent.getData中返回保存的路径
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3);   // 设置视频录制的最长时间
        startActivityForResult(intent, REQUEST_CODE_RECORD_VIDEO); // 跳转
    }
    String address="";
    /**
     * 发布帖子
     */
    private void addArticle(boolean isVideo) {
        String style = "2";
        if (isVideo) {
            style = "1";
        } else {
            style = "2";
        }
        if("".equals(address)){
            showToast("请选择地址！");
            return;
        }

        PostFormBuilder builder = OkHttpUtils.post()
                .url(AppConfig.getInstance().ADD_ARTICLE)
                .addParams("uid", SharedPreferencesUtils.getUid(this))
                .addParams("cateid", cateid)
                .addParams("content", articleContent.getText().toString())
                .addParams("title", articleTitle.getText().toString())
                .addParams("style", style)//1有，2无（是否有视频）
                .addParams("address", address)
                .addParams("city",city )
                .addParams("county", district);
        List<String> ids=new ArrayList<>();
        if (isVideo) {
            File videoFile = new File(videoPath);
            if (image.size() == 0) {
                ToastUtils.shortToast(this, "请上传视频！");
                return;
            }
            if (image.get(0).contains("/apifile")) {
                //编辑视频没有动封面图
                if(picMap!=null&&picMap.containsKey(image.get(0))){
                    String id=picMap.get(image.get(0));
                    ids.add(id);
                }
            }else{
                //修改过了
                File videoImageFile = new File(image.get(0));
                builder.addFile("pic", videoFile.getName(), videoFile);
                builder.addFile("img", videoImageFile.getName(), videoImageFile);
            }
        } else {
            if (adapter.getData() != null) {
                List<String> path=adapter.getData();

                List<String> pics = new ArrayList<>();
                for (int i = 0; i < path.size(); i++) {
                    String p=path.get(i);
                    if(picMap!=null&&p.contains("/apifile")&&picMap.containsKey(p)){
                        //网络图片
                        String id=picMap.get(p);
                        ids.add(id);
                    }else{
                        pics.add(p);
                    }
                }
                //本地图片
                for (int i = 0; i < pics.size(); i++) {
                    File file = new File(pics.get(i));
                    builder.addFile("pic[" + i + "]", file.getName(), file);//头像
                }
            }
        }

        if (nid != null && !"".equals(nid)) {
            builder.addParams("nid", nid);
            StringBuilder stringBuilder1=new StringBuilder();
            for (int i = 0; i < ids.size(); i++) {
                if(i==ids.size()-1){
                    stringBuilder1.append(ids.get(i));
                }else{
                    stringBuilder1.append(ids.get(i)+",");
                }
            }
            if(ids.size()!=0){
                builder.addParams("picid", stringBuilder1.toString());
            }
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
                        CountryArticleListResponse resp = JsonUtils.parseByGson(response, CountryArticleListResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                EventBus.getDefault().post("", "refreshHome");//刷新家
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

    /**
     * 获取分类
     */
    private void getHomeCategory() {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().COMMUNITY_CATEGORY_LIST)
                .addParams("uid", SharedPreferencesUtils.getUid(AddArticleActivity.this))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HomeCategoryResponse resp = JsonUtils.parseByGson(response, HomeCategoryResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                data = resp.getData();
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取新闻分类失败");
                        }
                    }
                });
    }

    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA
    };

    private void requestPermissions() {
        RxPermissions re = new RxPermissions(this);
        re.requestEach(BASIC_PERMISSIONS)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            if (permission.name.equals(Manifest.permission.CAMERA)) {
                                recordingVideo();
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                        }
                    }
                });
    }

    /**
     * 获取详情
     */
    private void initDetail() {
        if (d == null) {
            //修改
            return;
        }
        title.setText("编辑发帖");
    }

}

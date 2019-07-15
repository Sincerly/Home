package com.ysxsoft.home.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.common_base.view.custom.FlowLayout;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ChoseRuleResponse;
import com.ysxsoft.home.response.ProductDetailResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * create by Sincerly on 2019/1/18 0018
 **/
public class BottomRuleDialog extends Dialog {
    private Context mContext;
    private OnDialogClickListener listener;
    private String productPic;//商品图片
    private String productPrice;
    private String productRule;
    private String productKuCun;//商品库存
    private String productId;//商品id
    private String groupId;
    private List<ProductDetailResponse.DataBean.RuleBean> ruleBeans;
    private CustomAdapter adapter;
    private int n = 1;
    private int store = 0;
    TextView price, rule, kucun;
    ImageView image;
    Button submit;

    public BottomRuleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_product_rule, null);
        image = view.findViewById(R.id.productImage);//商品图片
        price = view.findViewById(R.id.price);//商品价格
        rule = view.findViewById(R.id.rule);//商品尺寸
        kucun = view.findViewById(R.id.kucun);//商品尺寸
        submit = view.findViewById(R.id.submit);//
        if (productRule != null) {
            rule.setText(productRule);
        }
        if (productKuCun != null) {
            kucun.setText(productKuCun);
        }
        if (productPrice != null) {
            price.setText(productPrice+"金币");
        }
        if (productPic != null&&!"".equals(productPic)) {
            Glide.with(mContext).load(productPic).into(image);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        TextView reduc = view.findViewById(R.id.reduct);//减
        TextView add = view.findViewById(R.id.add);//加
        TextView num = view.findViewById(R.id.num);//商品数量
        num.setText(String.valueOf(n));

        ImageView cancel = view.findViewById(R.id.close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IntentUtils.detectionForDoubleClick()){
                    createOrder();
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n++;
                num.setText(String.valueOf(n));
            }
        });

        reduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (n > 1) {
                    n--;
                    num.setText(String.valueOf(n));
                } else {
                    ToastUtils.shortToast(mContext, "不能再减了！");
                }
            }
        });
        initRecyclerView(recyclerView);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new CustomAdapter(R.layout.item_product_special);
        recyclerView.setAdapter(adapter);

        adapter.setNewData(ruleBeans);
    }

    public OnDialogClickListener getListener() {
        return listener;
    }

    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        setContentView(init());
    }

    public void showDialog() {
        if (!isShowing()) {
            show();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = DisplayUtils.getDisplayWidth(mContext);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
            getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    public void setData(List<ProductDetailResponse.DataBean.RuleBean> ruleBeans) {
        this.ruleBeans = ruleBeans;
    }

    public void setTopData(String productPic, String productPrice, String productRule, String productKuCun, String productId, String productGroupId) {
        //商品图片  商品价格  商品规格
        this.productPic = productPic;
        this.productPrice = productPrice;
        this.productRule = productRule;
        this.productId = productId;
        this.groupId = productGroupId;
        this.productKuCun = productKuCun;
    }

    public interface OnDialogClickListener {
        void OnClick();

        void OnSelected(String ruleIds, String ruleName);

        void OnCreateOrderSuccess(String groupId, int n);
    }

    private class CustomAdapter extends BaseQuickAdapter<ProductDetailResponse.DataBean.RuleBean, BaseViewHolder> {
        public CustomAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProductDetailResponse.DataBean.RuleBean item) {
            TextView childTitle = helper.getView(R.id.childTitle);
            childTitle.setText(item.getTitle());
            FlowLayout flowLayout = helper.getView(R.id.flowLayout);
            flowLayout.removeAllViews();

            List<ProductDetailResponse.DataBean.RuleBean.SubBean> childBeans = item.getSub();
            for (int i = 0; i < childBeans.size(); i++) {
                View view = View.inflate(mContext, R.layout.item_rule, null);
                TextView btn = view.findViewById(R.id.menu);
                btn.setText(childBeans.get(i).getTitle());
                btn.setSelected(childBeans.get(i).isSelected());

                btn.setTag(i);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag();//总页数
                        int size = childBeans.size();
                        for (int j = 0; j < size; j++) {
                            ProductDetailResponse.DataBean.RuleBean.SubBean childSpecBean = childBeans.get(j);
                            childSpecBean.setSelected(position == j);
                        }
                        adapter.notifyDataSetChanged();
                        getRule();
                    }
                });
                flowLayout.addView(view);
            }
        }
    }

    private String getRuleIds() {
        List<String> ruleids = new ArrayList<>();
        for (int i = 0; i < ruleBeans.size(); i++) {
            ProductDetailResponse.DataBean.RuleBean ruleBean = ruleBeans.get(i);
            List<ProductDetailResponse.DataBean.RuleBean.SubBean> subBeans = ruleBean.getSub();
            for (int j = 0; j < subBeans.size(); j++) {
                ProductDetailResponse.DataBean.RuleBean.SubBean bean = subBeans.get(j);
                if (bean.isSelected()) {
                    ruleids.add(bean.getRuleid());
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ruleids.size(); i++) {
            if (i == ruleids.size() - 1) {
                sb.append(ruleids.get(i));
            } else {
                sb.append(ruleids.get(i) + ",");
            }
        }
        return sb.toString();
    }

    private void getRule() {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().GOLD_PRODUCT_RULE)
                .tag(this)
                .addParams("productid", productId)
                .addParams("ruleid", getRuleIds())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ChoseRuleResponse choseRuleResponse = JsonUtils.parseByGson(response, ChoseRuleResponse.class);
                        if (choseRuleResponse != null) {
                            if (HttpResponse.SUCCESS.equals(choseRuleResponse.getCode())) {
                                ChoseRuleResponse.DataBean d = choseRuleResponse.getData();
                                if (d.getStore() != null && !"".equals(d.getStore()) && !d.getStore().contains(".")) {
                                    store = Integer.parseInt(d.getStore());
                                }
                                rule.setText(d.getRule_name());
                                kucun.setText("库存"+d.getStore()+"件");
                                price.setText(d.getPrice()+"金币");
                                Glide.with(mContext).load(d.getMin_pic()).into(image);
                                groupId=d.getGroup_id();
                            } else {
                                ToastUtils.shortToast(mContext, choseRuleResponse.getMsg());
                            }
                        }
                    }
                });
    }

    private void createOrder() {
//        OkHttpUtils.post()
//                .url(AppConfig.URL.CREATE_ORDER)
//                .tag(this)
//                .addParams("uid", DBUtils.getUid())
//                .addParams("group_id", groupId)
//                .addParams("num",n+"")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        submit.setEnabled(true);
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("tag", response);
//                        CreateOrderResponse createOrderResponse = JsonUtils.parseByGson(response, CreateOrderResponse.class);
//                        if (createOrderResponse != null) {
//                            if (HttpResponse.SUCCESS.equals(createOrderResponse.getCode())) {
//                                String orderId=createOrderResponse.getData();
//                                submit.setEnabled(true);
//                                if(listener!=null){
//                                    //下单成功
//                                    listener.OnCreateOrderSuccess(orderId,n);
//                                }
//                                dismiss();
//                            } else {
//                                ToastUtils.shortToast(mContext, createOrderResponse.getMsg());
//                            }
//                        }
//                    }
//                });
        //测试
        listener.OnCreateOrderSuccess(groupId,n);
    }
}
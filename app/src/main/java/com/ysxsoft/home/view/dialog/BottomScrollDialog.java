package com.ysxsoft.home.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ysxsoft.common_base.view.custom.FlowLayout;
import com.ysxsoft.common_base.view.custom.banner.BannerLayoutManager;
import com.ysxsoft.common_base.view.custom.banner.CenterSnapHelper;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.ysxsoft.common_base.view.custom.banner.BannerLayoutManager.HORIZONTAL;
import static com.ysxsoft.common_base.view.custom.banner.BannerLayoutManager.VERTICAL;

/**
 * create by Sincerly on 2019/6/12 0005
 **/
public class BottomScrollDialog extends Dialog {
    private Context mContext;
    private OnDialogClickListener listener;
    private RecyclerView recyclerView;
    private List<Data> datas = new ArrayList<>();
    public BottomScrollDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_bottom_scroll, null);
        TextView sure = view.findViewById(R.id.sure);
        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取选中
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);

        BannerLayoutManager mLayoutManager = new BannerLayoutManager(getContext(), VERTICAL,false);
        mLayoutManager.setItemSpace(4);
        mLayoutManager.setCenterScale(1.1f);
        mLayoutManager.setMoveSpeed(1.5f);
        mLayoutManager.setMaxVisibleItemCount(3);
        recyclerView.setLayoutManager(mLayoutManager);

        List<String> data=new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("4");
        data.add("4");
        data.add("4");
        data.add("4");
        new CenterSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new RBaseAdapter<String>(mContext,R.layout.item_bottom_scroll_list,data){

            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        });
        if (datas != null) {
        }
        return view;
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
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
            getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    /**
     * 注入数据
     *
     * @param datas
     */
    public void setData(List<Data> datas) {
        this.datas = datas;
    }

    public interface OnDialogClickListener {
        void OnSureClick(List<Data> selectData);
    }

    public static class Data {
        private String id;
        private String url;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

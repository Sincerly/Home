package com.ysxsoft.home.view.picker;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ysxsoft.common_base.utils.CityUtils;
import com.ysxsoft.common_base.view.custom.FlowLayout;
import com.ysxsoft.common_base.view.custom.picker.JsonBean;
import com.ysxsoft.home.R;
import com.ysxsoft.home.adapter.RBaseAdapter;
import com.ysxsoft.home.adapter.RViewHolder;
import com.ysxsoft.home.response.Json;
import com.ysxsoft.home.view.dialog.SelectMenuPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class ThreeCityPicker {
    private PopupWindow popupWindow;
    private Activity activity;
    private RBaseAdapter adapter1;
    private RBaseAdapter adapter2;
    private RBaseAdapter adapter3;
    private String selectP;
    private String selectC;
    private String selectD;
    RecyclerView pRecyclerView;
    RecyclerView cRecyclerView;
    RecyclerView dRecyclerView;
    TextView p, c, d;
    OnMenuSelectListener selectListener;

    public void init(Activity activity) {
        this.activity = activity;
    }

    ArrayList<JsonBean> list1 = new ArrayList<>();
    List<JsonBean.CityBean> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();

    public void showPopDown(View view, String province, String city, String district, OnMenuSelectListener selectListener) {
        this.selectListener = selectListener;
        View convertView = View.inflate(activity, R.layout.pop_select_city, null);
        p = convertView.findViewById(R.id.p);
        c = convertView.findViewById(R.id.c);
        d = convertView.findViewById(R.id.d);
        View v = convertView.findViewById(R.id.v);
        pRecyclerView = convertView.findViewById(R.id.pRecyclerView);
        cRecyclerView = convertView.findViewById(R.id.cRecyclerView);
        dRecyclerView = convertView.findViewById(R.id.dRecyclerView);
        p.setText(province);
        c.setText(city);
        d.setText(district);
        //初始化省
        list1 = CityUtils.getProvince(activity);
        list2 = list1.get(0).getCityList();
        list3 = list2.get(0).getArea();
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //初始化市
        initAdapter1(list1);
        initAdapter2(list2);
        initAdapter3(list3);
        //初始化区
        popupWindow = new PopupWindow(convertView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.showAsDropDown(view);
        popupWindow.setFocusable(true);
    }

    private void initAdapter1(List<JsonBean> jsonBeans) {
        adapter1 = new RBaseAdapter<JsonBean>(activity, R.layout.item_city, jsonBeans) {

            @Override
            protected void fillItem(RViewHolder holder, JsonBean item, int position) {
                holder.setText(R.id.companyName, item.getName());
            }

            @Override
            protected int getViewType(JsonBean item, int position) {
                return 0;
            }
        };
        adapter1.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
                JsonBean i = (JsonBean) jsonBeans.get(position);
                List<JsonBean.CityBean> cityBeans = i.getCityList();
                selectP = i.getName();
                p.setText(i.getName());
                initAdapter2(cityBeans);
            }
        });
        pRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        pRecyclerView.setAdapter(adapter1);
    }

    private void initAdapter2(List<JsonBean.CityBean> cityBeans) {
        adapter2 = new RBaseAdapter<JsonBean.CityBean>(activity, R.layout.item_city, cityBeans) {

            @Override
            protected void fillItem(RViewHolder holder, JsonBean.CityBean item, int position) {
                holder.setText(R.id.companyName, item.getName());
            }

            @Override
            protected int getViewType(JsonBean.CityBean item, int position) {
                return 0;
            }
        };
        adapter2.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
                JsonBean.CityBean i = (JsonBean.CityBean) adapter2.getItemData(position);
                c.setText(i.getName());
                List<String> dBeans = i.getArea();
                selectC = i.getName();
                initAdapter3(dBeans);
            }
        });
        cRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        cRecyclerView.setAdapter(adapter2);
    }

    private void initAdapter3(List<String> dList) {
        adapter3 = new RBaseAdapter<String>(activity, R.layout.item_city, dList) {

            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                holder.setText(R.id.companyName, item);
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        };
        adapter3.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
                String name = (String) adapter3.getItemData(position);
                d.setText(name);
                selectD = name;
                if (selectListener != null) {
                    selectListener.select(selectP, selectC, selectD);
                }
                dismiss();
            }
        });
        dRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        dRecyclerView.setAdapter(adapter3);
    }

    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        return popupWindow != null && popupWindow.isShowing();
    }

    public interface OnMenuSelectListener {
        void select(String p, String c, String d);
    }
}

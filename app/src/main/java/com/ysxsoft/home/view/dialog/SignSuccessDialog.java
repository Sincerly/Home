package com.ysxsoft.home.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.home.R;

/**
 * create by Sincerly on 2019/1/5 0005
 **/
public class SignSuccessDialog extends Dialog {
    private Context mContext;
    private OnDialogClickListener listener;
    private String data;//经验值

    public SignSuccessDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_sign_success, null);
        TextView jingyan = view.findViewById(R.id.jingyan);
        if (data != null) {
            jingyan.setText(data);
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
            getWindow().setAttributes(lp);
            getWindow().setGravity(Gravity.CENTER);
        }
    }

    public void setData(String data) {
        this.data = data;
    }

    public interface OnDialogClickListener {
        void OnSureClick();
    }
}

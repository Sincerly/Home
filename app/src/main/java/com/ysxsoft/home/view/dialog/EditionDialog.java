package com.ysxsoft.home.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ysxsoft.home.R;

/**
 * create by Sincerly on 2019/1/5 0005
 **/
public class EditionDialog extends Dialog {
    private Context mContext;
    private OnDialogClickListener listener;
    private String data;//提示语
    private String version;//版本号

    public EditionDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_edition, null);
        TextView message = view.findViewById(R.id.message);
        TextView v = view.findViewById(R.id.version);
        Button sure = view.findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (data != null) {
            message.setText(data);
        }
        if (version != null) {
            v.setText(version);
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

    public void setVersion(String version) {
        this.version = version;
    }

    public interface OnDialogClickListener {
        void OnSureClick();
    }
}

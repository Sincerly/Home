package com.ysxsoft.home.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.DisplayUtils;

/**
 * create by Sincerly on 2019/1/28 0028
 **/
public class JinHuoDialog extends Dialog {
    private Context mContext;
    private String content = "";
    private OnDialogClickListener listener;

    public OnDialogClickListener getListener() {
        return listener;
    }

    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    public JinHuoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_jin_huo_input, null);
        Button sure = view.findViewById(R.id.submit);
        EditText input = view.findViewById(R.id.input);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.sure();
                }
                dismiss();
            }
        });
        if (content != null && !"".equals(content)) {
            input.setText(content);
        }
        return view;
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
            lp.width = DisplayUtils.getDisplayWidth(mContext) * 6 / 7;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
            getWindow().setGravity(Gravity.CENTER);
        }
    }

    public void initContent(String content) {
        this.content = content;
    }

    public interface OnDialogClickListener {
        void sure();
    }

    public static void showDialog(Context context,String content,OnDialogClickListener listener){
        JinHuoDialog dialog=new JinHuoDialog(context,R.style.CenterDialogStyle);
        dialog.initContent(content==null?"":content);
        dialog.showDialog();
    }
}
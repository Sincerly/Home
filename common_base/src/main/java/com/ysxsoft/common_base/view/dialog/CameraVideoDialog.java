package com.ysxsoft.common_base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.DisplayUtils;


public class CameraVideoDialog extends Dialog {
    private Context mContext;
    private OnDialogClickListener listener;

    public CameraVideoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_camera_video, null);
        TextView camera = view.findViewById(R.id.video);
        TextView photo = view.findViewById(R.id.photo);
        TextView cancel = view.findViewById(R.id.cancel);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cameraVideo();
                }
                dismiss();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.album();
                }
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
            lp.width = DisplayUtils.getDisplayWidth(mContext);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
            getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    public static CameraVideoDialog show(Context context, OnDialogClickListener dialogClickListener) {
        CameraVideoDialog dialog = new CameraVideoDialog(context, R.style.BottomDialogStyle);
        dialog.setListener(dialogClickListener);
        dialog.showDialog();
        return dialog;
    }

    public interface OnDialogClickListener {
        void cameraVideo();
        void album();
    }
}
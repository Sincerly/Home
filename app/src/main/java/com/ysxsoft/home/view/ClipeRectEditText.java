package com.ysxsoft.home.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * create by Sincerly on 2019/6/1 0001
 **/
public class ClipeRectEditText extends AppCompatEditText {
    private Paint paint;

    public ClipeRectEditText(Context context) {
        this(context, null);
    }

    public ClipeRectEditText(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public ClipeRectEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        paint = new Paint();
        paint.setColor(Color.parseColor("#282828"));
        paint.setTextSize(24);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        String str = "拒绝退款原因：";
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        Rect r = new Rect(getPaddingLeft(), getPaddingTop(), rect.width(), getPaddingTop() + rect.height());
        canvas.clipRect(r);
        canvas.restore();
    }
}

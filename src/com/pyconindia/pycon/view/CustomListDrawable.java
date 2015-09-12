package com.pyconindia.pycon.view;

import com.pythonindia.pycon.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomListDrawable extends View {

    private Paint paint;
    private RectF rect;

    public CustomListDrawable(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
//        paint.setColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(rect, paint);
    }
}

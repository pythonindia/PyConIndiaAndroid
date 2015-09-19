package com.pyconindia.pycon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.pyconindia.pycon.R;

public class CustomListDrawable extends View {

    private Paint paint;
    private static final int PADDING_LEFT = 20;
    private static final int PADDING_RIGHT = 10;
    private static final int PADDING_TOP = 20;
    private static final int PADDING_BOTTOM = 2;
    private static final int MARGIN_TOP = 2;
    private static final int FIX = 8;

    public CustomListDrawable(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int h = getHeight();
        int w = getWidth();
        float radius = (15);
        float left = PADDING_LEFT + FIX;
        float right = left + 15;
        float top = PADDING_TOP + 2*radius + 20;
        float bottom = h - PADDING_BOTTOM;

        float cx = PADDING_LEFT + radius;
        float cy = PADDING_TOP + radius;

//        Log.d("abhishek", ""+top+" - "+left + " -- "+bottom + " - "+right);
        canvas.drawCircle(cx, cy, radius, paint);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}

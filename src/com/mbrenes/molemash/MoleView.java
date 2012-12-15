package com.mbrenes.molemash;

import android.view.View;
import android.view.MotionEvent;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class MoleView extends View {
    private Bitmap bitmap;
    private boolean touched;
    private boolean started;
	private int backgroundColor;
	private float x;
	private float y;
	private float xBitmap;
	private float yBitmap;
	private int wBitmap;
	private int hBitmap;

    public MoleView(Context context) {
        super(context);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        
        xBitmap = 0;
        yBitmap = 0;
        
        wBitmap = bitmap.getWidth();
        hBitmap = bitmap.getHeight();
        
        touched = false;
        started = false;
        
        x = 0;
        y = 0;
        
        backgroundColor = Color.WHITE;
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        backgroundColor = color;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(backgroundColor);
        
        if (!started) {
        	canvas.drawBitmap(bitmap, xBitmap, yBitmap, null);
        	started = true;
        } else {
        	canvas.save();
        	canvas.translate(xBitmap, yBitmap);
        	canvas.restore();
        }
    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	super.onTouchEvent(event);

        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
        	case MotionEvent.ACTION_DOWN: {
        		x = event.getX();
        		y = event.getY();
        		touched = true;

        		invalidate();
            break;
        	}
        }

        return true;
    }*/
}

package com.mbrenes.molemash;

import android.os.Vibrator;
import android.view.View;
import android.view.MotionEvent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class MoleView extends View {
	private Context ctx;
    private Bitmap bitmap;
    private boolean show;
	private float xBitmap;
	private float yBitmap;
	private int vibration_lenght;
	private int backgroundColor;
	private int wBitmap;
	private int hBitmap;
	private int fail;
	private static int hits;
	private static int miss;

    private MoleView(Context context) {
        super(context);
        ctx = context;
        
        initialize(Color.WHITE);
    }
    
    public MoleView(Context context, int background) {
        super(context);
        ctx = context;
        
        initialize(background);
    }
    
    private void initialize(int background) {
    	Resources resources = getResources();
    	
    	bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);
    	
    	vibration_lenght = getResources().getInteger(R.integer.vibration_lenght);
        miss = resources.getInteger(R.integer.energy);
        fail = resources.getInteger(R.integer.fail);
        
        hits = 0;
        
        xBitmap = 0;
        yBitmap = 0;
        
        wBitmap = bitmap.getWidth();
        hBitmap = bitmap.getHeight();
        
        show = false;
        
        backgroundColor = background;
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        backgroundColor = color;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(backgroundColor);
        
        if (show) {
        	canvas.save();
        	canvas.drawBitmap(bitmap, xBitmap, yBitmap, null);
        	canvas.restore();
        } else {
        	show = true;
        }
    }
    
    public void moveMole(float x, float y) {
        xBitmap = x;
        yBitmap = y;
        
        invalidate();
    }
    
    public float getMoleWidth() {
    	return wBitmap;
    }
    
    public float getMoleHeight() {
    	return hBitmap;
    }
    
    public static int getScore() {
    	return hits;
    }
    
    public static int getEnergy() {
    	return miss;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	super.onTouchEvent(event);

    	if (miss > 0) {
	        switch (event.getAction()) {
	        	case MotionEvent.ACTION_DOWN: {
	        		float x = event.getX();
	        		float y = event.getY();
	        		
	        		if (x >= xBitmap && x < (xBitmap + wBitmap) && y >= yBitmap && y < (yBitmap + hBitmap)) {
	        			hits++;
	        			
	        			Vibrator vibrator = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
	        			vibrator.vibrate(vibration_lenght);
	                } else {
	                	miss = miss - fail;
	                }
	        	}
	        }
    	}
    	
        return true;
    }
}

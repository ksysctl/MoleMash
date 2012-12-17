package com.mbrenes.molemash;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.graphics.Color;

public class MainActivity extends Activity {
	private TextView txtEnergy;
	private TextView txtScore;
	private Handler handler;
	private LinearLayout bgLayout;
	private MoleView moleView;
	private int next_movement;
	private String scoreboard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		handler = new Handler();
		
    	next_movement = getResources().getInteger(R.integer.next_movement);
    	scoreboard = getResources().getString(R.string.txt_score);
    	
		txtScore = (TextView) findViewById(R.id.txtScore);
		txtEnergy = (TextView) findViewById(R.id.txtEnergy);
		
        txtScore.setText(scoreboard + " " + Integer.toString(0));
		
		bgLayout = (LinearLayout) findViewById(R.id.bgLayout);
		moleView = new MoleView(this, Color.DKGRAY);
		bgLayout.addView(moleView);
		
		moleView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				view.onTouchEvent(motionEvent);
				
				int miss = MoleView.getEnergy();
                if (miss <= 0)
                	handler.removeCallbacks(runnable);
				
				switch(motionEvent.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						txtScore.setText(scoreboard + " " + Integer.toString(MoleView.getScore()));
		            	LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(miss, txtEnergy.getHeight());
		            	txtEnergy.setLayoutParams(layoutParams);
					}
				}
		        
		        return true;
			}
        });
	}

	@Override
	protected void onPause() {
		handler.removeCallbacks(runnable);
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		handler.postDelayed(runnable, next_movement);
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
    
	private Runnable runnable = new Runnable() {
		public void run() {
			Random random = new Random();
			
			float x = random.nextFloat() * (moleView.getWidth() - moleView.getMoleWidth());
			float y = random.nextFloat() * (moleView.getHeight() - moleView.getMoleHeight());
			
			moleView.moveMole(x, y);
			handler.postDelayed(this, next_movement);
		}
	};
}

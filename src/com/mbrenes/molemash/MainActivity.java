package com.mbrenes.molemash;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MotionEvent;
import android.media.MediaPlayer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	private OnSharedPreferenceChangeListener pfListener;
	private SharedPreferences preferences;
	private TextView txtEnergy;
	private TextView txtScore;
	private Handler handler;
	private LinearLayout bgLayout;
	private MoleView moleView;
	private int next_movement;
	private boolean vibrator;
	private boolean sound;
	private String scoreboard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		handler = new Handler();
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		next_movement = Integer.valueOf(
			preferences.getString("speed", getResources().getString(R.string.next_movement))
		);
		sound = preferences.getBoolean("sound", getResources().getBoolean(R.bool.sound));
		vibrator = preferences.getBoolean("vibrator", getResources().getBoolean(R.bool.vibrator));

    	scoreboard = getResources().getString(R.string.txt_score);
    	
		txtScore = (TextView) findViewById(R.id.txtScore);
		txtEnergy = (TextView) findViewById(R.id.txtEnergy);
		
        txtScore.setText(scoreboard + " " + Integer.toString(0));
		
		bgLayout = (LinearLayout) findViewById(R.id.bgLayout);
		moleView = new MoleView(this);
		moleView.setVibrator(vibrator);
		bgLayout.addView(moleView);
		
		PreferenceListener();
		TouchListener();
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
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.menu_settings:
	            Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
	            startActivity(settingsActivity);
	    		preferences.registerOnSharedPreferenceChangeListener(pfListener);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
	private void TouchListener() {
		moleView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				view.onTouchEvent(motionEvent);
				
				int miss = moleView.getEnergy();
                if (miss <= 0) {
                	handler.removeCallbacks(runnable);
                	
                	if (sound) {
	                	try {
	                		MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.game_over);
	                		mediaPlayer.start();
	                	} catch (Exception e) { }
                	}
                }
				
				switch(motionEvent.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						txtScore.setText(scoreboard + " " + Integer.toString(moleView.getScore()));
		            	LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(miss, txtEnergy.getHeight());
		            	txtEnergy.setLayoutParams(layoutParams);
					}
				}
		        
		        return true;
			}
        });
	}
	
	private void PreferenceListener() {
		pfListener = new OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences pf, String key) {
				if (pf.getBoolean("preference_first_time", true)) {
					SharedPreferences.Editor editor = pf.edit();
					editor.putBoolean("preference_first_time", false);
					editor.commit();
				} else {
			    	if (key.equals("speed")) {
			    		next_movement = Integer.valueOf(
			    			preferences.getString("speed", getResources().getString(R.string.next_movement))
			    		);
			    	} else if (key.equals("sound")) {
			    		sound = preferences.getBoolean("sound", getResources().getBoolean(R.bool.sound));
			    	} else if (key.equals("vibrator")) {
			    		vibrator = preferences.getBoolean("vibrator", getResources().getBoolean(R.bool.vibrator));
			    		moleView.setVibrator(vibrator);
			    	}
			    	Toast.makeText(getBaseContext(), "Preference saved", Toast.LENGTH_SHORT).show();
				}
			}
		};
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

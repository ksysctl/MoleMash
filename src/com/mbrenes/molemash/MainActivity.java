package com.mbrenes.molemash;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private LinearLayout bgLayout;
	private static MoleView mole;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bgLayout = (LinearLayout) findViewById(R.id.bgLayout);
		mole = new MoleView(this);
		bgLayout.addView(mole);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}

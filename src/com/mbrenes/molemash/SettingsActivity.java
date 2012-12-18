package com.mbrenes.molemash;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.annotation.TargetApi;

public class SettingsActivity extends PreferenceActivity {
	private static int preferences = R.xml.preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			AddResourceOlder();
		} else {
			AddResource();
		}
	}

	@SuppressWarnings("deprecation")
	protected void AddResourceOlder() {
		addPreferencesFromResource(preferences);
	}
	
	@TargetApi(11)
	protected void AddResource() {
		getFragmentManager().beginTransaction().replace(
			android.R.id.content, new SettingsFragment()
		).commit();
	}

	@TargetApi(11)
	public static class SettingsFragment extends PreferenceFragment {
		@Override
		public void onCreate(final Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(SettingsActivity.preferences);
		}
	}
}

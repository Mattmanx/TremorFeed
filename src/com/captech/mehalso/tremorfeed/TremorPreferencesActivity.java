/**
 * 
 */
package com.captech.mehalso.tremorfeed;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * @author mmehalso
 *
 */
public class TremorPreferencesActivity extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}

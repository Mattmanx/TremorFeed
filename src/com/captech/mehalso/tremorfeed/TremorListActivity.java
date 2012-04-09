package com.captech.mehalso.tremorfeed;

import java.util.Collection;

import com.captech.mehalso.tremorfeed.pojo.TremorRecord;
import com.captech.mehalso.tremorfeed.remote.HttpUSGSTremorRetrieverImpl;
import com.captech.mehalso.tremorfeed.remote.TremorRetriever;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class TremorListActivity extends ListActivity {
	private TremorArrayAdapter arrayAdapter;
	
	//cache this so we don't have to inflate it again and again.
	private ImageView refreshActionView;
	private Animation refreshAnimation;
	
	/**
	 * 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        arrayAdapter = new TremorArrayAdapter(this);
        setListAdapter(arrayAdapter);
        
        if(isNetworkAvailable()) {
        	fireTremorQuery(null);
        } else {
        	handleNetworkError();
        }
    }
    
    /**
     * Register the settings menu item.  
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        
        return true;
    }
    
    /**
     * Start up the preferences view.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.menu_settings: //preferences screen 
    		startActivity(new Intent(this, TremorPreferencesActivity.class));
    		return true;
    	case R.id.menu_refresh:
    		fireTremorQuery(item);
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * 
     */
    private void fireTremorQuery(MenuItem itemToAnimate) {
    	//Start refresh animation.
    	if(itemToAnimate != null) {
	    	if(refreshActionView == null) {
	    		refreshActionView = (ImageView) getLayoutInflater().inflate(R.layout.refresh_action_view, null);
	    		refreshAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
	    		refreshAnimation.setRepeatCount(Animation.INFINITE);
	    	}
    	
	    	refreshActionView.startAnimation(refreshAnimation);
    	
	    	itemToAnimate.setActionView(refreshActionView);
    	}
    	
    	//Fire task
    	new TremorQueryTask(itemToAnimate).execute(getString(R.string.usgs_url));
    }
    
    /**
     * 
     */
    private void handleNetworkError() {
    	//TODO: Notify the user gracefully!!!
    	//for now:
    	Toast.makeText(this, R.string.network_error_message, 10);
    }
    
    /**
     * 
     * @return
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) 
        	getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @author mmehalso
     *
     */
    private class TremorQueryTask extends AsyncTask<String,Void,Collection<TremorRecord>> {

    	MenuItem itemToAnimate = null;
    	
    	TremorQueryTask(MenuItem item) {
    		super();
    		itemToAnimate = item;
    	}
    	
    	/**
    	 * 
    	 */
		@Override
		protected Collection<TremorRecord> doInBackground(
				String... urls) {
			Log.v(TremorConstants.LOG_TAG, "Enter doInBackground()");
			
			Collection<TremorRecord> records = null;
			
			TremorRetriever service = new HttpUSGSTremorRetrieverImpl(urls[0]);
			
			try {
				records = service.getLatestEarthquakeData();
			} catch(Throwable t) {
				Log.e(TremorConstants.LOG_TAG, "Connection or parsing error while " +
						"pulling earthquake data.", t);
				records = null;
			}
			
			Log.v(TremorConstants.LOG_TAG, "Leave doInBackground() - records [" + 
					(records == null ? "null" : records.size()) + "]");
			
			return records;
		}
		
		/**
		 * 
		 */
		@Override
		protected void onPostExecute(Collection<TremorRecord> records) {
			//stop refresh animation.
			if(itemToAnimate != null) {
				itemToAnimate.getActionView().clearAnimation();
				itemToAnimate.setActionView(null);
			}
			
			if(records == null) {
				handleNetworkError();
			} else {
				//check preferences to see whether we should flush data...
				if(PreferenceManager.getDefaultSharedPreferences(TremorListActivity.this)
						.getBoolean(getString(R.string.pref_refresh_id), Boolean.TRUE)) {
					arrayAdapter.clear();
				}
				
				//Note - we may not want to perform this add() on the UI thread for huge data sets.  For now, 
				// performance does not seem affected so we'll leave as-is.  Not sure whether data is thread-safe,
				// anyway, so this is probably the best bet other than writing a completely custom collection-backed
				// list view adapter with a synchronized data set.  
				for(TremorRecord record : records) 
					arrayAdapter.add(record);
				
				arrayAdapter.sortRecordsByDate();
			}
		}
    }
}
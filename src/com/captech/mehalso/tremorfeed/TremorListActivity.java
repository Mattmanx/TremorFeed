package com.captech.mehalso.tremorfeed;

import java.util.Collection;

import com.captech.mehalso.tremorfeed.pojo.TremorRecord;
import com.captech.mehalso.tremorfeed.remote.HttpUSGSTremorRetrieverImpl;
import com.captech.mehalso.tremorfeed.remote.TremorRetriever;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TremorListActivity extends ListActivity {
	private TremorArrayAdapter arrayAdapter;
	
	/**
	 * 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        arrayAdapter = new TremorArrayAdapter(this);
        setListAdapter(arrayAdapter);
        
        if(isNetworkAvailable()) {
        	fireTremorQuery();
        } else {
        	handleNetworkError();
        }
    }
    
    /**
     * Register the settings menu item.  
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, 0, 0, "Settings");
    	return super.onCreateOptionsMenu(menu);
    }
    
    /**
     * Start up the preferences view.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case 0:
    		startActivity(new Intent(this, TremorPreferencesActivity.class));
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * 
     */
    private void fireTremorQuery() {
    	new TremorQueryTask().execute(getString(R.string.usgs_url));
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
			
			//TODO: Sort records based on date.
			
			Log.v(TremorConstants.LOG_TAG, "Leave doInBackground() - records [" + 
					(records == null ? "null" : records.size()) + "]");
			
			return records;
		}
		
		/**
		 * 
		 */
		@Override
		protected void onPostExecute(Collection<TremorRecord> records) {
			if(records == null) {
				handleNetworkError();
			} else {
				//TODO: check configuration for merge versus replaceAll
				//for now: 
				arrayAdapter.clear();
				
				for(TremorRecord record : records) 
					arrayAdapter.add(record);
			}
		}
    }
}
/**
 * 
 */
package com.captech.mehalso.tremorfeed;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * @author mmehalso
 *
 */
public class TremorWebActivity extends Activity {
	private WebView webView;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tremor_web_view);
 
		webView = (WebView) findViewById(R.id.tremor_web_view);
		webView.getSettings().setJavaScriptEnabled(true);

		String url = "http://www.google.com";
		
		if(getIntent().getSerializableExtra(TremorConstants.USGS_URL_KEY) != null) {
			url = (String) getIntent().getSerializableExtra(TremorConstants.USGS_URL_KEY);
		} else {
			Toast toast = Toast.makeText(getApplicationContext(), R.string.url_error_message, Toast.LENGTH_LONG);
			toast.show();
		}
		
		webView.setWebViewClient(new LocalWebViewClient());
		
		webView.loadUrl(url);
	}
	
	private class LocalWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
}

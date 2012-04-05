/**
 * 
 */
package com.captech.mehalso.tremorfeed.remote;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.captech.mehalso.tremorfeed.TremorConstants;
import com.captech.mehalso.tremorfeed.pojo.TremorRecord;

/**
 * @author mmehalso
 *
 */
public class HttpUSGSTremorRetrieverImpl implements TremorRetriever {

	private String httpUrl;
	private HttpClient httpClient;
	
	public HttpUSGSTremorRetrieverImpl(String httpUrl) {
		setHttpUrl(httpUrl);
		this.httpClient = AndroidHttpClient.newInstance(TremorConstants.HTTP_USER_AGENT);	//use default settings.
	}
	
	/* (non-Javadoc)
	 * @see com.captech.mehalso.tremorfeed.remote.TremorRetriever#getLatestEarthquakeData()
	 */
	public Collection<TremorRecord> getLatestEarthquakeData() {
		Log.v(TremorConstants.LOG_TAG, "Enter getLatestEarthquakeData()...");
		
		if(getHttpUrl() == null) {
			throw new RuntimeException("No URL provided for USGS RSS Feed. Unable to continue.");
		}
		
		try {
			Collection<TremorRecord> records;
		
			HttpGet request = new HttpGet(getHttpUrl());
			HttpResponse response = httpClient.execute(request);
			
			if(response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200) {
				records = parseResponse(response);
			} else {
				Log.e(TremorConstants.LOG_TAG, "Received non-success HTTP status code while retrieving " +
						"earthquake data. Status code [" + 
						(response.getStatusLine() == null ? "null" : response.getStatusLine().getStatusCode()));
				
				throw new RuntimeException("Received non-success HTTP status code while retrieving " +
						"earthquake data. Status code [" + 
						(response.getStatusLine() == null ? "null" : response.getStatusLine().getStatusCode()));
			}
			
			Log.v(TremorConstants.LOG_TAG, "Leave getLatestEarthquakeData()... Records [" + 
					(records == null ? "null" : records.size()) +"].");
			
			return records;
		} catch(Throwable t) {
			Log.e(TremorConstants.LOG_TAG, "Error obtaining earthquake data from URL [" + getHttpUrl() + "].", t);
			
			throw new RuntimeException("Error obtaining earthquake data from URL [" + getHttpUrl() + "].", t);
		}
	}

	/**
	 * Parses the response XML into tremor records.
	 * @param successResponse
	 * @return
	 * @throws XmlPullParserException 
	 */
	private Collection<TremorRecord> parseResponse(HttpResponse successResponse) 
											throws XmlPullParserException, IOException, NumberFormatException, ParseException {
		ArrayList<TremorRecord> tremors = new ArrayList<TremorRecord>();
		
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
		
		parser.setInput(successResponse.getEntity().getContent(), null);
		
		int eventType = parser.getEventType();
		
		TremorRecord record = null;
		SimpleDateFormat sdf = new SimpleDateFormat(TremorConstants.USGS_DATE_FORMAT);
		
		while(eventType != XmlPullParser.END_DOCUMENT) {
			switch(eventType) {
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				Log.v(TremorConstants.LOG_TAG, "XML Parsing---> Start Tag [" + tagName + "]");
				
				if(TremorConstants.XML_TAG_ITEM.equals(tagName)) {
					record = new TremorRecord();
				} else if(record != null) {
					String val = parser.getText();
					Log.v(TremorConstants.LOG_TAG, "XML Parsing---> Value [" + val + "]");
					
					if(TremorConstants.XML_TAG_TITLE.equals(tagName)) {
						record.setTitle(val);
					} else if(TremorConstants.XML_TAG_DEPTH.equals(tagName)) {
						record.setDepthKm(Double.valueOf(val));
					} else if(TremorConstants.XML_TAG_DESC.equals(tagName)) {
						record.setDescriptionHtml(val);
					} else if(TremorConstants.XML_TAG_LAT.equals(tagName)) {
						record.setLatitude(Double.valueOf(val));
					} else if(TremorConstants.XML_TAG_LONG.equals(tagName)) {
						record.setLongitude(Double.valueOf(val));
					} else if(TremorConstants.XML_TAG_PUBDATE.equals(tagName)) {
						record.setPubDate(sdf.parse(val));
					} else if(TremorConstants.XML_TAG_REGION.equals(tagName)) {
						record.setRegion(val);
					} else if(TremorConstants.XML_TAG_SECONDS.equals(tagName)) {
						record.setEventDateUtc(new Date(Long.parseLong(val)));
					} else if(TremorConstants.XML_TAG_SUBJECT.equals(tagName)) {
						record.setFloorMag(Integer.valueOf(val));
					}
				}
				
				break;
				
			case XmlPullParser.END_TAG:
				tagName = parser.getName();
				if(TremorConstants.XML_TAG_ITEM.equals(tagName)) {
					Log.v(TremorConstants.LOG_TAG, "Finished parsing record, adding to collection. " +
							"Record [" + record.toString() + "].");
					
					tremors.add(record);
					record = null;
				}
				
				break;
			}
			
			eventType = parser.next();
		}
		
		return tremors;
	}
	
	/**
	 * @return the httpUrl
	 */
	public String getHttpUrl() {
		return httpUrl;
	}

	/**
	 * @param httpUrl the httpUrl to set
	 */
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

}

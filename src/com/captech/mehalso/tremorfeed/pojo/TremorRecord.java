/**
 * 
 */
package com.captech.mehalso.tremorfeed.pojo;

import java.util.Date;

/**
 * @author mmehalso
 *
 * Example XML: 
 * 
	<title>3.5 - 17.0 km (10.6 mi) NNE of Indio, CA</title>
	<description>
	<![CDATA[
	<img src="http://earthquake.usgs.gov/eqcenter/shakemap/thumbs/shakemap_sc_11088434.jpg" width="100" align="left" hspace="10"/><p>Date: Wed, 04 Apr 2012 01:12:09 UTC<br/>Lat/Lon: 33.8627/-116.169<br/>Depth: 1.26</p>
	]]>
	</description>
	<link>
	http://earthquake.usgs.gov/eqcenter/shakemap/sc/shake/11088434/
	</link>
	<pubDate>Wed, 04 Apr 2012 02:18:42 +0000</pubDate>
	<geo:lat>33.8627</geo:lat>
	<geo:long>-116.169</geo:long>
	<dc:subject>3</dc:subject>
	<eq:seconds>1333501929</eq:seconds>
	<eq:depth>1.26</eq:depth>
	<eq:region>sc</eq:region>
	
 */
public class TremorRecord {

	private String title;
	private String descriptionHtml;
	private String url;
	private Date pubDate;
	private Number latitude;
	private Number longitude;
	private Number floorMag;	//subject
	private Date eventDateUtc;	//seconds
	private Number depthKm;
	private String region;
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the descriptionHtml
	 */
	public String getDescriptionHtml() {
		return descriptionHtml;
	}
	/**
	 * @param descriptionHtml the descriptionHtml to set
	 */
	public void setDescriptionHtml(String descriptionHtml) {
		this.descriptionHtml = descriptionHtml;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the pubDate
	 */
	public Date getPubDate() {
		return pubDate;
	}
	/**
	 * @param pubDate the pubDate to set
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	/**
	 * @return the latitude
	 */
	public Number getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Number latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public Number getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Number longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the floorMag
	 */
	public Number getFloorMag() {
		return floorMag;
	}
	/**
	 * @param floorMag the floorMag to set
	 */
	public void setFloorMag(Number floorMag) {
		this.floorMag = floorMag;
	}
	/**
	 * @return the eventDateUtc
	 */
	public Date getEventDateUtc() {
		return eventDateUtc;
	}
	/**
	 * @param eventDateUtc the eventDateUtc to set
	 */
	public void setEventDateUtc(Date eventDateUtc) {
		this.eventDateUtc = eventDateUtc;
	}
	/**
	 * @return the depthKm
	 */
	public Number getDepthKm() {
		return depthKm;
	}
	/**
	 * @param depthKm the depthKm to set
	 */
	public void setDepthKm(Number depthKm) {
		this.depthKm = depthKm;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TremorRecord [title=" + title + ", descriptionHtml="
				+ descriptionHtml + ", url=" + url + ", pubDate=" + pubDate
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", floorMag=" + floorMag + ", eventDateUtc=" + eventDateUtc
				+ ", depthKm=" + depthKm + ", region=" + region + "]";
	}
	
	
}

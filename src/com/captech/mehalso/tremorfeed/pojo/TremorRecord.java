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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TremorRecord other = (TremorRecord) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	/**
	 * Used when we have to merge existing records together.  Copy newer records values. 
	 * @param otherRecord
	 */
	public void copyValues(TremorRecord otherRecord) {
		this.setDepthKm(otherRecord.getDepthKm());
		this.setDescriptionHtml(otherRecord.getDescriptionHtml());
		this.setEventDateUtc(otherRecord.getEventDateUtc());
		this.setFloorMag(otherRecord.getFloorMag());
		this.setLatitude(otherRecord.getLatitude());
		this.setLongitude(otherRecord.getLongitude());
		this.setPubDate(otherRecord.getPubDate());
		this.setRegion(otherRecord.getRegion());
		this.setTitle(otherRecord.getTitle());
	}
}

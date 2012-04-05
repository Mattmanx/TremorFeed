/**
 * 
 */
package com.captech.mehalso.tremorfeed.remote;

import java.util.Collection;

import com.captech.mehalso.tremorfeed.pojo.TremorRecord;

/**
 * @author mmehalso
 *
 */
public interface TremorRetriever {
	public Collection<TremorRecord> getLatestEarthquakeData();
}

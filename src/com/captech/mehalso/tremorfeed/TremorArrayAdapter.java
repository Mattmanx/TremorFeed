/**
 * 
 */
package com.captech.mehalso.tremorfeed;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.captech.mehalso.tremorfeed.pojo.TremorRecord;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author mmehalso
 *
 */
public class TremorArrayAdapter extends ArrayAdapter<TremorRecord> {
	private final Activity context;
	private List<TremorRecord> deletedRecords = new ArrayList<TremorRecord>();
	
	Object syncLock = new Object();
	
	static class ViewHolder {
		//This should match our row item layout.
		public TextView text;
	}
	
	/** 
	 * Constructor... We want to pull data asynchronously, so empty for now.
	 * @param context
	 */
	public TremorArrayAdapter(Activity context) {
		super(context, R.layout.row_layout);
		this.context = context;
	}
	
	/**
	 * Convenience method to sort tremor records by date after update.
	 */
	public void sortRecordsByDate() {
		//Need to synchronize because we depend on index of a given record during the merge process			
		this.sort(new Comparator<TremorRecord>() {
			public int compare(TremorRecord lhs, TremorRecord rhs) {
				if(lhs.getEventDateUtc().after(rhs.getEventDateUtc())) {
					return 1;
				} else if(lhs.getEventDateUtc().equals(rhs.getEventDateUtc())) {
					return 0;
				} else {
					return -1;
				}
			}
		});
	}
	
	/**
	 * Custom getView for row layout.  Uses tagging pattern for performance. 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		
		if(rowView == null) {
			rowView = context.getLayoutInflater().inflate(R.layout.row_layout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.textView1);
			
			//TODO: Update this as I get my row layout figured out. 
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		//TODO: Update this as I get my row layout figured out.
		TremorRecord record = getItem(position);
		holder.text.setText(record.getTitle());
		
		//TODO: Decorate based on magnitude. 
		if(record.getFloorMag().intValue() >= 5 && 
				record.getFloorMag().intValue() < 7) {
			rowView.setBackgroundColor(android.R.color.holo_red_light);
		} else if(record.getFloorMag().intValue() >= 7){
			rowView.setBackgroundColor(android.R.color.holo_red_dark);
		} else {
			rowView.setBackgroundColor(android.R.color.black); 
		}
		
		//TODO: Add on-click listener
		
		return rowView;
	}
	
	/**
	 * Intercepting add() operation so that we can check whether item already exists or
	 * has been deleted before adding to collection.
	 */
	@Override
	public void add (TremorRecord record) {
		//No need to synchronize here, because all data is added on the UI thread.
		int index = 0;
		if(deletedRecords.contains(record)){
			//item already deleted by user, do not re-add.
			Log.i(TremorConstants.LOG_TAG, "Attempt to add record explicitly deleted by user. Ignoring.  " +
					"Record URL [" + record.getUrl() + "]");
		} else if((index = this.getPosition(record)) > -1) {
			//item already exists in the list, do not re-add. Copy any updated data that might exist.  
			Log.i(TremorConstants.LOG_TAG, "Attempt to add record already in list. Merging record URL [" + 
					record.getUrl() + "].");
			
			this.getItem(index).copyValues(record);
		} else {
			Log.i(TremorConstants.LOG_TAG, "Adding record with URL [" + record.getUrl() + "].");
			super.add(record);
		}
	}
	
	/**
	 * Intercepting clear() operation to remove from deleted items as well.
	 */
	@Override
	public void clear () {
		deletedRecords.clear();
		super.clear();
	}
	
	/**
	 * Explicit deletion of a record per user action.
	 * @param record
	 */
	public void delete(TremorRecord record) {
		deletedRecords.add(record);
		super.remove(record);
	}
}

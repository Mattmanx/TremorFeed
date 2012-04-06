/**
 * 
 */
package com.captech.mehalso.tremorfeed;

import com.captech.mehalso.tremorfeed.pojo.TremorRecord;

import android.app.Activity;
import android.content.Context;
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
		//TODO: Add on-click listener
		
		return rowView;
	}
}

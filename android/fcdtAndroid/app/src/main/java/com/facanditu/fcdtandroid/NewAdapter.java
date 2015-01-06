package com.facanditu.fcdtandroid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unchecked")
public class NewAdapter extends BaseExpandableListAdapter {

	public ArrayList<MenuItem> groupItem,tempChild;
	public ArrayList<Object> Childtem = new ArrayList<Object>();
	public LayoutInflater minflater;
	public Activity activity;
	private final Context context;

	public NewAdapter(Context context, ArrayList<MenuItem> grList, ArrayList<Object> childItem) {
		this.context = context;
        this.minflater = LayoutInflater.from(context);;
		this.groupItem = grList;
		this.Childtem = childItem;
	}

	public void setInflater(LayoutInflater mInflater, Activity act) {
		this.minflater = mInflater;
		activity = act;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		tempChild = (ArrayList<MenuItem>) Childtem.get(groupPosition);
        if(tempChild!=null && tempChild.size()>0){
            if (convertView == null) {
                convertView = minflater.inflate(R.layout.one_menu_item,parent,false);
            }
            TextView title = (TextView)convertView.findViewById(R.id.menu_item_text);
            ImageView image = (ImageView)convertView.findViewById(R.id.menu_item_icon);
            MenuItem menuItem = tempChild.get(childPosition);
            title.setText(menuItem.getTitle());
            image.setImageResource(menuItem.getIcon());
            convertView.setTag(menuItem.getTagValue());
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));

        }

		return convertView;
	}

	

	@Override
	public int getChildrenCount(int groupPosition) {
        Object children = Childtem.get(groupPosition);
        if(children!=null){
            return ((ArrayList<String>)children).size();
        }

		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return groupItem.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			//convertView = new TextView(context);
            convertView = minflater.inflate(R.layout.one_menu_item,parent,false);
		}
        TextView title = (TextView)convertView.findViewById(R.id.menu_item_text);
        ImageView image = (ImageView)convertView.findViewById(R.id.menu_item_icon);
        MenuItem menuItem = groupItem.get(groupPosition);
        title.setText(menuItem.getTitle());
        image.setImageResource(menuItem.getIcon());
		convertView.setTag(menuItem.getTagValue());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}

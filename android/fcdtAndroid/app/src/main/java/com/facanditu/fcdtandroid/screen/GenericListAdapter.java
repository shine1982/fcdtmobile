package com.facanditu.fcdtandroid.screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.GenericItemObject;

import java.util.List;

/**
 * Created by fengqin on 15/1/7.
 */
public class GenericListAdapter extends ArrayAdapter<GenericItemObject> {

    private Context context;
    private List<GenericItemObject> list;

    public GenericListAdapter(Context context, int resource, List<GenericItemObject> list) {
        super(context, resource, list);
        this.context = context;
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = convertView;
        if (v == null) {
            v = inflater.inflate(R.layout.oneline_generic, parent, false);
        }
        TextView textView = (TextView)v.findViewById(R.id.textView1);
        textView.setText(list.get(position).getValue());
        v.setTag(list.get(position).getKey());

        return v;
    }
}

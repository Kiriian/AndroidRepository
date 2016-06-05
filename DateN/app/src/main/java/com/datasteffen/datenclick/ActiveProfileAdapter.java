package com.datasteffen.datenclick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by steffen on 05-06-2016.
 */
public class ActiveProfileAdapter extends ArrayAdapter<ActiveProfile> {

    public ActiveProfileAdapter(Context context, List<ActiveProfile> activeProfile) {
        super(context,R.layout.rowlayout,  activeProfile);
    }
    TextView tv1;
    TextView tv2;
    TextView tv3;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        ActiveProfile activeProfile = getItem(position);

        View customView = layoutInflater.inflate(R.layout.rowlayout, parent, false);
        ActiveProfile activeProfile1 = getItem(position);


        tv1 = (TextView) customView.findViewById(R.id.textview1);
        tv2 = (TextView) customView.findViewById(R.id.textview2);
        tv3 = (TextView) customView.findViewById(R.id.textview3);


        tv1.setText(activeProfile.getName());
        tv2.setText(String.valueOf(activeProfile.getLat()));
        tv3.setText(String.valueOf(activeProfile.getLon()));

        return customView;
    }
}

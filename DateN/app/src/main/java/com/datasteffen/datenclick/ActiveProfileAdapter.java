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
    TextView tv4;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        ActiveProfile activeProfile = getItem(position);

        View customView = layoutInflater.inflate(R.layout.rowlayout, parent, false);
        ActiveProfile activeProfile1 = getItem(position);


        tv1 = (TextView) customView.findViewById(R.id.textview1);
        tv2 = (TextView) customView.findViewById(R.id.textview2);
        tv3 = (TextView) customView.findViewById(R.id.textview3);
        tv4 = (TextView) customView.findViewById(R.id.textview4);


        tv1.setText("Name: "+ activeProfile.getName());
        tv2.setText("Email: " +activeProfile.getEmail());
        tv3.setText("Latitude: "+ activeProfile.getLon());
        tv4.setText("longitude: " + activeProfile.getLon());

        return customView;
    }
}

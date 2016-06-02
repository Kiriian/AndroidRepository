package com.datasteffen.datenclick;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steffen on 02-06-2016.
 */
public class DaterList extends Fragment {

    ListView tv;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daterlist_fragment,container,false);


//        Intent i = new Intent();
//        List<ActiveProfile> activeProfiles = new ArrayList<>();
//
//
//        activeProfiles = (ArrayList<ActiveProfile>) getActivity().getIntent().getBundleExtra("Daters").getSerializable("ListDaters");
//        tv = (ListView) view.findViewById(R.id.listviewid);
//
//        for (ActiveProfile d :activeProfiles) {
//
//            //   tv.append( d.toString());
//        }
        return  view;



    }
}

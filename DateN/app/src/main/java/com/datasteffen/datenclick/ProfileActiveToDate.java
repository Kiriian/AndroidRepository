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
import java.util.concurrent.ExecutionException;

/**
 * Created by steffen on 05-06-2016.
 */
public class ProfileActiveToDate extends Fragment {
    private List<ActiveProfile> activeProfileList = new ArrayList<>();
    ListView tv;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daterlist_fragment,container,false);


        tv = (ListView) view.findViewById(R.id.listviewid);

        return  view;
    }
}



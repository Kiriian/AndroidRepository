package com.datasteffen.datenclick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by steffen on 05-06-2016.
 */
public class ActiveProfileAdapter extends ArrayAdapter<ActiveProfile> {

    public ActiveProfileAdapter(Context context, List<ActiveProfile> activeProfile) {
        super(context,R.layout.rowlayout,  activeProfile);
    }
    ImageView im;
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

        im = (ImageView) customView.findViewById(R.id.imageview1);
        tv1 = (TextView) customView.findViewById(R.id.textview1);
        tv2 = (TextView) customView.findViewById(R.id.textview2);
        tv3 = (TextView) customView.findViewById(R.id.textview3);
        tv4 = (TextView) customView.findViewById(R.id.textview4);

        im.setImageBitmap(bytesToBitmap(activeProfile.getImgbytes()));
        tv1.setText("Name: "+ activeProfile.getName());
        tv2.setText("Email: " +activeProfile.getEmail());
        tv3.setText("Latitude: "+ activeProfile.getLon());
        tv4.setText("longitude: " + activeProfile.getLon());

        return customView;
    }

    public static Bitmap bytesToBitmap (byte[] imageBytes)
    {
        byte[] byte1 = imageBytes;
        Bitmap bitmap = BitmapFactory.decodeByteArray(byte1, 0, imageBytes.length);
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        matrix.postRotate(90);
        matrix.postTranslate(bitmap.getWidth(), 0);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap resize = Bitmap.createScaledBitmap(bitmap,100,100,false);

        return resize;
    }
}

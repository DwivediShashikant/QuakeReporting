package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shashikant on 3/21/2017.
 */

public class quakeAdapter extends ArrayAdapter<quake> {
  private static final String LOG_TAG = quakeAdapter.class.getSimpleName();
  public quakeAdapter(Activity context ,ArrayList<quake> list, int resource){
      super(context,0,list);

  }
    @Override
    public View getView(int position, View convertView , ViewGroup parent){
        // check if the existing view is being is used otherwise inflate it, get the context
        // of the activity and inflate the
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.quake_description, parent, false);
        }

        // creating object of type quake, getItem is a function that will find the appropriate View
        // at runtime when user scrolls the list, each listItem is a View of type quake_description
        quake currentQuake = getItem(position);
        updateUI(listItemView,currentQuake);

        return listItemView;
    }
     private static void updateUI(View listItemView, quake currentQuake){
         TextView magnitudeTextView = (TextView)listItemView.findViewById(R.id.magnitude_id);
         String magnitude = Double.toString(currentQuake.getMagnitude());
         magnitudeTextView.setText(magnitude);

         TextView placeTextView = (TextView)listItemView.findViewById(R.id.place_id);
         placeTextView.setText(currentQuake.getPlace());

         TextView timeTextView = (TextView)listItemView.findViewById(R.id.time_id);
         String time = Long.toString(currentQuake.getTime());
         timeTextView.setText(time);
     }

}

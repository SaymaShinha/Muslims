package com.mys3soft.muslims.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mys3soft.muslims.Models.World_Cities;
import com.mys3soft.muslims.R;

import java.util.List;

public class PrayerLocationAdapter extends ArrayAdapter<World_Cities> {
    public PrayerLocationAdapter(Context context, List<World_Cities> world_citiesList) {
        super(context, R.layout.single_location_text_layout, world_citiesList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_location_text_layout, parent, false);
        }

        World_Cities world_location = getItem(position);

        TextView location = (TextView) convertView.findViewById(R.id.single_location_text_id);
        String s = world_location.getCity()+"."+world_location.getCountry();
        location.setText(s);

        return convertView;
    }
}

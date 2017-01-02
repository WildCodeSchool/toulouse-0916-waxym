package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.wildcodeschool.haa.waxym.model.ActivityItemModel;

/**
 * Created by apprenti on 26/12/16.
 */

public class CustomDayAdapter extends BaseAdapter {
    private Context context;
    private final String[] days;

    public CustomDayAdapter(Context context, String[] days) {
        this.context = context;
        this.days = days;
    }


    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null){
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.activity_day,null);
            TextView textView = (TextView) gridView.findViewById(R.id.activity_day_text);
            textView.setText(days[position]);
        }else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}

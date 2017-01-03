package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;
import fr.wildcodeschool.haa.waxym.model.GridDateModel;

/**
 * Created by apprenti on 26/12/16.
 */

public class CustomDayAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<GridDateModel> days;
    private DBHandler mDBHandler;
    private ArrayList<DayStuffModel> dayEvents;


    public CustomDayAdapter(Context context, ArrayList<GridDateModel> days) {
        this.context = context;
        this.days = days;
    }


    @Override
    public int getCount() {
        return days.size();
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
        mDBHandler = new DBHandler(context);

        try{
            this.dayEvents = this.mDBHandler.getDayEvents(1,days.get(0).getDate());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        DayStuffModel thisDay = new DayStuffModel();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (convertView == null){
            gridView = inflater.inflate(R.layout.activity_day,null);
            TextView halfDay = (TextView) gridView.findViewById(R.id.activity_day_text);
            if(position == 0){
                if(this.dayEvents.size() > 0){
                    halfDay.setText(this.dayEvents.get(0).getContractNumber() + " " + this.dayEvents.get(0).getActivity());
                    halfDay.setBackgroundColor(Color.parseColor(this.dayEvents.get(0).getActivityColor()));
                }
                else {
                    halfDay.setText("Matin");
                }
            }
            else if (position == 1){
                if(this.dayEvents.size() > 1){
                    halfDay.setText(this.dayEvents.get(1).getContractNumber() + " " + this.dayEvents.get(1).getActivity());
                    halfDay.setBackgroundColor(Color.parseColor(this.dayEvents.get(1).getActivityColor()));

                }
                else {
                    halfDay.setText("Après-midi");
                }
            }
        }
        else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}

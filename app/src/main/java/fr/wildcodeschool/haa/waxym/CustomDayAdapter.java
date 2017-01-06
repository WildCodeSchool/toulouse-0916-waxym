package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(100);
            if(position == 0){
                halfDay.setText("Matin");
                halfDay.setTextColor(Color.BLACK);
                if(this.dayEvents.size() > 0){
                    halfDay.setText(this.dayEvents.get(0).getContractNumber() + " " + this.dayEvents.get(0).getActivity());
                    gd.setColor(Color.parseColor(this.dayEvents.get(0).getActivityColor()));
                    gd.setStroke(100, Color.parseColor("#FFFFFF"));
                    halfDay.setBackgroundDrawable(gd);
                }

            }
            else if (position == 1){
                halfDay.setText("Après-midi");
                halfDay.setTextColor(Color.BLACK);
                if(this.dayEvents.size() > 1){
                    halfDay.setText(this.dayEvents.get(1).getContractNumber() + " " + this.dayEvents.get(1).getActivity());
                    gd.setColor(Color.parseColor(this.dayEvents.get(1).getActivityColor()));
                    gd.setStroke(100, Color.parseColor("#FFFFFF"));
                    halfDay.setBackgroundDrawable(gd);


                }

            }
        }
        else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}

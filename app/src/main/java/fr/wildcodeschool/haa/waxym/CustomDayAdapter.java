package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.ActivityItemModel;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;
import fr.wildcodeschool.haa.waxym.model.GridDateModel;

/**
 * Created by apprenti on 26/12/16.
 */

public class CustomDayAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<GridDateModel> days;
    private DBHandler mDBHandler;
    private ArrayList<DayStuffModel> listProvisoire;


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
            listProvisoire = this.mDBHandler.getTwoMonthEvents(1,days.get(0).getDate());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        DayStuffModel thisDay = new DayStuffModel();
        Calendar calendarCompare = Calendar.getInstance();
        calendarCompare.setTime(days.get(0).getDate());
        Calendar calendarToCompare = Calendar.getInstance();
        ArrayList<DayStuffModel> dayEvent = new ArrayList<>();
        for (int i = 0; i < listProvisoire.size(); i ++){
            calendarToCompare.setTime(listProvisoire.get(i).getDate());
            if(calendarToCompare.get(Calendar.DAY_OF_MONTH) == calendarCompare.get(Calendar.DAY_OF_MONTH)){
                dayEvent.add(listProvisoire.get(i));
            }

        }
      /*  for(int i = 0; i <) {
            if () {

            }
        }*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (convertView == null){
            gridView = inflater.inflate(R.layout.activity_day,null);
            TextView halfDay = (TextView) gridView.findViewById(R.id.activity_day_text);
            if(position == 0){
                if(dayEvent.size() > 0){
                    halfDay.setText(dayEvent.get(0).getContractNumber() + " " + dayEvent.get(0).getActivity());
                    halfDay.setBackgroundColor(Color.parseColor(dayEvent.get(0).getActivityColor()));
                }
                else {
                    halfDay.setText("Matin");
                }
            }
            else if (position == 1){
                if(dayEvent.size() > 1){
                    halfDay.setText(dayEvent.get(1).getContractNumber() + " " + dayEvent.get(1).getActivity());
                    halfDay.setBackgroundColor(Color.parseColor(dayEvent.get(1).getActivityColor()));

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

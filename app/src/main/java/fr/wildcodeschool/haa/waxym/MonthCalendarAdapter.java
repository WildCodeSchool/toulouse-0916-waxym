package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;

/**
 *
 */

public class MonthCalendarAdapter extends ArrayAdapter<GridDateModel>
{
    // days with events
    private ArrayList<DayStuffModel> eventDays;
    private DBHandler mDBHandler;
    // for view inflation
    private LayoutInflater inflater;
    private Context context;

    public MonthCalendarAdapter(Context context, ArrayList<GridDateModel> days)
    {
        super(context, R.layout.calendar_day2, days);
        this.inflater = LayoutInflater.from(context);
        this.context = context;

    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {

        // day in question
        Date date = getItem(position).getDate();
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();

        mDBHandler = new DBHandler(getContext());
        try {
            eventDays = this.mDBHandler.getEvents(1, date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // today
        Date today = new Date();

        // inflate item if it does not exist yet
        if (view == null)
            view = inflater.inflate(R.layout.calendar_day2, parent, false);
        //get views
        TextView dayDateView = (TextView)view.findViewById(R.id.day_date);
        TextView matinView = (TextView) view.findViewById(R.id.image_matin);
        TextView apresMidiView = (TextView)view.findViewById(R.id.image_apres_midi);



        // if this day has an event, specify event image

        if (eventDays != null) {
            for (DayStuffModel eventDate : eventDays) {
                if (eventDate.getDate().getDate() == day &&
                        eventDate.getDate().getMonth() == month &&
                        eventDate.getDate().getYear() == year) {
                    if (eventDate.getAfternoon() == 1) {
                        apresMidiView.setText(eventDate.getActivity());
                        apresMidiView.setBackgroundColor(Color.parseColor(eventDate.getActivityColor()));
                    } else {
                        matinView.setText(eventDate.getActivity());
                        matinView.setBackgroundColor(Color.parseColor(eventDate.getActivityColor()));


                    }
                }
            }
        }

        // clear styling
        dayDateView.setTypeface(null, Typeface.NORMAL);
        dayDateView.setTextColor(Color.BLACK);

        if (month != today.getMonth() || year != today.getYear())
        {
            // if this day is outside current month, grey it out
            dayDateView.setTextColor(context.getResources().getColor(R.color.greyed_out));
        }
        else if (day == today.getDate())
        {
            // if it is today, set it to blue/bold
            dayDateView.setTypeface(null, Typeface.BOLD);
            dayDateView.setTextColor(context.getResources().getColor(R.color.today));
        }

        // set text
        dayDateView.setText(String.valueOf(date.getDate()));
        //set row height
        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT,200));
        return view;
    }

}
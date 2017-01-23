package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;
import fr.wildcodeschool.haa.waxym.model.GridDateModel;

/**
 * adapter for gridview month mode
 */

public class MonthAdapter extends ArrayAdapter<GridDateModel> {
    // days with events
    private ArrayList<DayStuffModel> eventDays;
    private DBHandler mDBHandler;
    // for view inflation
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<GridDateModel> days;
    private MainActivityCallBackInterface callback;
    private StatusSingleton status;
    private int height;

    public MonthAdapter(Context context, ArrayList<GridDateModel> days) {
        super(context, R.layout.calendar_day, days);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.days = days;

        mDBHandler = new DBHandler(getContext());
        try {
            eventDays = this.mDBHandler.getMonthEvents(StatusSingleton.getInstance().getCurrentUserId(), days.get(15).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.height = size.y;

    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        // day in question
        Date date = getItem(position).getDate();
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();


        // today
        Date today = new Date();

        // inflate item if it does not exist yet
        if (view == null)
            view = inflater.inflate(R.layout.calendar_day, parent, false);
        //get views
        TextView dayDateView = (TextView) view.findViewById(R.id.day_date);
        TextView matinView = (TextView) view.findViewById(R.id.image_matin);
        TextView apresMidiView = (TextView) view.findViewById(R.id.image_apres_midi);


        // if this day has an event, specify event view
        if (eventDays != null) {

            for (DayStuffModel eventDate : eventDays) {
                if (eventDate.getDate().getMonth() == days.get(15).getDate().getMonth()) {
                    if (eventDate.getDate().getDate() == day &&
                            eventDate.getDate().getMonth() == month &&
                            eventDate.getDate().getYear() == year) {

                        if (eventDate.getAfternoon() == 1) {
                            if (eventDate.getActivityId().equals(Constants.CLEAR_ACTIVITY)){
                                apresMidiView.setBackgroundColor(android.R.color.white);
                            }else
                            apresMidiView.setBackgroundColor(Color.parseColor("#" + eventDate.getActivityColor()));
                        } else {
                            if (eventDate.getActivityId().equals(Constants.CLEAR_ACTIVITY)){
                                apresMidiView.setBackgroundColor(android.R.color.white);
                            }else
                            matinView.setBackgroundColor(Color.parseColor("#" + eventDate.getActivityColor()));


                        }
                    }
                }
            }
        }

        // clear styling
        dayDateView.setTypeface(null, Typeface.NORMAL);
        dayDateView.setTextColor(Color.BLACK);

        if (month == days.get(15).getDate().getMonth()) {
            dayDateView.setTextColor(Color.BLACK);
            if (day == today.getDate() && month == today.getMonth()) {
                // if it is today, set it to blue/bold
                dayDateView.setTypeface(null, Typeface.BOLD);
                dayDateView.setTextColor(context.getResources().getColor(R.color.today));
            }
        } else {
            // if this day is outside current month, hide it
            dayDateView.setTextColor(Color.parseColor("#00FF0000"));
            // today in blue

        }


        // set text
        dayDateView.setText(String.valueOf(date.getDate()));
        //set row height
        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, (int) (height / 8.5)));
        status = StatusSingleton.getInstance();
        if (!status.isEditMode()) {
            Calendar currentDay = Calendar.getInstance();
            currentDay.setTime(days.get(position).getDate());
            if (currentDay.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && currentDay.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && month == days.get(15).getDate().getMonth()) {
                final View finalView = view;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(days.get(position).getDate());
                        if (status.getLastMonthPosition() != Constants.TOTAL_SLIDES / 2) {
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                        }
                        ;
                        status.setCurrentDate(calendar);
                        callback.launchDayView();
                    }
                });
            }
        }
        return view;
    }

    public void setCallback(MainActivityCallBackInterface callback) {

        this.callback = callback;
    }

}
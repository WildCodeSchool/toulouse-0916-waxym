package fr.wildcodeschool.haa.waxym;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by tuffery on 14/12/16.
 */

public class CalendarFragment extends Fragment implements CalendarInterface {

    public static final String POSITION_KEY = "Month";
    private int position;
    private ArrayList<GridDateModel> cells ;
    public static boolean isMenuCreated = false;
    public static boolean isEditMode = false;
    private boolean isDoneOnce = false;
    private MultiSelectMenuFragment fragment;
    private TextView txtDate;
    private GridView grid;
    private float startX = 0;
    private float startY = 0;
    private LinearLayout header;

    int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};
    // seasons' rainbow
    int[] rainbow = new int[] {
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };
    // for logging
    private static final String LOGTAG = "Calendar View";

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    // date format
    private String dateFormat;

    // current displayed month
    public static Calendar currentDate = Calendar.getInstance();


    public static CalendarFragment newInstance(Bundle args) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        position = getArguments().getInt(POSITION_KEY);

        View root = inflater.inflate(R.layout.calendar_layout_fragment, container, false);
        grid = (GridView)root.findViewById(R.id.calendar_grid);
        header = (LinearLayout)getActivity().findViewById(R.id.calendar_header);

        txtDate = (TextView)getActivity().findViewById(R.id.calendar_date_display);

        updateCalendar(root.getContext());
        return root;
    }

    public void updateCalendar(final Context context)
    {

        this.cells = new ArrayList<>();
        final Calendar calendar = (Calendar)currentDate.clone();
        calendar.add(Calendar.MONTH, position);
        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        final int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(new GridDateModel(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        final MonthCalendarAdapter calendarAdapter = new MonthCalendarAdapter(context,cells);
        grid.setAdapter(calendarAdapter);

        // multiselect
        //grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        //on touch
        if(this.isEditMode) {

            grid.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    float initialY = motionEvent.getY(), initialX = motionEvent.getX();
                    int i = grid.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                    if (i >= 0 && i < 42) {

                        if (motionEvent.getActionMasked() == MotionEvent.ACTION_UP){
                            isDoneOnce = false;
                        }
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            changeSateAndResetPos(cells.get(i),i,initialX,initialY);
                            if(!isMenuCreated) {
                                launchMultiSelectMenu(context);
                                isMenuCreated =true;
                            }
                            if (!isDoneOnce) {
                                sendDataToFragment(i, cells.get(i));
                                isDoneOnce = true;
                            }

                            return true;

                            //on swipe
                        }else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                            if (checkDistance(startX, startY, motionEvent.getX(), motionEvent.getY(), grid.getChildAt(i).getWidth(),grid.getChildAt(i).getHeight())) {
                                changeSateAndResetPos(cells.get(i),i,initialX,initialY);
                                sendDataToFragment(i,cells.get(i));

                            }

                        }

                    }

                    return true;
                }
            });
        }
        else{
            grid.setOnTouchListener(null);

            // à faire ( swipe changer mois
        }
        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));

    }
    public boolean checkDistance(float x, float y, float deltaX, float deltaY, int width, int height) {
        float diffX = 0;
        float diffY = 0;
        diffX = Math.abs(deltaX - x );
        diffY = Math.abs(deltaY - y);
        if(diffX>width-width*0.1 || diffY >height-height *0.1)
            return true;
        else
            return false;


    }
    // change state of cell at position and set associated background and reset start positions
    public void changeSateAndResetPos(GridDateModel selectedDay, int position, float x, float y){
        startX = x;
        startY = y;
        if (!selectedDay.isState()){
            grid.getChildAt(position).setBackgroundResource(R.color.SELECTING_COLOR);
            selectedDay.setState(true);

        }else {
            grid.getChildAt(position).setBackgroundResource(0);
            selectedDay.setState(false);

        }

    }

    public void sendDataToFragment(int position, GridDateModel gridDate){
        if(this.fragment != null) {
            if (gridDate.isState()) {
                try {
                    ((AdapterCallbackInterface) this.fragment).passCheckedDay(cells.get(position).getDate(),position, true);
                } catch (ClassCastException e) {

                }
            } else {
                try {
                    ((AdapterCallbackInterface) this.fragment).passCheckedDay(cells.get(position).getDate(),position, false);
                } catch (ClassCastException e) {

                }
            }
        }
    }
    public void launchMultiSelectMenu(Context context){


        final Activity activity = (MainActivity)context;
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        this.fragment = new MultiSelectMenuFragment();
        ft.add(R.id.list_fragment_container,fragment).commit();


    }


}
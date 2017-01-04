package fr.wildcodeschool.haa.waxym;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import fr.wildcodeschool.haa.waxym.model.GridDateModel;


/**
 * Created by tuffery on 14/12/16.
 */

public class CalendarFragment extends Fragment  {

    private int position;
    private ArrayList<GridDateModel> cells ;
    private boolean isDoneOnce = false;
    private MultiSelectMenuFragment fragment;
    private TextView txtDate;
    private GridView grid;
    private LinearLayout header;
    private int monthBeginningCell;
    Calendar dayCalendar;

    View root;
    Calendar calendar;
    int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};
    // seasons' rainbow
    int[] rainbow = new int[] {
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // current displayed month
    public static Calendar currentDate;


    public static CalendarFragment newInstance(Bundle args) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentDate = Calendar.getInstance();
        position = getArguments().getInt(Constants.POSITION_KEY);

       this.root = inflater.inflate(R.layout.calendar_layout_fragment, container, false);
        grid = (GridView)root.findViewById(R.id.calendar_grid);
        header = (LinearLayout)getActivity().findViewById(R.id.calendar_header);

        updateCalendar(root.getContext());
        return root;
    }

    public void updateCalendar(final Context context) {

        StatusSingleton status = StatusSingleton.getInstance();
        currentDate = Calendar.getInstance();

        this.cells = new ArrayList<>();
        this.calendar = (Calendar) currentDate.clone();
        if (status.isInMonthView()) {
            this.calendar.add(Calendar.MONTH, position - Constants.TOTAL_SLIDES / 2);
        } else if (status.isInDayView()) {
          //  this.calendar.add(Calendar.DAY_OF_MONTH, position - Constants.TOTAL_SLIDES / 2);
        }

        // determine the cell for current month's beginning
        if (status.isInMonthView()) {
            this.calendar.set(Calendar.DAY_OF_MONTH, 1);
            if (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                this.monthBeginningCell = this.calendar.get(Calendar.DAY_OF_WEEK) + 5;
            } else
                this.monthBeginningCell = this.calendar.get(Calendar.DAY_OF_WEEK) - 2;
        } else if (status.isInDayView()) {
            this.dayCalendar = status.getCurrentDate();
           // this.dayCalendar.add(Calendar.DAY_OF_MONTH, position - Constants.TOTAL_SLIDES/2);

           /* if (this.dayCalendar.get(Calendar.DAY_OF_MONTH) == Calendar.SUNDAY) {
                this.dayCalendar.add(Calendar.DAY_OF_MONTH, 1);
            } else if (this.dayCalendar.get(Calendar.DAY_OF_MONTH) == Calendar.SATURDAY) {
                this.dayCalendar.add(Calendar.DAY_OF_MONTH, 2);
            }*/
        }

        // move calendar backwards to the beginning of the week
        if (status.isInMonthView()) {
            this.calendar.add(Calendar.DAY_OF_MONTH, -this.monthBeginningCell);

            // fill cells
            while (cells.size() < DAYS_COUNT) {
                cells.add(new GridDateModel(this.calendar.getTime()));
                this.calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            // create and set adapter on gridView
            final MonthCalendarAdapter calendarAdapter = new MonthCalendarAdapter(context, cells);
            grid.setNumColumns(7);
            MainActivity activity = (MainActivity)getActivity();
            MainActivityCallBackInterface mainActivityCallBackInterface = (MainActivityCallBackInterface)activity;
            calendarAdapter.setCallback(mainActivityCallBackInterface);
            grid.setAdapter(calendarAdapter);
        }

        else if (status.isInDayView()){
            cells.add(new GridDateModel(this.dayCalendar.getTime()));
            cells.add(new GridDateModel(this.dayCalendar.getTime()));
            grid.setNumColumns(1);
            grid.setAdapter(new CustomDayAdapter(context, cells));
        }
        // multiselect
        //on touch
        final StatusSingleton statusSingleton = StatusSingleton.getInstance();
        if(statusSingleton.isEditMode()) {

            grid.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int touchedPosition = grid.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                    if (touchedPosition >= 0 && touchedPosition < 42) {

                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            boolean isAlreadychecked = false;
                            boolean isAlreadyCheckedTwice = false;
                            int checkedFirstPos = 0;
                            int checkLastPos = 0;
                            // check if menu is launched if not,  launch it
                            if(!statusSingleton.isMenuCreated()) {
                                launchMultiSelectMenu();
                                statusSingleton.setMenuCreated(true);
                            }
                            for (int g = 0; g < cells.size();g++) {
                                if (cells.get(g).isState()) {
                                    isAlreadychecked = true;
                                    checkedFirstPos = g;
                                    break;
                                }
                            }
                                if (isAlreadychecked){
                                    for (int g = cells.size()-1; g >= checkedFirstPos + 1 ;g--){
                                        if (cells.get(g).isState()){
                                            isAlreadyCheckedTwice = true;
                                            checkLastPos = g;
                                            break;

                                        }
                                }

                            }
                            if(isAlreadychecked){
                                if(!isAlreadyCheckedTwice) {
                                    if (touchedPosition == checkedFirstPos){
                                        changeSate(cells.get(touchedPosition),touchedPosition);
                                    }
                                    else if (touchedPosition <= checkedFirstPos) {
                                        for (int j = touchedPosition; j <= checkedFirstPos -1 ; j++) {

                                            changeSate(cells.get(j), j);
                                        }

                                    } else {
                                        for (int j = checkedFirstPos +1 ; j < touchedPosition + 1; j++) {
                                            changeSate(cells.get(j), j);
                                        }

                                    }
                                }else {
                                    if(!cells.get(touchedPosition).isState()) {

                                        if (touchedPosition <= checkLastPos) {
                                            for (int j = touchedPosition; j <= checkedFirstPos - 1; j++) {

                                                changeSate(cells.get(j), j);
                                            }

                                        } else {
                                            for (int j = checkLastPos + 1; j < touchedPosition + 1; j++) {
                                                changeSate(cells.get(j), j);
                                            }

                                        }
                                    }else {
                                        if(touchedPosition == checkedFirstPos){
                                            changeSate(cells.get(touchedPosition),touchedPosition);
                                        }else if (touchedPosition <= checkLastPos) {
                                            for (int j = touchedPosition; j <= checkLastPos; j++) {

                                                changeSate(cells.get(j), j);
                                            }

                                        } else {
                                            for (int j = checkLastPos + 1; j < touchedPosition + 1; j++) {
                                                changeSate(cells.get(j), j);
                                            }

                                        }

                                    }
                                }
                            }else {
                                changeSate(cells.get(touchedPosition), touchedPosition);
                                //sendDataToFragment(touchedPosition,cells.get(touchedPosition) );
                            }


                            if (!isDoneOnce) {
                                sendDataToFragment(touchedPosition, cells.get(touchedPosition));
                                isDoneOnce = true;
                            }

                            return true;

                        }

                    }

                    return true;
                }
            });
        }
        else{
            grid.setOnTouchListener(null);


        }

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));

    }
    // change state of GridDateModel selected and set right color on gridview
    public void changeSate(GridDateModel selectedDay, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDay.getDate());
        int cellPosition;
        StatusSingleton status = StatusSingleton.getInstance();
        if(status.isInDayView()){
            cellPosition = 0;
        }
        else if (status.isInMonthView()){
            cellPosition = 15;
        }
        else cellPosition = 02;
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && calendar.get(Calendar.DAY_OF_WEEK )!= Calendar.SUNDAY
                && selectedDay.getDate().getMonth() == cells.get(cellPosition).getDate().getMonth()) {
            if (!selectedDay.isState()) {

                grid.getChildAt(position).setBackgroundResource(R.color.SELECTING_COLOR);
                selectedDay.setState(true);

            } else {
                grid.getChildAt(position).setBackgroundResource(0);
                selectedDay.setState(false);

            }
            sendDataToFragment(position,selectedDay);

        }
    }
    public void sendDataToFragment(int position, GridDateModel gridDate){
        if(this.fragment != null) {
            if (gridDate.isState()) {
                try {
                    ((AdapterCallbackInterface) this.fragment).passCheckedDay(cells.get(position).getDate(),position, gridDate.isState());
                } catch (ClassCastException e) {

                }
            } else {
                try {
                    ((AdapterCallbackInterface) this.fragment).passCheckedDay(cells.get(position).getDate(),position, gridDate.isState());
                } catch (ClassCastException e) {

                }
            }
        }
    }
    public void launchMultiSelectMenu(){


        //final Activity activity = (MainActivity)getActivity();
        FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        this.fragment = new MultiSelectMenuFragment();
        ft.add(R.id.list_fragment_container,fragment).commit();


    }
    // get date to show on top
    public GridDateModel getCurrentDate(){
        StatusSingleton status = StatusSingleton.getInstance();
        if(status.isInMonthView()) {
            return this.cells.get(15);
        }
        else if(status.isInDayView()){
            return new GridDateModel(this.dayCalendar.getTime());
        }
        // Week place
        else return null;
    }





}
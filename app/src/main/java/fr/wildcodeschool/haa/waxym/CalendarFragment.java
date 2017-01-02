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

    public void updateCalendar(final Context context)
    {
        currentDate = Calendar.getInstance();

        this.cells = new ArrayList<>();
        calendar = (Calendar)currentDate.clone();
        calendar.add(Calendar.MONTH, position-Constants.TOTAL_SLIDES /2);

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        final int monthBeginningCell;
        if(calendar.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY){
            monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) +5;
        }else
            monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) -2;

        // move calendar backwards to the beginning of the week

        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(new GridDateModel(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        // create and set adapter on gridView
        final MonthCalendarAdapter calendarAdapter = new MonthCalendarAdapter(context,cells);
        grid.setAdapter(calendarAdapter);

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
                            int checkedPos = 0;
                            int checkLastPos = 0;
                            for (int g = 0; g < cells.size();g++) {
                                if (cells.get(g).isState()) {
                                    isAlreadychecked = true;
                                    checkedPos = g;
                                    break;
                                }
                            }
                                if (isAlreadychecked){
                                    for (int g = cells.size()-1; g >=0 ;g--){
                                        if (cells.get(g).isState()){
                                            isAlreadyCheckedTwice = true;
                                            checkLastPos = g;
                                            break;

                                        }
                                }

                            }
                            if(isAlreadychecked){
                                if(!isAlreadyCheckedTwice) {
                                    if (touchedPosition <= checkedPos) {
                                        for (int j = touchedPosition; j <= checkedPos ; j++) {

                                            changeSate(cells.get(j), j);
                                        }

                                    } else {
                                        for (int j = checkedPos +1 ; j < touchedPosition + 1; j++) {
                                            changeSate(cells.get(j), j);
                                        }

                                    }
                                }else {
                                    if (touchedPosition <= checkLastPos) {
                                    for (int j = touchedPosition; j <= checkLastPos; j++) {

                                        changeSate(cells.get(j), j);
                                    }

                                } else {
                                    for (int j = checkLastPos +1; j < touchedPosition + 1; j++) {
                                        changeSate(cells.get(j), j);
                                    }

                                }

                                }
                            }else {
                                changeSate(cells.get(touchedPosition), touchedPosition);
                            }

                            // check if menu is launched if not,  launch it
                            if(!statusSingleton.isMenuCreated()) {
                                launchMultiSelectMenu();
                                statusSingleton.setMenuCreated(true);
                            }
                         /*   if (!isDoneOnce) {
                                sendDataToFragment(touchedPosition, cells.get(touchedPosition));
                                isDoneOnce = true;
                            }*/

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
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && calendar.get(Calendar.DAY_OF_WEEK )!= Calendar.SUNDAY
                && selectedDay.getDate().getMonth() == cells.get(15).getDate().getMonth()) {
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
        return this.cells.get(15);
    }



}
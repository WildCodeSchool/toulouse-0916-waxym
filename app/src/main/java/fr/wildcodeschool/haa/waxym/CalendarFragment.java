package fr.wildcodeschool.haa.waxym;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;

import fr.wildcodeschool.haa.waxym.model.GridDateModel;


/**
 * fragment where calendar view is generated with it properties for day or month calling respectives adapters
 */

public class CalendarFragment extends Fragment {

    private int position;
    private ArrayList<GridDateModel> cells;
    private boolean isDoneOnce = false;
    private MultiSelectMenuFragment fragment;
    private GridView grid;
    private LinearLayout header;
    private int monthBeginningCell;
    private Calendar dayCalendar;
    private MonthAdapter calendarAdapter;
    private float startX;
    private float startY;
    private int startPosition = 0;

    View root;
    Calendar calendar;
    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};
    // seasons' rainbow
    int[] rainbow = new int[]{
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
        grid = (GridView) root.findViewById(R.id.calendar_grid);
        header = (LinearLayout) getActivity().findViewById(R.id.calendar_header);

        updateCalendar(root.getContext());
        return root;
    }

    public void updateCalendar(final Context context) {

        final StatusSingleton status = StatusSingleton.getInstance();
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
        }

        // move calendar backwards to the beginning of the week
        if (status.isInMonthView()) {
            this.calendar.add(Calendar.DAY_OF_MONTH, -this.monthBeginningCell);

            // fill cells
            while (cells.size() < DAYS_COUNT) {
                cells.add(new GridDateModel(this.calendar.getTime()));
                this.calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new CreateAndSetMonthAdapter().doInBackground();
                }
            });

            // create and set adapter on gridView


        } else if (status.isInDayView()) {
            cells.add(new GridDateModel(this.dayCalendar.getTime()));
            cells.add(new GridDateModel(this.dayCalendar.getTime()));
            grid.setNumColumns(1);
            grid.setAdapter(new DayAdapter(context, cells));
        }
        // multiselect
        //on touch
        final StatusSingleton statusSingleton = StatusSingleton.getInstance();


        if (statusSingleton.isEditMode()) {
            grid.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    float initialY = motionEvent.getY(), initialX = motionEvent.getX();
                    int position = grid.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                    if (position >= 0 && position < 42) {

                        if (motionEvent.getActionMasked() == MotionEvent.ACTION_UP) {
                            isDoneOnce = false;
                            startPosition = 0;
                        }
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            changeSate(cells.get(position), position, initialX, initialY);
                            if (startPosition == 0) {
                                startPosition = position;
                            }
                            if (!status.isMenuCreated()) {
                                launchMultiSelectMenu();
                                status.setMenuCreated(true);
                            }
                            if (!isDoneOnce) {
                                sendDataToFragment(position, cells.get(position));
                                isDoneOnce = true;

                            }

                            return true;

                            //on swipe
                        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                            if (checkDistance(startX, startY, motionEvent.getX(), motionEvent.getY(), grid.getChildAt(position).getWidth(), grid.getChildAt(position).getHeight())) {
                                if (startPosition < position) {
                                    for (int i = startPosition + 1; i < position + 1; i++) {
                                        changeSate(cells.get(i), i, initialX, initialY);

                                    }
                                } else {
                                    for (int i = position; i < startPosition; i++) {
                                        changeSate(cells.get(i), i, initialX, initialY);

                                    }


                                }
                                startPosition = position;
                            }

                        }

                    }

                    return true;
                }
            });


        } else {
            grid.setOnTouchListener(null);


        }

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));

    }

    // change state of GridDateModel selected and set right color on gridview
    public void changeSate(GridDateModel selectedDay, int position, float x, float y) {
        startX = x;
        startY = y;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDay.getDate());
        int cellPosition;
        StatusSingleton status = StatusSingleton.getInstance();
        if (status.isInDayView()) {
            cellPosition = 0;
        } else if (status.isInMonthView()) {
            cellPosition = 15;
        } else cellPosition = 02;
        if (isNotWeekEnd(selectedDay, calendar, cells.get(cellPosition).getDate().getMonth())) {
            if (!selectedDay.isState()) {

                grid.getChildAt(position).setBackgroundResource(R.color.SELECTING_COLOR);
                selectedDay.setState(true);

            } else {
                grid.getChildAt(position).setBackgroundResource(0);
                selectedDay.setState(false);

            }
            sendDataToFragment(position, selectedDay);

        }
    }

    protected boolean isNotWeekEnd(GridDateModel gridDateModel, Calendar calendar, int month) {
        return !isWeekend(calendar) && gridDateModel.getMonth() == month;
    }

    protected boolean isWeekend(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public void sendDataToFragment(int position, GridDateModel gridDate) {
        if (this.fragment != null) {
            if (gridDate.isState()) {
                try {
                    ((MultiSelectMenuCallbackInterface) this.fragment).passCheckedDay(cells.get(position).getDate(), position, gridDate.isState());
                } catch (ClassCastException e) {

                }
            } else {
                try {
                    ((MultiSelectMenuCallbackInterface) this.fragment).passCheckedDay(cells.get(position).getDate(), position, gridDate.isState());
                } catch (ClassCastException e) {

                }
            }
        }
    }

    public void launchMultiSelectMenu() {


        //final Activity activity = (MainActivity)getActivity();
        FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        this.fragment = new MultiSelectMenuFragment();
        ft.add(R.id.list_fragment_container, fragment).commit();


    }

    // get date to show on top
    public GridDateModel getCurrentDate() {
        StatusSingleton status = StatusSingleton.getInstance();
        if (status.isInMonthView()) {
            return this.cells.get(15);
        } else if (status.isInDayView()) {
            return new GridDateModel(this.dayCalendar.getTime());
        }
        // Week place
        else return null;
    }

    public boolean checkDistance(float x, float y, float deltaX, float deltaY, int width, int height) {
        float diffX = 0;
        float diffY = 0;
        diffX = Math.abs(deltaX - x);
        diffY = Math.abs(deltaY - y);
        if (diffX > width - width * 0.15 || diffY > height - height * 0.15)
            return true;
        else
            return false;


    }

    class CreateAndSetMonthAdapter extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            calendarAdapter = new MonthAdapter(getContext(), cells);
            grid.setNumColumns(7);
            MainActivity activity = (MainActivity) getActivity();
            MainActivityCallBackInterface mainActivityCallBackInterface = (MainActivityCallBackInterface) activity;
            calendarAdapter.setCallback(mainActivityCallBackInterface);
            grid.setAdapter(calendarAdapter);
            return null;
        }
    }


}
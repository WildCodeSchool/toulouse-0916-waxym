package fr.wildcodeschool.haa.waxym;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fr.wildcodeschool.haa.waxym.model.DayStuffModel;

/**
 * Created by a7med on 28/06/2015.
 */
public class CalendarView extends LinearLayout {
    private ArrayList<GridDateModel> cells ;
    public static boolean isMenuCreated = false;
    public static boolean isEditMode = false;
    private boolean isDoneOnce = false;
    private MultiSelectMenuFragment fragment;

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

    //event handling
    private EventHandler eventHandler = null;
    // init move pos
    private float startX = 0;
    private float startY = 0;
    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    // seasons' rainbow
    int[] rainbow = new int[] {
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    // month-season association (northern hemisphere, sorry australia :)
    int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public CalendarView(Context context)
    {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs)
    {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try
        {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        }
        finally
        {
            ta.recycle();
        }
    }
    private void assignUiElements()
    {
        // layout is inflated, assign local variables to components
        header = (LinearLayout)findViewById(R.id.calendar_header);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers()
    {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // long-pressing a day
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id)
            {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((GridDateModel)view.getItemAtPosition(position));
                return true;
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    /**
     * Display dates correctly in grid
     */

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar()
    {

        this.cells = new ArrayList<>();
        final Calendar calendar = (Calendar)currentDate.clone();

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
        final MonthCalendarAdapter calendarAdapter = new MonthCalendarAdapter(getContext(),cells);
        grid.setAdapter(calendarAdapter);

        // multiselect
        //grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        //on touch
        if(this.isEditMode) {

            grid.setOnTouchListener(new OnTouchListener() {

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
                                launchMultiSelectMenu();
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
            assignClickHandlers();
            // à faire ( swipe changer mois
        }
        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));

    }

   




    /**
     * Assign event handler to be passselectedList = new ArrayList<>();ed needed events
     */
    public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler
    {
        void onDayLongPress(GridDateModel date);
    }
    // check if distance between two point is higher than max entered

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
    public void launchMultiSelectMenu(){


        final Activity activity = (MainActivity)getContext();
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        this.fragment = new MultiSelectMenuFragment();
        ft.add(R.id.list_fragment_container,fragment).commit();


    }
    public void onButtonCancel(boolean bool){
        if (!bool) {
            for (int i = 0 ; i < grid.getChildCount();i++)
            grid.getChildAt(i).setBackgroundResource(0);



        }

    }



}

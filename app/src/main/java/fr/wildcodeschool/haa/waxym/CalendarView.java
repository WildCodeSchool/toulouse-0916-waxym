package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.wildcodeschool.haa.waxym.model.GridDateModel;

/**
 * Created by a7med on 28/06/2015.
 */
public class CalendarView extends LinearLayout {




    //event handling
    private EventHandler eventHandler = null;
    // init move pos

    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;



    // month-season association (northern hemisphere, sorry australia :)

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

       // loadDateFormat(attrs);
        assignUiElements();
        //assignClickHandlers();
       /* if(((Activity)context).getFragmentManager().findFragmentById(R.id.viewPager) == null)
        ((CalendarInterface)context).clearCalendar(context);*/
    }


    private  void assignUiElements()
    {
        // layout is inflated, assign local variables to components
        header = (LinearLayout)findViewById(R.id.calendar_header);
       // btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        //btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
    }

    /*private void assignClickHandlers()
    {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, 1);
                clearCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, -1);
                clearCalendar();
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

    }*/

    /**
     * Display dates correctly in grid
     */

    /**
     * Display dates correctly in grid
     */


   




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


    public void onButtonCancel(boolean bool){
        if (!bool) {
            for (int i = 0 ; i < grid.getChildCount();i++)
            grid.getChildAt(i).setBackgroundResource(0);



        }

    }



}

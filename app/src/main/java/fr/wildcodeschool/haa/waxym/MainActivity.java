package fr.wildcodeschool.haa.waxym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;



public class MainActivity extends OptionMenuActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getSerializableExtra("date et event") !=null) {
            DayEvent eventRtt = (DayEvent) getIntent().getSerializableExtra("date et event");
            HashSet<DayEvent> events = new HashSet<>();
            events.add(eventRtt);

            CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view));
            cv.updateCalendar(events);
        }
        // assign event handler
        CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view));
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(MainActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

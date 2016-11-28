package fr.wildcodeschool.haa.waxym;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.DayStuff;


public class MainActivity extends AppCompatActivity {
    private static final String LIST_FRAGMENT_TAG = "list_fragment";
    private DBHandler mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create  DBHandler
        this.mDBHelper = new DBHandler(this);
        // check if database exist
        File database = this.getApplicationContext().getDatabasePath(Constants.DBNAME);
        //copyDatabase(getApplicationContext());
        if (!database.exists()) {
            this.mDBHelper.getReadableDatabase();
            // and copy database with method
            if (!this.copyDatabase(this)) {
                Toast.makeText(this, "error cannot copy Database", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        // test to get activities list
        mDBHelper.getUserActivitiesList(1);
        DayStuff dayStuff = null;
        // test to get events of user
        try {
            dayStuff = mDBHelper.getEvents(1,Calendar.getInstance().getTime()).get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dayStuff.setDate(Calendar.getInstance().getTime());
        try {
            mDBHelper.setEvent(dayStuff);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView textView = (TextView)findViewById(R.id.test);

        try {
            textView.setText(mDBHelper.getEvents(1,Calendar.getInstance().getTime()).get(0).getUserName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (getIntent().getSerializableExtra("date et event") != null) {
            DayEvent eventRtt = (DayEvent) getIntent().getSerializableExtra("date et event");
            HashSet<DayEvent> events = new HashSet<>();
            events.add(eventRtt);

            CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view));
            cv.updateCalendar(events);
        }
        // assign event handler
        CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view));
        cv.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {
                // show returned day
                DateFormat sdf = SimpleDateFormat.getDateInstance();
                Toast.makeText(MainActivity.this, sdf.format(date), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_list) {
            toggleList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //animation du menu
    private void toggleList() {
        Fragment f = getFragmentManager().findFragmentByTag(LIST_FRAGMENT_TAG);
        if (f != null) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.slide_up,
                            R.animator.slide_down,
                            R.animator.slide_up,
                            R.animator.slide_down)
                    .add(R.id.list_fragment_container, SlidingListFragment
                                    .instantiate(this, SlidingListFragment.class.getName()),
                            LIST_FRAGMENT_TAG
                    ).addToBackStack(null).commit();
        }
    }

    //copying database from assets to database folder
    private boolean copyDatabase(Context context) {
        try {
            InputStream inpuStream = context.getAssets().open(Constants.DBNAME);
            // set target of output
            OutputStream outputStream = new FileOutputStream(getDatabasePath(Constants.DBNAME));
            // buffer
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inpuStream.read(buff)) > 0) {
                //writing
                outputStream.write(buff, 0, length);

            }
            //clear buffer
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity", "DB copied");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings)
        {
            return true;
        }*/

        ////  return super.onOptionsItemSelected(item);
        //}
    }
}

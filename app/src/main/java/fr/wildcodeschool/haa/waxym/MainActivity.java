package fr.wildcodeschool.haa.waxym;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Handler;


import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;


public class MainActivity extends OptionMenuActivity implements MainActivityCallBackInterface {
    private static final String LIST_FRAGMENT_TAG = "list_fragment";
    private DBHandler mDBHelper;
    CalendarView cv;
    ViewPager viewPager;
    CalendarFragment calendarFragment;
    TextView textDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDate = (TextView)findViewById(R.id.calendar_date_display);


        this.mDBHelper = new DBHandler(this);
        // check if database exist
        File database = this.getApplicationContext().getDatabasePath(Constants.DBNAME);
        copyDatabase(getApplicationContext());
        if (!database.exists()) {
            this.mDBHelper.getReadableDatabase();
            // and copy database with method
            if (!this.copyDatabase(this)) {
                Toast.makeText(this, "error cannot copy Database", Toast.LENGTH_SHORT).show();
                return;
            }

        }


        final Button editButton = (Button) findViewById(R.id.buttonEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusSingleton statusSingleton = StatusSingleton.getInstance();
                if( statusSingleton.isEditMode()){
                    statusSingleton.setEditMode(false);

                   if (statusSingleton.isMenuCreated()) {
                       getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.list_fragment_container)).commit();
                   }
                    statusSingleton.setMenuCreated(false);

                    editButton.setBackgroundResource(R.drawable.edit);
                    updateCurrentViewPagerFragment();
                   // viewPager.getAdapter().notifyDataSetChanged();
                }
                else{
                    statusSingleton.setEditMode(true);
                    editButton.setBackgroundResource(R.drawable.annul);
                    updateCurrentViewPagerFragment();
                    //viewPager.getAdapter().notifyDataSetChanged();

                }
            }
        });


        PagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), this);
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.viewPager.setAdapter(adapter);
        this.viewPager.setCurrentItem(Constants.historyCount/2);
        // set postdelayed to let time to viewpager instantiate fragments
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                   showCurrentDate();
            }
        }, 8);

        // set OnpageChangeListener to refresh currentDate
        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 showCurrentDate();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        viewPager.addOnPageChangeListener(pageChangeListener);






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
            this.viewPager.setCurrentItem(Constants.historyCount/2);
           // this.viewPager.getAdapter().notifyDataSetChanged();
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

    }
    @Override
    public void onMethodCallBack() {
        updateCurrentViewPagerFragment();
       // viewPager.getAdapter().notifyDataSetChanged();


    }

    @Override
    public void sendSelectedDays(ArrayList<DayStuffModel> passedList) {
        ArrayList<DayStuffModel> dates = new ArrayList<>();
        dates = passedList;
    toggleList();
    }

    public void showCurrentDate(){
        MyPagerAdapter adapter1 = (MyPagerAdapter)viewPager.getAdapter();

        CalendarFragment calendarFragment = (CalendarFragment) adapter1.instantiateItem(viewPager,viewPager.getCurrentItem());

        GridDateModel gridDateModel = calendarFragment.getCurrentDate();
        String monthFormat = "MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(monthFormat, Locale.FRANCE);
       // SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        String currentDate = sdf.format(gridDateModel.getDate());
/*        currentDate += " ";
        currentDate+= sdf2.format(gridDateModel.getDate());*/
        textDate.setText(currentDate);

    }

    public void updateCurrentViewPagerFragment(){
        FragmentStatePagerAdapter fsp = (FragmentStatePagerAdapter)viewPager.getAdapter();
        CalendarFragment calendarFragment = (CalendarFragment) fsp.instantiateItem(viewPager,viewPager.getCurrentItem());
        calendarFragment.updateCalendar(getApplicationContext());
    }


}

package fr.wildcodeschool.haa.waxym;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;
import fr.wildcodeschool.haa.waxym.model.GridDateModel;


public class MainActivity extends AppCompatActivity implements MainActivityCallBackInterface {
    private static final String LIST_FRAGMENT_TAG = "list_fragment";
    private DBHandler mDBHelper;
    ViewPager viewPager;
    TextView textDate;
    LinearLayout header;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDate = (TextView) findViewById(R.id.calendar_date_display);


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


        final Button editButton = (Button) findViewById(R.id.buttonEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusSingleton statusSingleton = StatusSingleton.getInstance();
                if (statusSingleton.isEditMode()) {
                    statusSingleton.setEditMode(false);

                    if (statusSingleton.isMenuCreated()) {
                        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.list_fragment_container)).commit();
                        statusSingleton.setMenuCreated(false);
                    }

                    editButton.setBackgroundResource(R.drawable.edit);
                    updateCurrentViewPagerFragment();
                    // viewPager.getAdapter().notifyDataSetChanged();
                } else {
                    statusSingleton.setEditMode(true);
                    editButton.setBackgroundResource(R.drawable.annul);
                    updateCurrentViewPagerFragment();
                    //viewPager.getAdapter().notifyDataSetChanged();

                }
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), getApplicationContext());
                viewPager = (ViewPager) findViewById(R.id.viewPager);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(Constants.TOTAL_SLIDES / 2);
                // set postdelayed to let time to viewpager instantiate fragments
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showCurrentDate();
                    }
                }, 18);

                // set OnpageChangeListener to refresh currentDate
                ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        updateCurrentViewPagerFragment();
                        showCurrentDate();

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                };
                viewPager.addOnPageChangeListener(pageChangeListener);


            }
        });

        header = (LinearLayout)findViewById(R.id.calendar_header);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //String[] items={"Choisissez","Jour","Semaine","Mois"};

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list, R.layout.spinner_item);
        //adapter.setDropDownViewResource(R.layout.spinner_item);
        MenuItem mitem = menu.findItem(R.id.item1);

        Spinner spin = (Spinner) MenuItemCompat.getActionView(mitem);

        spin.setAdapter(adapter);
        //click spinner
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatusSingleton status = StatusSingleton.getInstance();
                if (position == 1) {
                    status.setInDayView(true);
                    status.setInMonthView(false);
                    updateCurrentViewPagerFragment();
                    updateBorderViewPagerFragment();
                }
                else if (position == 3){
                    status.setInDayView(false);
                    status.setInMonthView(true);
                    updateCurrentViewPagerFragment();
                    updateBorderViewPagerFragment();
                }
                showCurrentDate();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_list) {
            this.viewPager.setCurrentItem(Constants.TOTAL_SLIDES / 2);
            // this.viewPager.getAdapter().notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //launch Pop-up
    private void launchPopup(ArrayList<DayStuffModel> selectedDates) {
        // need to create new Arraylist because data is lost if not
        ArrayList<DayStuffModel> selectedDatesAgain = new ArrayList<>();
        selectedDatesAgain.addAll(selectedDates);
        //initiate argument
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.SELECTED_DAYS, selectedDatesAgain);

        Fragment slidingListFragment = new SlidingListFragment();
        //configure fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_up,
                        R.animator.slide_down,
                        R.animator.slide_up,
                        R.animator.slide_down)
                .add(R.id.list_fragment_container, slidingListFragment,
                        LIST_FRAGMENT_TAG
                ).addToBackStack(null);
        //set argument
        slidingListFragment.setArguments(bundle);
        // launch
        ft.commit();
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
        // update current viewed fragment
        updateCurrentViewPagerFragment();
    }

    @Override
    public void sendSelectedDays(ArrayList<DayStuffModel> passedList) {
        launchPopup(passedList);
    }
    // get current Date of currentCalendarFragment and show it on top
    public void showCurrentDate() {
        StatusSingleton status = StatusSingleton.getInstance();
        MyPagerAdapter adapter1 = (MyPagerAdapter) viewPager.getAdapter();

        CalendarFragment calendarFragment = (CalendarFragment) adapter1.instantiateItem(viewPager, viewPager.getCurrentItem());

        GridDateModel gridDateModel = calendarFragment.getCurrentDate();
        String dateFormat;
        if(status.isInMonthView()) {
            header.setVisibility(View.VISIBLE);
            dateFormat = "MMMM yyyy";
        }
        else if (status.isInDayView()) {
            header.setVisibility(View.INVISIBLE);
            dateFormat = "dd MMMM";
        }
        else
            dateFormat = "MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.FRANCE);
        // SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        String currentDate = sdf.format(gridDateModel.getDate());
/*        currentDate += " ";
        currentDate+= sdf2.format(gridDateModel.getDate());*/
        textDate.setText(currentDate);

    }

    public void updateCurrentViewPagerFragment() {
        FragmentStatePagerAdapter fsp = (FragmentStatePagerAdapter) viewPager.getAdapter();
        CalendarFragment calendarFragment = (CalendarFragment) fsp.instantiateItem(viewPager, viewPager.getCurrentItem());
        calendarFragment.updateCalendar(getApplicationContext());
    }

    public void updateBorderViewPagerFragment() {
        FragmentStatePagerAdapter fsp = (FragmentStatePagerAdapter) viewPager.getAdapter();
        CalendarFragment rightCalendarFragment = (CalendarFragment) fsp.instantiateItem(viewPager, viewPager.getCurrentItem()+1);
        rightCalendarFragment.updateCalendar(getApplicationContext());
        CalendarFragment leftCalendarFragment = (CalendarFragment) fsp.instantiateItem(viewPager, viewPager.getCurrentItem()-1);
        leftCalendarFragment.updateCalendar(getApplicationContext());
    }


}

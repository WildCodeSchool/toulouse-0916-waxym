package fr.wildcodeschool.haa.waxym;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;


public class MainActivity extends OptionMenuActivity implements MainActivityCallBackInterface {
    private static final String LIST_FRAGMENT_TAG = "list_fragment";
    private DBHandler mDBHelper;
    CalendarView cv;
    ViewPager viewPager;
    android.support.v4.app.Fragment calendarFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create  DBHandler
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
                calendarFragment=  getSupportFragmentManager().findFragmentById(R.id.viewPager);
                StatusSingleton statusSingleton = StatusSingleton.getInstance();
                if( statusSingleton.isEditMode()){
                    statusSingleton.setEditMode(false);


                    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.list_fragment_container)).commit();
                    statusSingleton.setMenuCreated(false);
                    ((CalendarInterface)calendarFragment).clearCalendar();
                    editButton.setBackgroundResource(R.drawable.edit);
                   // refreshViewPager();
                }
                else{
                    statusSingleton.setEditMode(true);
                    editButton.setBackgroundResource(R.drawable.annul);
                    //refreshViewPager();
                }
            }
        });

        // assign event handler


        PagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()){

            @Override
            public int getCount() {
                return Constants.historyCount;
            }

            @Override
            public android.support.v4.app.Fragment getItem(int position) {

                Bundle bundle = new Bundle();
                bundle.putInt("Month",position);

                return CalendarFragment.newInstance(bundle);
            }

        };

        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(adapter);

        // actually an InfiniteViewPager
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.viewPager.setAdapter(wrappedAdapter);
        this.viewPager.setCurrentItem(Constants.historyCount/2);
        this.viewPager.getAdapter().notifyDataSetChanged();
        this.calendarFragment=  getSupportFragmentManager().findFragmentById(R.id.viewPager);



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
            this.viewPager.getAdapter().notifyDataSetChanged();
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
    @Override
    public void onMethodCallBack() {
        viewPager.getAdapter().notifyDataSetChanged();
//        ((CalendarInterface)this.calendarFragment).clearCalendar(getApplicationContext());


    }

    @Override
    public void sendSelectedDays(ArrayList<DayStuffModel> passedList) {
        ArrayList<DayStuffModel> dates = new ArrayList<>();
        dates = passedList;
    toggleList();
    }

    @Override
    public void refreshDate(Calendar calendar) {
        String monthFormat = "MMMM";
        TextView textDate = (TextView)findViewById(R.id.calendar_date_display);
        SimpleDateFormat sdf = new SimpleDateFormat(monthFormat, Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        textDate.setText(sdf.format(calendar.get(Calendar.MONTH))+ " " + sdf2.format(calendar.get(Calendar.YEAR)));
    }

    public void refreshViewPager(){
        int positionSave = viewPager.getCurrentItem();
        viewPager.getAdapter().destroyItem(viewPager,viewPager.getCurrentItem(),calendarFragment);
        viewPager.getAdapter().instantiateItem(viewPager,positionSave);
    }

}

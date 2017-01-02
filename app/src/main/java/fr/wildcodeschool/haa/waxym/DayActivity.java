package fr.wildcodeschool.haa.waxym;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Spinner;

/**
 * Created by apprenti on 26/12/16.
 */

public class DayActivity extends AppCompatActivity {
    GridView gridView;

    static final String[] days = new String[] {
            "Boulot du matin", "Boulot de l'aprem" };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_grid);


       // BaseAdapter customDay = new CustomDayAdapter(gridView.getContext(),days);
        gridView = (GridView) findViewById(R.id.activity_day);
        gridView.setAdapter(new CustomDayAdapter(this, days));

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinner_list,R.layout.spinner_item);

        MenuItem mitem = menu.findItem(R.id.item1);

        Spinner spin =(Spinner) MenuItemCompat.getActionView(mitem);

        spin.setAdapter(adapter);
        //click spinner
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3){
                    Intent intent = new Intent(DayActivity.this, MainActivity.class);
                    DayActivity.this.startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;

    }

}

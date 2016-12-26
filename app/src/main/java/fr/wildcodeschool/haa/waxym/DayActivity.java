package fr.wildcodeschool.haa.waxym;

import android.content.Intent;
import android.os.Bundle;
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
        // Spinner action bar
        MenuItem mitem = menu.findItem(R.id.item1);
        Spinner spin =(Spinner) mitem.getActionView();
        setupSpinner(spin);

        //employeah action bar centre
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        return true;
    }
    public void setupSpinner(Spinner spin){
        String[] items={"Choisissez","Jour","Semaine","Mois"};
        //wrap the items in the Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,items);
        //assign adapter to the Spinner
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
    }
}

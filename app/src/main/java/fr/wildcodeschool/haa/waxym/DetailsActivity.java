package fr.wildcodeschool.haa.waxym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import java.util.zip.Inflater;

public class DetailsActivity extends OptionMenuActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // get serialized date
        final Date date = (Date)getIntent().getSerializableExtra("date");

        Button rtt = (Button)findViewById(R.id.rtt);
        rtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this,MainActivity.class);
                intent.putExtra("date et event", new DayEvent(date,"RTT"));
                finish();
                startActivity(intent);
            }
        });
        //activities list
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        parent.getItemAtPosition(pos);
        LinearLayout plop = (LinearLayout)findViewById(R.id.info);
        plop.removeAllViews();
        View child = getLayoutInflater().inflate(R.layout.details,null);
        plop.addView(child);
        TextView title = (TextView)findViewById(R.id.textView3);
        title.setText((String) parent.getItemAtPosition(pos));
        TextView detail = (TextView)findViewById(R.id.textView2);
        detail.setText(new String(new char[10]).replace("\0", ((String) parent.getItemAtPosition(pos))));


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
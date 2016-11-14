package fr.wildcodeschool.haa.waxym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.zip.Inflater;

public class DetailsActivity extends OptionMenuActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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

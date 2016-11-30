package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by apprenti on 30/11/16.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] values;
    LayoutInflater inflater;

    public CustomAdapter(Context context, String[] contrat){
       // super(context, R.layout.list_view_item, contrat);
        this.context = context;
        this.values = contrat;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_item, null);
        TextView textView = (TextView) rowView.findViewById(R.id.list);

        textView.setText(values[position]);

        String s = values[position];

        if (s.startsWith("Contrat1") || s.startsWith("Contrat2")
                || s.startsWith("Contrat3")) {
            textView.setBackgroundColor(0x3FA851);
        } else {
            textView.setBackgroundColor(0x709DAE);
        }

        return rowView;

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}

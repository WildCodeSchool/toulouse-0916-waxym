package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    int[] color;

    public CustomAdapter(Context context, String[] values, int[] color){
        //super(context, R.layout.list_view_item);
        this.context = context;
        this.values = values;
        this.color = color;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //(LayoutInflater.from(getContext()));
        View customView;

        customView = inflater.inflate(R.layout.list_view_item, parent, false);
        TextView text = (TextView) customView.findViewById(R.id.list);

            text.setText(values[position]);
            text.setBackgroundColor(this.color[position]);

        return customView;

    }

}

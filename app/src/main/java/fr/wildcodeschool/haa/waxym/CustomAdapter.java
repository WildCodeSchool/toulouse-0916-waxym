package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import fr.wildcodeschool.haa.waxym.model.ActivityItemModel;

/**
 * Created by apprenti on 30/11/16.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<ActivityItemModel> listContrat;

    public CustomAdapter(Context context, ArrayList<ActivityItemModel> listContrat){
        //super(context, R.layout.list_view_item);
        this.context = context;
        this.listContrat = listContrat;
    }

    @Override
    public int getCount() {
        return listContrat.size();
    }

    @Override
    public Object getItem(int position) {
        return listContrat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView;

        customView = inflater.inflate(R.layout.list_view_item, parent, false);
        TextView text = (TextView) customView.findViewById(R.id.list);

            text.setText(listContrat.get(position).getActivityName());
            if (listContrat.get(position).isSelected()){
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.parseColor("#" + listContrat.get(position).getActivityColor())); // Changes this drawbale to use a single color instead of a gradient
                gd.setCornerRadius(5);
                gd.setStroke(5, Color.parseColor("#0000E5"));
                text.setBackgroundDrawable(gd);
            }else {
                if (this.listContrat.get(position).getActivityColor() != null)
                text.setBackgroundColor(Color.parseColor("#" + this.listContrat.get(position).getActivityColor()));
            }
        return customView;

    }

}

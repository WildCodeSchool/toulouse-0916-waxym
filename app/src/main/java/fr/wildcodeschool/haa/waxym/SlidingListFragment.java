package fr.wildcodeschool.haa.waxym;

import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by apprenti on 15/11/16.
 */

public class SlidingListFragment extends DialogFragment {
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sliding_fragment_layout, container, false);

        String[] values = new String[] { "Contrat1", "Contrat2", "Contrat3",
                "Contrat4", "Contrat5" };
        // use your custom layout
        this.adapter = new ArrayAdapter<String>(container.getContext(),
                R.layout.list_view_item, R.id.list, values);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        return view;
    }




    protected void onListItemClick(ListView l, View v, int position, long id) {
       // String item = (String) adapter.getListAdapter().getItem(position);
        //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }

}


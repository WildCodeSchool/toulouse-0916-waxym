package fr.wildcodeschool.haa.waxym;

import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by apprenti on 15/11/16.
 */

public class SlidingListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sliding_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListAdapter(new MyListAdapter());
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        //nombre de fragments
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Button result = (Button) convertView;
            if (result == null) {
                result = (Button) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_view_item, parent, false);
            }
            //ici le texte dans les fragments
            result.setText("couleur" + position);
            result.setBackgroundColor(Color.rgb(30,127,203));

            return result;
        }
    }
}


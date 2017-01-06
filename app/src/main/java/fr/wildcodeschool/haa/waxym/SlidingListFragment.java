package fr.wildcodeschool.haa.waxym;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.text.ParseException;
import java.util.ArrayList;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.ActivityItemModel;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;


/**
 * Created by apprenti on 15/11/16.
 */

public class SlidingListFragment extends DialogFragment {
    private ArrayList<ActivityItemModel> mesContrats;
    private ActivityItemModel selectedContract;
    private ArrayList<DayStuffModel> selectedDays;
    private boolean isEraseChoice = false;
    private boolean isWritingModeChoiceDone = false;
    private boolean isContractSelected = false;
    Button complete;
    Button erase;
    DBHandler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.selectedDays = getArguments().getParcelableArrayList(Constants.SELECTED_DAYS);

        final View view = inflater.inflate(R.layout.sliding_fragment_layout, container, false);
        this.mHandler = new DBHandler(view.getContext());
        this.mesContrats = this.mHandler.getUserActivitiesList(1);



        final BaseAdapter customAdapter = new CustomAdapter(view.getContext(), mesContrats);
        final ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isContractSelected = true;
                selectedContract = mesContrats.get(position);
                mesContrats.get(position).setSelected(true);
                for (int i = 0;i < mesContrats.size();i++){
                    if(i != position) {
                        mesContrats.get(i).setSelected(false);
                    }
                }
                listView.setAdapter(customAdapter);
                listView.setSelection(position);
                if (isWritingModeChoiceDone){
                   addSelectedContractToSelectedDays();

                }
            }
        });


        this.complete = (Button)view.findViewById(R.id.complete);
        this.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWritingModeChoiceDone = true;
                if (isContractSelected)
            addSelectedContractToSelectedDays();
                isEraseChoice = false;
                complete.setBackgroundResource(R.drawable.button2_selected);
                erase.setBackgroundResource(R.drawable.button);
            }
        });
        this.erase = (Button)view.findViewById(R.id.erase);
        this.erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWritingModeChoiceDone = true;
                if (isContractSelected)
                addSelectedContractToSelectedDays();
                isEraseChoice = true;
                erase.setBackgroundResource(R.drawable.button_selected);
                complete.setBackgroundResource(R.drawable.button2);
            }
        });

        Button validate = (Button)view.findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isContractSelected) {
                    if (isWritingModeChoiceDone) {
                        try {
                            writeInDatabase();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        closeFragment();
                    } else
                        Toast.makeText(view.getContext(), "Veuillez sélectionner compléter ou écraser", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(view.getContext(), "Veuillez sélectionner le type d'activité", Toast.LENGTH_SHORT).show();
            }
        });

        final Button cancel = (Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        return view;
    }
    private void addSelectedContractToSelectedDays(){
    for (int i =0; i < selectedDays.size(); i++){
        selectedDays.get(i).setContractNumber(selectedContract.getActivityNumber());
        selectedDays.get(i).setActivity(selectedContract.getActivityName());
        selectedDays.get(i).setActivityColor(selectedContract.getActivityColor());
        selectedDays.get(i).setUserId(1);
    }
    }
    private void writeInDatabase() throws ParseException {
        for (int i = 0; i < this.selectedDays.size();i++){
            if (!this.isEraseChoice){
               mHandler.setEventCompleter(selectedDays.get(i));
            }else
                mHandler.setEventEraser(selectedDays.get(i));
        }


    }
    private   void closeFragment(){
        getActivity().getFragmentManager().beginTransaction().remove(SlidingListFragment.this).commit();
        ((MainActivityCallBackInterface)getView().getContext()
        ).onMethodCallBack();


    }

}


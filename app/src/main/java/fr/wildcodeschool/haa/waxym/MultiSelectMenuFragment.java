package fr.wildcodeschool.haa.waxym;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import fr.wildcodeschool.haa.waxym.model.DayStuffModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MultiSelectMenuFragment extends Fragment implements AdapterCallbackInterface {

    ArrayList<DayStuffModel> selectedList =new ArrayList<>() ;

    public MultiSelectMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_multi_select_menu, container, false);
        //On cancel
        final Button cancel = (Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
                try {

                    closeMenu();
                    // refresh current CalendarFragment
                    ((MainActivityCallBackInterface)getView().getContext()
                    ).onMethodCallBack();
                }catch (ClassCastException e){

                }
                selectedList.clear();
                resetMultiselect();
            }
        });
        // on validate
        final Button valid = (Button)view.findViewById(R.id.valid);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();

                try {

                    ((MainActivityCallBackInterface)getView().getContext()
                    ).sendSelectedDays(selectedList);
                }catch (ClassCastException e){

                }
                selectedList.clear();
                resetMultiselect();
            }
        });
        return view;
    }
    public  void closeMenu(){
        getActivity().getFragmentManager().beginTransaction().remove(MultiSelectMenuFragment.this).commit();


    }



    //get days selected
    @Override
    public void passCheckedDay(Date date,int position, boolean isChecked) {
        DayStuffModel passedDay = new DayStuffModel();
        StatusSingleton statusSingleton = StatusSingleton.getInstance();
        if (isChecked) {
            passedDay.setDate(date);
            if(statusSingleton.isInMonthView()) {
                // add Morning and afternoon of passed dates in passedList
                passedDay.setAfternoon(0);
                passedDay.setMorning(1);
                this.selectedList.add(passedDay);
                DayStuffModel passedDay2 = new DayStuffModel();
                passedDay2.setDate(date);
                passedDay2.setMorning(0);
                passedDay2.setAfternoon(1);
                this.selectedList.add(passedDay2);
            }
        } else {

            if (statusSingleton.isInMonthView()) {
                // search and delete passed days from selectedList
                Iterator iterator = selectedList.iterator();
                while (iterator.hasNext()) {
                    DayStuffModel passedDay2 = (DayStuffModel) iterator.next();
                    Calendar passedDate = Calendar.getInstance();
                    passedDate.setTime(passedDay2.getDate());
                    Calendar evaluateDate = Calendar.getInstance();
                    evaluateDate.setTime(date);
                    if (passedDate.get(Calendar.DAY_OF_MONTH) == evaluateDate.get(Calendar.DAY_OF_MONTH))
                        iterator.remove();
                }

            }
        }
    }
private void resetMultiselect(){
    StatusSingleton statusSingleton = StatusSingleton.getInstance();
    statusSingleton.setMenuCreated(false);

}

}

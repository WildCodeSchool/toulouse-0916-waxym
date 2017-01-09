package fr.wildcodeschool.haa.waxym;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import fr.wildcodeschool.haa.waxym.database.DBHandler;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MultiSelectMenuFragment extends Fragment implements AdapterCallbackInterface {

    public ArrayList<DayStuffModel> selectedList = new ArrayList<>();
    private View mView;
    public MultiSelectMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_multi_select_menu, container, false);
        //On cancel
        final Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeMenu();
                // refresh current CalendarFragment
                ((MainActivityCallBackInterface) getView().getContext()
                ).onMethodCallBack();

                selectedList.clear();
                resetMultiselect();
            }
        });
        // on validate
        final Button valid = (Button) view.findViewById(R.id.valid);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();


                ((MainActivityCallBackInterface) getView().getContext()
                ).sendSelectedDays(selectedList);

                selectedList.clear();
                resetMultiselect();
            }
        });
        //on clear
        final Button clear = (Button) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clearSelected();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                resetMultiselect();
                ((MainActivityCallBackInterface) getView().getContext()
                ).onMethodCallBack();
            }
        });


        return view;
    }

    public void closeMenu() {
        getActivity().getFragmentManager().beginTransaction().remove(MultiSelectMenuFragment.this).commit();


    }


    //get days selected
    @Override
    public void passCheckedDay(Date date, int position, boolean isChecked) {
        DayStuffModel passedDay = new DayStuffModel();
        StatusSingleton statusSingleton = StatusSingleton.getInstance();
        if (isChecked) {
            passedDay.setDate(date);
            if (statusSingleton.isInMonthView()) {
                // add Morning and afternoon of passed dates in passedList
                passedDay.setAfternoon(0);
                passedDay.setMorning(1);
                this.selectedList.add(passedDay);
                DayStuffModel passedDay2 = new DayStuffModel();
                passedDay2.setDate(date);
                passedDay2.setMorning(0);
                passedDay2.setAfternoon(1);
                this.selectedList.add(passedDay2);
            } else if (statusSingleton.isInDayView()) {
                if (position == 0) {
                    passedDay.setAfternoon(0);
                    passedDay.setMorning(1);
                    this.selectedList.add(passedDay);
                } else {
                    passedDay.setDate(date);
                    passedDay.setMorning(0);
                    passedDay.setAfternoon(1);
                    this.selectedList.add(passedDay);
                }
            }
        } else {


            // search and delete passed days from selectedList
            Iterator iterator = selectedList.iterator();
            while (iterator.hasNext()) {
                DayStuffModel passedDay3 = (DayStuffModel) iterator.next();
                Calendar passedDate = Calendar.getInstance();
                passedDate.setTime(passedDay3.getDate());
                Calendar evaluateDate = Calendar.getInstance();
                evaluateDate.setTime(date);
                if (passedDate.get(Calendar.DAY_OF_MONTH) == evaluateDate.get(Calendar.DAY_OF_MONTH))
                    iterator.remove();


            }
        }
    }

    private void resetMultiselect() {
        StatusSingleton statusSingleton = StatusSingleton.getInstance();
        statusSingleton.setMenuCreated(false);

    }
    private void clearSelected() throws ParseException {
        for (int i = 0; i < this.selectedList.size(); i++){
            this.selectedList.get(i).setActivity(Constants.CLEAR_ACTIVITY);
            this.selectedList.get(i).setActivityColor("#" + getResources().getString(R.color.white).substring(3));
            this.selectedList.get(i).setUserId(StatusSingleton.getInstance().getCurrentUserId());
            DBHandler mDBHandler = new DBHandler(getActivity().getBaseContext());
            mDBHandler.setEventEraser(this.selectedList.get(i));
        }
    }

}

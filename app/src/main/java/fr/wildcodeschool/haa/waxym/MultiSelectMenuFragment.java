package fr.wildcodeschool.haa.waxym;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;

import fr.wildcodeschool.haa.waxym.model.DayStuffModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MultiSelectMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiSelectMenuFragment extends Fragment implements AdapterCallbackInterface {

    ArrayList<DayStuffModel> selectedList = new ArrayList<>() ;
    public MultiSelectMenuFragment() {
        // Required empty public constructor
    }

    public static MultiSelectMenuFragment newInstance() {
        MultiSelectMenuFragment fragment = new MultiSelectMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_multi_select_menu, container, false);
        final Button cancel = (Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(MultiSelectMenuFragment.this).commit();
                try {
                    closeMenu();

                    ((MainActivityCallBackInterface)getView().getContext()
                    ).onMethodCallBack();
                }catch (ClassCastException e){

                }
                resetMultiselect();
            }
        });
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
                resetMultiselect();
            }
        });
        return view;
    }
    public  void closeMenu(){
        getActivity().getFragmentManager().beginTransaction().remove(MultiSelectMenuFragment.this).commit();


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void passCheckedDay(Date date,int position, boolean isChecked) {
        DayStuffModel passedDay = new DayStuffModel();
        if (isChecked) {
            passedDay.setDate(date);
            if (position % 2 == 0) {
                passedDay.setMorning(1);
            } else
                passedDay.setAfternoon(1);

            this.selectedList.add(passedDay);
        }  else
            this.selectedList.remove(date);

    }
private void resetMultiselect(){
    StatusSingleton statusSingleton = StatusSingleton.getInstance();
    statusSingleton.setEditMode(false);

}

    /*public interface OnFragmentInteractionListener {
        public void onButtonCancel(boolean bool);
    }*/
}

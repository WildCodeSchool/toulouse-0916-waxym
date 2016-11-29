package fr.wildcodeschool.haa.waxym;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MultiSelectMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiSelectMenuFragment extends Fragment {

    private AdapterCallBackInterface mListener;

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
                    ((AdapterCallBackInterface)getActivity()).onMethodCallBack();
                }catch (ClassCastException e){

                }
            }
        });
        return view;
    }

    public void onButtonPressed() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /*public interface OnFragmentInteractionListener {
        public void onButtonCancel(boolean bool);
    }*/
}

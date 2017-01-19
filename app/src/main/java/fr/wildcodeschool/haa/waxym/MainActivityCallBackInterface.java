package fr.wildcodeschool.haa.waxym;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.wildcodeschool.haa.waxym.model.DayStuffModel;

/**
 * interface to send check days from multiselctFragment to mainactivity
 *
 * and to update spinner on dayclick
 */

public interface MainActivityCallBackInterface {
    void onMethodCallBack();

    void sendSelectedDays(ArrayList<DayStuffModel> passedList);

    void launchDayView();

}


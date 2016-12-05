package fr.wildcodeschool.haa.waxym;

import java.util.ArrayList;
import java.util.Date;

import fr.wildcodeschool.haa.waxym.model.DayStuffModel;

/**
 * Created by tuffery on 29/11/16.
 */

public interface MultiselectCallBackInterface {
    void onMethodCallBack();
    void sendSelectedDays(ArrayList<DayStuffModel> passedList);
}


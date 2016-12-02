package fr.wildcodeschool.haa.waxym;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tuffery on 29/11/16.
 */

public interface MultiselectCallBackInterface {
    void onMethodCallBack();
    void sendSelectedDays(ArrayList<Date> passedList);
}


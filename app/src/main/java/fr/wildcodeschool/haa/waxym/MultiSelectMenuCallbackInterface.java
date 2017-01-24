package fr.wildcodeschool.haa.waxym;

import java.util.ArrayList;
import java.util.Date;


/**
 * used to send date from calendarFragment to multiselectfragment
 */

public interface MultiSelectMenuCallbackInterface {
    void passCheckedDay(Date date, int position, boolean bool);
}

package fr.wildcodeschool.haa.waxym;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tuffery on 15/11/16.
 */

public class DayEvent implements Serializable{
    private Date date;
    private String what;

    public DayEvent(Date date, String what) {
        this.date = date;
        this.what = what;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }
}

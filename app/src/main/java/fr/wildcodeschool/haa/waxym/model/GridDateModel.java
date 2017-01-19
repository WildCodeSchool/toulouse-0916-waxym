package fr.wildcodeschool.haa.waxym.model;

import java.util.Date;

/**
 * used to generate calendar view in gridview
 */

public class GridDateModel {
    private Date date;
    private boolean state;

    public GridDateModel(Date date) {
        this.date = date;
        this.state = false;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}

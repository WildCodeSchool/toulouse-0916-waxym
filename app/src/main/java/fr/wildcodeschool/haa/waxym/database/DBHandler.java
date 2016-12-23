package fr.wildcodeschool.haa.waxym.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by tuffery on 23/11/16.
 */


import android.content.ContentValues;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.wildcodeschool.haa.waxym.Constants;
import fr.wildcodeschool.haa.waxym.model.ActivityItemModel;
import fr.wildcodeschool.haa.waxym.model.DayStuffModel;


public class DBHandler extends SQLiteOpenHelper implements Serializable {
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
    private Context mContext;
    private SQLiteDatabase mDatabase;

    /**
     * Class to communicate with database
     */
    public DBHandler(Context context) {
        super(context, Constants.DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(Constants.DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    // get all events of mentioned user from 1 month of the entered date
    public ArrayList<DayStuffModel> getTwoMonthEvents(int user, Date referenceDate) throws ParseException {
        DayStuffModel dayStuff = null;
        ArrayList<DayStuffModel> eventsList = new ArrayList<>();
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(referenceDate);
        fromDate.setTime(referenceDate);
        fromDate.add(Calendar.MONTH, -1);
        toDate.add(Calendar.MONTH, 1);
        openDatabase();
        // sqlite request : SQLiteQuery: SELECT activity.date, activity_name, contract_number,morning, afternoon, name_user, id_user FROM user,activity,activity_type
        // WHERE user.id_user = activity.id_user AND user.id_user = '1' AND activity.id_activity_type = activity_type.id_activity_type AND date >= entered date -1 month And date <= entered date +1 month
        Cursor cursor = mDatabase.rawQuery("SELECT " + Constants.DATE_ACTIVITY + ", " + Constants.NAME_ACTIVITY + ", " + Constants.CONTRACT_NUMBER +
                ", " + Constants.ACTIVITY_COLOR + "," + Constants.MORNING + ", " + Constants.AFTERNOON + ", " + Constants.NAME_USER + ", " + Constants.ID_USER_USER +
                " FROM " + Constants.USER + "," + Constants.ACTIVITY + "," + Constants.ACTIVITY_TYPE +
                " WHERE " + Constants.ID_USER_USER + " = " + Constants.ID_USER_ACTIVITY +
                " AND " + Constants.ID_USER_ACTIVITY + " = " + "'" + user + "'" +
                " AND " + Constants.ID_ACTIVITY_TYPE_ACTIVITY + " = " + Constants.ID_ACTIVITY_TYPE_ACTIVITY_TYPE +
                " AND " + Constants.DATE_ACTIVITY + " >= " + "'" + convertDatetoString(fromDate.getTime()) + "'" +
                " AND " + Constants.DATE_ACTIVITY + " <= " + "'" + convertDatetoString(toDate.getTime()) + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dayStuff = new DayStuffModel(convertStringToDate(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4),
                    cursor.getInt(5), cursor.getString(6), cursor.getInt(7));
            eventsList.add(dayStuff);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return eventsList;
    }


    // set activity event on date and overwrite if already exist
    public void setEventEraser(DayStuffModel dayEvent) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesActivity = new ContentValues();
        // if date half- day already exists in db for this user
        if (searchAndCompareDate(getTwoMonthEvents(dayEvent.getUserId(), dayEvent.getDate()), dayEvent)) {


            // Activity table
            valuesActivity.put(Constants.NAME_ACTIVITY, dayEvent.getActivity());
            valuesActivity.put(Constants.CONTRACT_NUMBER, dayEvent.getContractNumber());
            valuesActivity.put(Constants.ACTIVITY_COLOR, dayEvent.getActivityColor());
            valuesActivity.put(Constants.ID_ACTIVITY_TYPE, determineActyvityTypeID(dayEvent));

            String SQLActivity = Constants.DATE + " = " + this.sdf.format(dayEvent.getDate()) + " AND " + "id_user" + " = " + dayEvent.getUserId();
            db.update(Constants.ACTIVITY, valuesActivity, SQLActivity, null);

        } else {
            // Activity table
            valuesActivity.put(Constants.NAME_ACTIVITY, dayEvent.getActivity());
            valuesActivity.put(Constants.CONTRACT_NUMBER, dayEvent.getContractNumber());
            valuesActivity.put(Constants.ACTIVITY_COLOR, dayEvent.getActivityColor());
            valuesActivity.put(Constants.ID_ACTIVITY_TYPE, determineActyvityTypeID(dayEvent));
            valuesActivity.put(Constants.DATE, this.sdf.format(dayEvent.getDate()));
            valuesActivity.put(Constants.ID_USER, dayEvent.getUserId());

            db.insert(Constants.ACTIVITY, null, valuesActivity);
            db.close();
        }

    }

    // set activity event on date  if this event don't already exist
    public void setEventCompleter(DayStuffModel dayEvent) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesActivity = new ContentValues();
        // if date half- day already exists in db for this user
        if (!searchAndCompareDate(getTwoMonthEvents(dayEvent.getUserId(), dayEvent.getDate()), dayEvent)) {

            // Activity table
            valuesActivity.put(Constants.NAME_ACTIVITY, dayEvent.getActivity());
            valuesActivity.put(Constants.CONTRACT_NUMBER, dayEvent.getContractNumber());
            valuesActivity.put(Constants.ACTIVITY_COLOR, dayEvent.getActivityColor());
            valuesActivity.put(Constants.ID_ACTIVITY_TYPE, determineActyvityTypeID(dayEvent));
            valuesActivity.put(Constants.DATE, this.sdf.format(dayEvent.getDate()));
            valuesActivity.put(Constants.ID_USER, dayEvent.getUserId());

            db.insert(Constants.ACTIVITY, null, valuesActivity);
            db.close();


        }

    }

    public ArrayList<ActivityItemModel> getUserActivitiesList(int userId) {
        ArrayList<ActivityItemModel> activitiesList = new ArrayList<>();
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.MONTH, -1);
        to.add(Calendar.MONTH, 1);
        String activity, contractNumber, activityColor;
        openDatabase();
       /* "SELECT activity.activity_name , activity.contract_number " +
        "From activity WHERE" +" activity.id_activity_type is null " +
                "AND activity.id_user = 'userId' " +
                "OR activity.id_user is null",null);*/
        Cursor cursor = mDatabase.rawQuery("SELECT " + Constants.NAME_ACTIVITY + ", " + Constants.CONTRACT_NUMBER + ", " + Constants.ACTIVITY_COLOR +
                " FROM " + Constants.ACTIVITY +
                " WHERE " + Constants.ID_ACTIVITY_TYPE_ACTIVITY + " is null " +
                " AND " + Constants.ID_USER_ACTIVITY + " is null", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            activity = cursor.getString(0);
            contractNumber = cursor.getString(1);
            activityColor = cursor.getString(2);
            if(contractNumber == null){
                contractNumber = "";
            }
            activitiesList.add(new ActivityItemModel(contractNumber+" "+ activity,activityColor));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();


        return activitiesList;
    }

    private Date convertStringToDate(String toDate) throws ParseException {
        try {
            return this.sdf.parse(toDate);
        } catch (ParseException e) {
            e.printStackTrace();

        }

        return null;
    }

    private String convertDatetoString(Date toString) {
        return this.sdf.format(toString);

    }

    // search if date is already used with the same half-day
    private boolean searchAndCompareDate(ArrayList<DayStuffModel> list, DayStuffModel arg) {
        for (DayStuffModel day : list) {
            if (day.getDate() != null) {
                if (convertDatetoString(day.getDate()).equals(convertDatetoString(arg.getDate()))
                        && (day.getAfternoon() == arg.getAfternoon()
                        || day.getMorning() == arg.getMorning()))
                    return true;
            }
        }
        return false;
    }

    private int determineActyvityTypeID(DayStuffModel checkedDay) {
        if (checkedDay.getMorning() == checkedDay.getAfternoon()) {
            return Constants.ID_ERROR;
        } else {
            if (checkedDay.getMorning() == 1)
                return 1;
            else
                return 2;
        }
    }

}


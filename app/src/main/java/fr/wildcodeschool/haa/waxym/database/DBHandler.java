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
import java.util.List;

import fr.wildcodeschool.haa.waxym.Constants;
import fr.wildcodeschool.haa.waxym.StatusSingleton;
import fr.wildcodeschool.haa.waxym.model.ActivitiesModel;
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
    public ArrayList<DayStuffModel> getMonthEvents(long user, Date referenceDate) throws ParseException {
        DayStuffModel dayStuff = null;
        ArrayList<DayStuffModel> eventsList = new ArrayList<>();
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(referenceDate);
        fromDate.setTime(referenceDate);
        fromDate.set(Calendar.DAY_OF_MONTH, fromDate.getActualMinimum(Calendar.DAY_OF_MONTH));
        toDate.set(Calendar.DAY_OF_MONTH, toDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        openDatabase();
        // sqlite request : SQLiteQuery: SELECT activity.date, activity_name, contract_number,morning, afternoon, name_user, id_user FROM user,activity,activity_type
        // WHERE user.id_user = activity.id_user AND user.id_user = '1' AND activity.id_activity_type = activity_type.id_activity_type AND date >= entered date -1 month And date <= entered date +1 month
        Cursor cursor = mDatabase.rawQuery("SELECT " + Constants.DATE_ACTIVITY + ", " + Constants.NAME_ACTIVITY + ", " + Constants.CONTRACT_NUMBER +
                ", " + Constants.ACTIVITY_COLOR + "," + Constants.MORNING + ", " + Constants.AFTERNOON + ", " + Constants.NAME_USER + ", " + Constants.ID_USER_USER + ", " + Constants.ACTIVITY_SEND_STATE +
                ", " + Constants.ID_ACTIVITY_ACTIVITY_DETAILS + "," + Constants.CATEGORY_ACTIVITY +
                " FROM " + Constants.USER + "," + Constants.ACTIVITY + "," + Constants.ACTIVITY_TYPE + "," + Constants.ACTIVITY_DETAILS +
                " WHERE " + Constants.ID_USER_USER + " = " + Constants.ID_USER_ACTIVITY +
                " AND " + Constants.ID_USER_ACTIVITY + " = " + "'" + user + "'" +
                " AND " + Constants.ID_ACTIVITY_TYPE_ACTIVITY + " = " + Constants.ID_ACTIVITY_TYPE_ACTIVITY_TYPE +
                " AND " + Constants.ID_ACTIVITY_DETAILS + " = " + Constants.ID_ACTIVITY_ACTIVITY_DETAILS +
                " AND " + Constants.DATE_ACTIVITY + " >= " + "'" + convertDatetoString(fromDate.getTime()) + "'" +
                " AND " + Constants.DATE_ACTIVITY + " <= " + "'" + convertDatetoString(toDate.getTime()) + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dayStuff = new DayStuffModel(convertStringToDate(cursor.getString(0)), cursor.getString(1), cursor.getLong(2), cursor.getString(3), cursor.getInt(4),
                    cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getLong(9), cursor.getLong(10));
            eventsList.add(dayStuff);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return eventsList;
    }

    // get all events of mentioned user of the entered date
    public ArrayList<DayStuffModel> getDayEvents(long user, Date referenceDate) throws ParseException {
        DayStuffModel dayStuff = null;
        ArrayList<DayStuffModel> eventsList = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        date.setTime(referenceDate);
        openDatabase();
        //SELECT activity.date, activity_name, contract_number,morning, afternoon, name_user, user.id_user FROM user,activity,activity_type
        //WHERE user.id_user = activity.id_user AND user.id_user = '1' AND activity.id_activity_type = activity_type.id_activity_type AND activity.date = '2016-11-30'        // WHERE user.id_user = activity.id_user AND user.id_user = '1' AND activity.id_activity_type = activity_type.id_activity_type AND date >= entered date -1 month And date <= entered date +1 month
        Cursor cursor = mDatabase.rawQuery("SELECT " + Constants.DATE_ACTIVITY + ", " + Constants.NAME_ACTIVITY + ", " + Constants.CONTRACT_NUMBER +
                ", " + Constants.ACTIVITY_COLOR + "," + Constants.MORNING + ", " + Constants.AFTERNOON + ", " + Constants.NAME_USER + ", " + Constants.ID_USER_USER + ", " + Constants.ACTIVITY_SEND_STATE +
                ", " + Constants.ID_ACTIVITY_ACTIVITY_DETAILS + "," + Constants.CATEGORY_ACTIVITY +
                " FROM " + Constants.USER + "," + Constants.ACTIVITY + "," + Constants.ACTIVITY_TYPE + "," + Constants.ACTIVITY_DETAILS +
                " WHERE " + Constants.ID_USER_USER + " = " + Constants.ID_USER_ACTIVITY +
                " AND " + Constants.ID_USER_ACTIVITY + " = " + "'" + user + "'" +
                " AND " + Constants.ID_ACTIVITY_TYPE_ACTIVITY + " = " + Constants.ID_ACTIVITY_TYPE_ACTIVITY_TYPE +
                " AND " + Constants.ID_ACTIVITY_DETAILS + " = " + Constants.ID_ACTIVITY_ACTIVITY_DETAILS +
                " AND " + Constants.DATE_ACTIVITY + " = " + "'" + convertDatetoString(date.getTime()) + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dayStuff = new DayStuffModel(convertStringToDate(cursor.getString(0)), cursor.getString(1), cursor.getLong(2), cursor.getString(3), cursor.getInt(4),
                    cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getLong(9), cursor.getLong(10));
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
        if (searchAndCompareDate(getMonthEvents(dayEvent.getUserId(), dayEvent.getDate()), dayEvent)) {


            // Activity table
            valuesActivity.put(Constants.ID_ACTIVITY, dayEvent.getActivityId());
            valuesActivity.put(Constants.ID_ACTIVITY_TYPE, determineActyvityTypeID(dayEvent));
            valuesActivity.put(Constants.SEND_STATE, dayEvent.getSendState());

            String SQLActivity = Constants.DATE + " = '" + this.sdf.format(dayEvent.getDate()) + "' " +
                    "AND " + Constants.ID_USER + " = '" + dayEvent.getUserId() + "'" +
                    "AND " + Constants.ID_ACTIVITY_TYPE + " = '" + determineActyvityTypeID(dayEvent) + "'";
            db.update(Constants.ACTIVITY, valuesActivity, SQLActivity, null);


        } else {
            // Activity table
            valuesActivity.put(Constants.ID_ACTIVITY, dayEvent.getActivityId());
            valuesActivity.put(Constants.ID_ACTIVITY_TYPE, determineActyvityTypeID(dayEvent));
            valuesActivity.put(Constants.DATE, this.sdf.format(dayEvent.getDate()));
            valuesActivity.put(Constants.ID_USER, dayEvent.getUserId());
            valuesActivity.put(Constants.SEND_STATE, dayEvent.getSendState());

            db.insert(Constants.ACTIVITY, null, valuesActivity);
            db.close();
        }

    }

    // set activity event on date  if this event don't already exist
    public void setEventCompleter(DayStuffModel dayEvent) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesActivity = new ContentValues();
        // if date half- day already doesn't exists in db for this user
        if (!searchAndCompareDate(getMonthEvents(dayEvent.getUserId(), dayEvent.getDate()), dayEvent)) {

            // Activity table
            valuesActivity.put(Constants.ID_ACTIVITY, dayEvent.getActivityId());
            valuesActivity.put(Constants.ID_ACTIVITY_TYPE, determineActyvityTypeID(dayEvent));
            valuesActivity.put(Constants.DATE, this.sdf.format(dayEvent.getDate()));
            valuesActivity.put(Constants.ID_USER, dayEvent.getUserId());
            valuesActivity.put(Constants.SEND_STATE, dayEvent.getSendState());

            db.insert(Constants.ACTIVITY, null, valuesActivity);
            db.close();


        }

    }

    public ArrayList<ActivityItemModel> getUserActivitiesList() {
        ArrayList<ActivityItemModel> activitiesList = new ArrayList<>();
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.MONTH, -1);
        to.add(Calendar.MONTH, 1);
        String activity;
        long contractNumber, activityId;
        String activityColor;
        openDatabase();
       /* "SELECT activity.activity_name , activity.contract_number " +
        "From activity WHERE" +" date is null "*/
        Cursor cursor = mDatabase.rawQuery("SELECT " + Constants.NAME_ACTIVITY + ", " + Constants.CONTRACT_NUMBER + ", " + Constants.ACTIVITY_COLOR + "," + Constants.ID_ACTIVITY_ACTIVITY_DETAILS +
                " FROM " + Constants.ACTIVITY + "," + Constants.ACTIVITY_DETAILS +
                " WHERE " + Constants.DATE + " is null " +
                " AND " + Constants.ID_ACTIVITY_ACTIVITY_DETAILS + " = " + Constants.ID_ACTIVITY_DETAILS, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            activity = cursor.getString(0);
            contractNumber = cursor.getLong(1);
            activityColor = cursor.getString(2);
            activityId = cursor.getLong(3);
            activitiesList.add(new ActivityItemModel(contractNumber, activity, activityColor, activityId));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();


        return activitiesList;
    }

    public void updateActivitiesList(List<ActivitiesModel> newActivitiesList) {
        SQLiteDatabase db = this.getWritableDatabase();

        // erase old list
        String oldWhere = Constants.DATE + "   is null";
        db.delete(Constants.ACTIVITY, oldWhere, null);
        ArrayList<Long> existingActivityID = getActivitiesIDList();
        // generate newOne

        for (int i = 0; i < newActivitiesList.size(); i++) {
            for (int j = 0; j < existingActivityID.size(); j++) {
                if (newActivitiesList.get(i).getId() == existingActivityID.get(j)) {
                    newActivitiesList.remove(i);
                    i--;
                }
            }

            addBaseActivity(newActivitiesList.get(i));
        }
        db = this.getWritableDatabase();
        ContentValues valuesActivity = new ContentValues();
        for (int i = 0; i < newActivitiesList.size(); i++) {
            if (newActivitiesList.get(i).getId() != Constants.BLANK_HOLIDAY) {
                valuesActivity.put(Constants.ID_ACTIVITY, newActivitiesList.get(i).getId());
                valuesActivity.put(Constants.ID_USER, StatusSingleton.getInstance().getCurrentUserId());
                db.insert(Constants.ACTIVITY, null, valuesActivity);
            }
        }
        db.close();

    }

    public ArrayList<DayStuffModel> getNotSendedActivities() throws ParseException {
        ArrayList<DayStuffModel> notSendedList = new ArrayList<>();
        DayStuffModel dayStuff;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT " + Constants.DATE_ACTIVITY + ", " + Constants.NAME_ACTIVITY + ", " + Constants.CONTRACT_NUMBER +
                ", " + Constants.ACTIVITY_COLOR + "," + Constants.MORNING + ", " + Constants.AFTERNOON + ", " + Constants.NAME_USER + ", " + Constants.ID_USER_USER + ", " + Constants.SEND_STATE +
                ", " + Constants.ID_ACTIVITY_ACTIVITY_DETAILS + "," + Constants.CATEGORY_ACTIVITY +
                " FROM " + Constants.USER + "," + Constants.ACTIVITY + "," + Constants.ACTIVITY_TYPE + "," + Constants.ACTIVITY_DETAILS +
                " WHERE " + Constants.SEND_STATE +" = '" + Constants.NOT_SENDED + "'" +
                " AND " + Constants.ID_ACTIVITY_ACTIVITY_DETAILS + " = " + Constants.ID_ACTIVITY_DETAILS +
                " AND " + Constants.ID_USER_USER + " = " + Constants.ID_USER_ACTIVITY +
                " AND " + Constants.ID_USER_ACTIVITY +" = '" + StatusSingleton.getInstance().getCurrentUserId() + "'"+
                " AND " + Constants.ID_ACTIVITY_TYPE_ACTIVITY + " = " + Constants.ID_ACTIVITY_TYPE_ACTIVITY_TYPE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dayStuff = new DayStuffModel(convertStringToDate(cursor.getString(0)), cursor.getString(1), cursor.getLong(2), cursor.getString(3), cursor.getInt(4),
                    cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getLong(9), cursor.getLong(10));
            notSendedList.add(dayStuff);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return notSendedList;

    }

    public void addBaseActivity(ActivitiesModel newActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesActivity = new ContentValues();
        valuesActivity.put(Constants.ID_ACTIVITY, newActivity.getId());
        valuesActivity.put(Constants.NAME_ACTIVITY, newActivity.getLabel());
        valuesActivity.put(Constants.CONTRACT_NUMBER, newActivity.getContractId());
        valuesActivity.put(Constants.CATEGORY_ACTIVITY, newActivity.getType());
        valuesActivity.put(Constants.ACTIVITY_COLOR, newActivity.getColor());

        db.insert(Constants.ACTIVITY_DETAILS, null, valuesActivity);
        db.close();
    }

    public ArrayList<Long> getAllUsers() {
        openDatabase();
        ArrayList<Long> userIdList = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("SELECT " + Constants.ID_USER +
                " FROM " + Constants.USER, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(0);
            userIdList.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return userIdList;
    }

    public void newUser(long id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesActivity = new ContentValues();
        valuesActivity.put(Constants.NAME_USER, name);
        valuesActivity.put(Constants.ID_USER, id);
        db.insert(Constants.USER, null, valuesActivity);
    }

    public ArrayList<Long> getActivitiesIDList() {
        ArrayList<Long> idList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT " + Constants.ID_ACTIVITY +
                " FROM " + Constants.ACTIVITY_DETAILS, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(0);
            idList.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return idList;
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


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
import fr.wildcodeschool.haa.waxym.model.DayStuff;


public class DBHandler extends SQLiteOpenHelper implements Serializable {
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
        public ArrayList<DayStuff> getEvents (String user, Date referenceDate) throws ParseException {
            DayStuff dayStuff = null;
            ArrayList<DayStuff> eventsList = new ArrayList<>();
            Calendar fromDate = Calendar.getInstance();
            Calendar toDate = Calendar.getInstance();
            toDate.setTime(referenceDate);
            fromDate.setTime(referenceDate);
            fromDate.add(Calendar.MONTH, -1);
            toDate.add(Calendar.MONTH, 1);
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
            openDatabase();
            // sqlite request : SQLiteQuery: SELECT activity.date, activity_name, contract_number,morning, afternoon, name_user, id_user FROM user,activity,activity_type
            // WHERE user.id_user = activity.id_user AND user.id_user = '1' AND activity.id_activity_type = activity_type.id_activity_type AND date >= entered date -1 month And date <= entered date +1 month
            Cursor cursor = mDatabase.rawQuery("SELECT "+ Constants.DATE_ACTIVITY + ", " + Constants.NAME_ACTIVITY+
                    ", "+ Constants.CONTRACT_NUMBER +"," + Constants.MORNING + ", " + Constants.AFTERNOON +", "+Constants.NAME_USER+ ", "+ Constants.ID_USER_USER +
                    " FROM "+Constants.USER+","+ Constants.ACTIVITY+","+Constants.ACTIVITY_TYPE+
                    " WHERE "+Constants.ID_USER_USER + " = " + Constants.ID_USER_ACTIVITY +
                    " AND " + Constants.NAME_USER + " = " + "'"+user+"'"+
                    " AND " + Constants.ID_ACTIVITY_TYPE_ACTIVITY + " = " + Constants.ID_ACTIVITY_TYPE_ACTIVITY_TYPE +
                    " AND " +Constants.DATE_ACTIVITY + " >= " + "'"+ convertDatetoString(fromDate.getTime()) +"'" +
                    " AND " +Constants.DATE_ACTIVITY + " <= " + "'"+ convertDatetoString(toDate.getTime()) +"'", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                dayStuff = new DayStuff(convertStringToDate(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getInt(3),
                        cursor.getInt(4),cursor.getString(5),cursor.getInt(6));
                eventsList.add(dayStuff);
                cursor.moveToNext();
            }
            cursor.close();
            closeDatabase();
            return eventsList;
        }
        // set activity event on date
        public void setEvent ( DayStuff dayEvent) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues valuesActivity = new ContentValues();
            // if date half- day already exists in db for this user
            if (searchAndCompareDate(getEvents(dayEvent.getUserName(),dayEvent.getDate()),dayEvent)){


                // Activity table
                valuesActivity.put("activity_name",dayEvent.getActivity());
                valuesActivity.put("contract_number",dayEvent.getContractNumber());
                valuesActivity.put("id_activity_type", determineActyvityTypeID(dayEvent));

                String SQLActivity = "date" +" = " + sdf.format(dayEvent.getDate()) + " AND "+ "id_user" + " = " + dayEvent.getUserId();
                db.update(Constants.ACTIVITY,valuesActivity, SQLActivity, null);

            }else {
                // Activity table
                valuesActivity.put("activity_name",dayEvent.getActivity());
                valuesActivity.put("contract_number",dayEvent.getContractNumber());
                valuesActivity.put("id_activity_type", determineActyvityTypeID(dayEvent));
                valuesActivity.put("date", sdf.format(dayEvent.getDate()));
                valuesActivity.put("id_user", dayEvent.getUserId());

                db.insert(Constants.ACTIVITY,null,valuesActivity);
                db.close();
            }

        }
    private Date convertStringToDate(String toDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        try {
            return sdf.parse(toDate);
        }catch (ParseException e) {
            e.printStackTrace();

        }

        return null;
    }
    private String convertDatetoString(Date toString) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        return sdf.format(toString);

    }
    // search if date is already used with the same half-day
    private boolean searchAndCompareDate(ArrayList<DayStuff> list, DayStuff arg){
        for (DayStuff day : list) {
            if (day.getDate() != null) {
                if (convertDatetoString(day.getDate()).equals(convertDatetoString(arg.getDate()))
                        && (day.getAfternoon() == arg.getAfternoon()
                        || day.getMorning() == arg.getMorning()))
                    return true;
            }
        }
        return false;
    }
    private int determineActyvityTypeID(DayStuff checkedDay){
        if(checkedDay.getMorning() == checkedDay.getAfternoon()){
            return Constants.ID_ERROR;
        }else {
            if (checkedDay.getMorning() == 1)
                return 1;
            else
                return 2;
            }
        }

    }


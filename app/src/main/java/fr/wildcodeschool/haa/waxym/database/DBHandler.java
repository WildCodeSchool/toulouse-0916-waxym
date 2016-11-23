package fr.wildcodeschool.haa.waxym.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by tuffery on 23/11/16.
 */


    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import java.io.Serializable;
    import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    // get all events of mentioned used
        public HashMap<Date,DayStuff> getEvents (int user){
            DayStuff dayStuff = null;
            HashMap<Date,DayStuff> eventsList = new HashMap<>();
            openDatabase();
            // sqlite request : SQLiteQuery: SELECT activity.date, activity_name, contract_number,morning, afternoon, name_user FROM user,activity,activity_type
            // WHERE user.id_user = activity.id_user AND user.id_user = '1' AND activity.id_activity_type = activity_type.id_activity_type
            Cursor cursor = mDatabase.rawQuery("SELECT "+ Constants.DATE_ACTIVITY + ", " + Constants.NAME_ACTIVITY+
                    ", "+ Constants.CONTRACT_NUMBER +"," + Constants.MORNING + ", " + Constants.AFTERNOON +", "+Constants.NAME_USER+
                    " FROM "+Constants.USER+","+ Constants.ACTIVITY+","+Constants.ACTIVITY_TYPE+
                    " WHERE "+Constants.ID_USER_USER + " = " + Constants.ID_USER_ACTIVITY +
                    " AND " + Constants.ID_USER_USER + " = " + "'"+user+"'"+
                    " AND " + Constants.ID_ACTIVITY_TYPE_ACTIVITY + " = " +Constants.ID_ACTIVITY_TYPE_ACTIVITY_TYPE, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                dayStuff = new DayStuff(new Date(cursor.getLong(0)*1000),cursor.getString(1),cursor.getString(2),cursor.getInt(3),
                        cursor.getInt(4),cursor.getString(5));
                eventsList.put(dayStuff.getDate(),dayStuff);
                cursor.moveToNext();
            }
            cursor.close();
            closeDatabase();
            return eventsList;
        }
    }


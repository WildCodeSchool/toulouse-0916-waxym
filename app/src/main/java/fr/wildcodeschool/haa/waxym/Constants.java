package fr.wildcodeschool.haa.waxym;

/**
 * Created by tuffery on 23/11/16.
 */

public class Constants {
    public final static String DBNAME ="database.sqlite";
    //
    public final static String DATE_FORMAT  = "yyyy-MM-dd";
    // table columns names
    public final static String ACTIVITY_COLOR = "activity_color";
    public final static String DATE = "date";
    public final static String DATE_ACTIVITY = "activity.date";
    public final static String NAME_ACTIVITY = "activity_name";
    public final static String CONTRACT_NUMBER = "contract_number";
    public final static String MORNING = "morning";
    public final static String AFTERNOON = "afternoon";
    public final static String ID_USER = "id_user";
    public final static String ID_USER_ACTIVITY = "activity.id_user";
    public final static String ID_USER_USER = "user.id_user";
    public final static String NAME_USER = "name_user";
    public final static String ID_ACTIVITY_TYPE = "id_activity_type";
    public final static String ID_ACTIVITY_TYPE_ACTIVITY_TYPE = "activity_type.id_activity_type";
    public final static String ID_ACTIVITY_TYPE_ACTIVITY = "activity.id_activity_type";
    public final static String ACTIVITY_SEND_STATE = "activity.send_state";

    public final static String SEND_STATE = "send_state";
    // table names
    public final static String ACTIVITY = "activity";
    public final static String ACTIVITY_TYPE = "activity_type";
    public final static String USER = "user";

    //Id error
    public final static int ID_ERROR = 404;

    //other
    public final static String POSITION_KEY= "Month";
    public final static String SELECTED_DAYS = "selected";
    public final static int TOTAL_SLIDES = 60;
    public final static String CLEAR_ACTIVITY = "clearEvilOfDoom";
    public final static int NOT_SENDED = 1;

    //Code server
    public final static int OK = 0;
    public final static int USER_KNOWED = 1001;
    public final static int STATUS_CODE_OK = 200;
    public final static int DSTART_DTEND_UNKNOW = 1002;
    public final static int incoherent_content = 1003;
    public final static String BASE_URL = "http://www.waxym.com/employeah/v1/";


}

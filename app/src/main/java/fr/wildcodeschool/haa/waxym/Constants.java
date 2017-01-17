package fr.wildcodeschool.haa.waxym;

/**
 * Created by tuffery on 23/11/16.
 */

public class Constants {
    public final static String DBNAME = "database.sqlite";
    //
    public final static String DATE_FORMAT = "yyyy-MM-dd";
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
    public final static String CATEGORY_ACTIVITY = "activity_category";
    public final static String ID_ACTIVITY = "id_activity_details";
    public final static String ID_ACTIVITY_ACTIVITY_DETAILS = "activity.id_activity_details";
    public final static String ID_ACTIVITY_DETAILS = "activity_details.id_activity_details";
    public final static String SEND_STATE = "send_state";
    // table names
    public final static String ACTIVITY = "activity";
    public final static String ACTIVITY_TYPE = "activity_type";
    public final static String USER = "user";
    public final static String ACTIVITY_DETAILS = "activity_details";

    //Id error
    public final static int ID_ERROR = 404;

    //other
    public final static String POSITION_KEY = "Month";
    public final static String SELECTED_DAYS = "selected";
    public final static int TOTAL_SLIDES = 120;
    public final static String CLEAR_ACTIVITY = "clearEvilOfDoom";
    public final static int NOT_SENDED = 1;

    //Code server
    public final static int BLANK_HOLIDAY = -1;
    public final static int OK = 0;
    public final static int USER_KNOWNED = 1001;
    public final static int STATUS_CODE_OK = 200;
    public final static int DSTART_DTEND_UNKNOW = 1002;
    public final static int incoherent_content = 1003;
    public final static String BASE_URL = "http://www.waxym.com/employeah/v1/";

    //ActivitiesModel Constants
    public final static String ACTIVITIES_MODEL_ID = "id";
    public final static String ACTIVITIES_MODEL_TYPE = "type";
    public final static String ACTIVITIES_MODEL_LABEL = "label";
    public final static String ACTIVITIES_MODEL_CLIENT_ID = "clientId";
    public final static String ACTIVITIES_MODEL_CONTRACT_ID = "contractId";
    public final static String ACTIVITIES_MODEL_COLOR = "color";

    //ActivityModel Constants
    public final static String ACTIVITY_MODEL_TYPE = "type";
    public final static String ACTIVITY_MODEL_LABEL = "label";
    public final static String ACTIVITY_MODEL_CLIENT_ID = "clientId";
    public final static String ACTIVITY_MODEL_CONTRACT_ID = "contractId";
    public final static String ACTIVITY_MODEL_COLOR = "color";

    //DayActivitiesMModel Constants
    public final static String DAY_ACTIVITIES_MODEL_DAY = "day";
    public final static String DAY_ACTIVITIES_MODEL_AM_ACTIVITY_ID = "amActivityId";
    public final static String DAY_ACTIVITIES_MODEL_PM_ACTIVITY_ID = "pmActivityId";

    //IdModel Constants
    public final static String ID_MODEL = "day";

    //ListOfActivitiesModel Constants
    public final static String LIST_OF_ACTIVITIES_MODEL = "activities";

    //UserModel Constants
    public final static String USER_MODEL_LOGIN = "login";
    public final static String USER_MODEL_PWD = "pwd";

}
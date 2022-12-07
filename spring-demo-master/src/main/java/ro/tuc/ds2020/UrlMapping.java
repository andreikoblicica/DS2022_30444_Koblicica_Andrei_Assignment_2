package ro.tuc.ds2020;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String AUTH = API_PATH + "/auth";

    public static final String LOG_IN = "/log-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String DEVICES = API_PATH+ "/admin/devices";
    public static final String DEVICE_ID="/{id}";
    public static final String USER_DEVICES=API_PATH+"/devices/{id}";

    public static final String USERS = API_PATH+"/admin/users";
    public static final String USER_ID = "/{id}";

    public static final String FILTERS = API_PATH+"/devices/{id}/{date}";

}

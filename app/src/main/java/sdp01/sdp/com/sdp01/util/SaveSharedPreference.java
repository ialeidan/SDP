package sdp01.sdp.com.sdp01.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import sdp01.sdp.com.sdp01.LoginActivity;
import sdp01.sdp.com.sdp01.MyApplication;

/**
 * Created by ibrahimaleidan on 21/02/2018.
 */

public class SaveSharedPreference extends Application{

    private static Context context = MyApplication.getAppContext();

    private static final String PREF= "sdp01.sdp.com.sdp01";
    private static final String PREF_USER_NAME= "sdp01.sdp.com.sdp01.username";
    private static final String PREF_USER_EMAIL= "sdp01.sdp.com.sdp01.email";
    private static final String PREF_USER_IDTOKEN= "sdp01.sdp.com.sdp01.idToken";
    private static final String PREF_USER_REFRESHTOKEN= "sdp01.sdp.com.sdp01.refreshToken";
    private static final String PREF_USER_EXPIRESIN= "sdp01.sdp.com.sdp01.expiresIn";
    private static final String PREF_USER_LOCALID= "sdp01.sdp.com.sdp01.localId";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return ctx.getSharedPreferences(
                PREF, Context.MODE_PRIVATE);
    }

    public static void setString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    public static void setUserLoginInfo(Context ctx, String email, String idToken, String refreshToken,
                                        String expiresIn, String localId) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, email);
        editor.putString(PREF_USER_IDTOKEN, idToken);
        editor.putString(PREF_USER_REFRESHTOKEN, refreshToken);
        editor.putString(PREF_USER_EXPIRESIN, expiresIn);
        editor.putString(PREF_USER_LOCALID, localId);
        editor.commit();
    }

//    public static String getUserLoginInfo(Context ctx) {
//        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
//    }


    public static boolean isLoggedIn(Context ctx){
        String email = getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
        return (email.length() != 0);
    }

}

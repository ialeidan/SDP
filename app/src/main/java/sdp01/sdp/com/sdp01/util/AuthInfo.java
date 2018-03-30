package sdp01.sdp.com.sdp01.util;

import android.content.Context;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Created by ibrahimaleidan on 11/03/2018.
 */

public class AuthInfo extends JSONObject {

    private static final String PREF_USER_NAME= "sdp01.sdp.com.sdp01.username";
    private static final String PREF_USER_EMAIL= "sdp01.sdp.com.sdp01.email";
    private static final String PREF_USER_PHONE= "sdp01.sdp.com.sdp01.phone";
    private static final String PREF_USER_ACCESSTOKEN= "sdp01.sdp.com.sdp01.access_token";
    private static final String PREF_USER_USERID= "sdp01.sdp.com.sdp01.user_id";

    public AuthInfo(){

    }

    // Setters:
    public static void setUserID(String userID) {
        SaveSharedPreference.setString(PREF_USER_USERID, userID);
    }

    public static void setUserName(String userName) {
        SaveSharedPreference.setString(PREF_USER_NAME, userName);
    }

    public static void setUserEmail(String userEmail) {
        SaveSharedPreference.setString(PREF_USER_EMAIL, userEmail);
    }

    public static void setAccessToken(String accessToken) {
        SaveSharedPreference.setString(PREF_USER_ACCESSTOKEN, accessToken);
    }

    public static void setPhone(String phone) {
        SaveSharedPreference.setString(PREF_USER_PHONE, phone);
    }


    // Getters:
    public static String getUserID() {
        return SaveSharedPreference.getString(PREF_USER_USERID);
    }

    public static String getUserName() {
        return SaveSharedPreference.getString(PREF_USER_NAME);
    }

    public static String getUserEmail() {
        return SaveSharedPreference.getString(PREF_USER_EMAIL);
    }

    public static String getAccessToken() {
        return SaveSharedPreference.getString(PREF_USER_ACCESSTOKEN);
    }

    public static String getPhone() {
        return SaveSharedPreference.getString(PREF_USER_PHONE);
    }


    public static void clearToken() {
        setUserID(null);
        setUserName(null);
        setUserEmail(null);
        setAccessToken(null);
        setPhone(null);
    }

    public static boolean isLoggedIn(){
        String access_token = getAccessToken();
        return (access_token.length() != 0);
    }

}

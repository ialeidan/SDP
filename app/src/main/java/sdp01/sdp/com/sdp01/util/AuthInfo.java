package sdp01.sdp.com.sdp01.util;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Created by ibrahimaleidan on 11/03/2018.
 */

public class AuthInfo extends JSONObject {

    public AuthInfo(){

    }

    // Setters:
    public static void setUserID(String userID) {
        SaveSharedPreference.setString("userID", userID);
    }

    public static void setUserName(String userName) {
        SaveSharedPreference.setString("userName", userName);
    }

    public static void setUserLogin(String userLogin) {
        SaveSharedPreference.setString("userEmail", userLogin);
    }

    public static void setUserPassword(String userPassword) {
        try {
            byte[] utf8str = userPassword.getBytes("UTF-8");
            String base64Encoded = Base64.getEncoder().encodeToString(utf8str);
            SaveSharedPreference.setString("userPassword", base64Encoded);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void setTokenType(String tokenType) {
        SaveSharedPreference.setString("tokenType", tokenType);
    }

    public static void setAccessToken(String accessToken) {
        SaveSharedPreference.setString("accessToken", accessToken);
    }

    public static void setRefreshToken(String refreshToken) {
        SaveSharedPreference.setString("refreshToken", refreshToken);
    }


    // Getters:
    public static String getUserID() {
        return SaveSharedPreference.getString("userID");
    }

    public static String getUserName() {
        return SaveSharedPreference.getString("userName");
    }

    public static String getUserLogin() {
        return SaveSharedPreference.getString("userEmail");
    }

    public static String getUserPassword() {
        String decodeStr = SaveSharedPreference.getString("userPassword");
        byte[] base64Decoded = Base64.getDecoder().decode(decodeStr);
        try {
            return new String(base64Decoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTokenType() {
        return SaveSharedPreference.getString("tokenType");
    }

    public static String getAccessToken() {
        return SaveSharedPreference.getString("accessToken");
    }

    public static String getRefreshToken() {
        return SaveSharedPreference.getString("refreshToken");
    }




    public static void clearToken() {
        setAccessToken(null);
        setRefreshToken(null);
        setTokenType(null);
    }

}

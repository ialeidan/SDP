package sdp01.sdp.com.sdp01.util;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import sdp01.sdp.com.sdp01.data_source.DataSourceRequestListner;
import sdp01.sdp.com.sdp01.data_source.ErrorCode;

/**
 * Created by ibrahimaleidan on 20/02/2018.
 */

public class Networking {

    //TODO: Change JSONObjects to our server specifications.
    private final static String URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/";
    private final static String API_KEY = "AIzaSyAyi-cQ6qbHVLpgl4ZQSmp35ZDJwLsXmpw";

    // Login with email and password
    public static void authenticateUser(final String email, final String password, final DataSourceRequestListner listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("returnSecureToken", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(URL + "verifyPassword?key=" + API_KEY)
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String idToken, refreshToken, expiresIn, localId;

                        try {
                            idToken = response.getString("idToken");
                            refreshToken = response.getString("refreshToken");
                            expiresIn = response.getString("expiresIn");
                            localId = response.getString("localId");

                            AuthInfo.setUserID(localId);
                            AuthInfo.setUserLogin(email);
                            AuthInfo.setUserPassword(password);
                            AuthInfo.setRefreshToken(refreshToken);
                            AuthInfo.setAccessToken(idToken);
                            AuthInfo.setTokenType(expiresIn);       //TODO: Change!


                            listener.onResponse(response);

//                            Log.e("success_signIn", refreshToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-13, "Error Signing In", e.getMessage()));
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
//                        Log.e("error_signIn", anError.getErrorBody());

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            if (error != null) {
                                code = error.getJSONObject("error").getJSONArray("errors").getJSONObject(0).getString("message");

                                if (code.equals("EMAIL_NOT_FOUND")) {
                                    listener.onError(new ErrorCode(-11, "EMAIL_NOT_FOUND", ""));
                                } else if (code.equals("INVALID_PASSWORD")) {
                                    listener.onError(new ErrorCode(-12, "INVALID_PASSWORD", ""));
                                } else {
                                    listener.onError(new ErrorCode(-13, "Error Signing In", ""));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-13, "Error Signing In", e.getMessage()));
                        }
                    }
                });
    }

    // Sign Up new user.
    public static void signUp(String username, String phone, final String email, final String password, final DataSourceRequestListner listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("returnSecureToken", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(URL + "signupNewUser?key=" + API_KEY)
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String idToken, refreshToken, expiresIn, localId;

                        try {
                            idToken = response.getString("idToken");
                            refreshToken = response.getString("refreshToken");
                            expiresIn = response.getString("expiresIn");
                            localId = response.getString("localId");

                            AuthInfo.setUserID(localId);
                            AuthInfo.setUserLogin(email);
                            AuthInfo.setUserPassword(password);
                            AuthInfo.setRefreshToken(refreshToken);
                            AuthInfo.setAccessToken(idToken);
                            AuthInfo.setTokenType(expiresIn);       //TODO: Change!


                            listener.onResponse(response);

//                            Log.e("success_signUp", refreshToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-23, "Error Signing Up", e.getMessage()));
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            if (error != null) {
                                code = error.getJSONObject("error").getJSONArray("errors").getJSONObject(0).getString("message");

                                if (code.equals("EMAIL_EXISTS")) {
                                    listener.onError(new ErrorCode(-21, "EMAIL_EXISTS", ""));
                                } else {
                                    listener.onError(new ErrorCode(-23, "Error Signing Up", ""));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-23, "Error Signing Up", e.getMessage()));
                        }
                    }
                });

    }

    // Sign Up new SP.
    public static void signUpSP(String username, String phone, final String email, final String password, final DataSourceRequestListner listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("returnSecureToken", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(URL + "signupNewUser?key=" + API_KEY)
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String idToken, refreshToken, expiresIn, localId;

                        try {
                            idToken = response.getString("idToken");
                            refreshToken = response.getString("refreshToken");
                            expiresIn = response.getString("expiresIn");
                            localId = response.getString("localId");

                            AuthInfo.setUserID(localId);
                            AuthInfo.setUserLogin(email);
                            AuthInfo.setUserPassword(password);
                            AuthInfo.setRefreshToken(refreshToken);
                            AuthInfo.setAccessToken(idToken);
                            AuthInfo.setTokenType(expiresIn);       //TODO: Change!


                            listener.onResponse(response);

//                            Log.e("success_signUp", refreshToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-23, "Error Signing Up", e.getMessage()));
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            if (error != null) {
                                code = error.getJSONObject("error").getJSONArray("errors").getJSONObject(0).getString("message");

                                if (code.equals("EMAIL_EXISTS")) {
                                    listener.onError(new ErrorCode(-21, "EMAIL_EXISTS", ""));
                                } else {
                                    listener.onError(new ErrorCode(-23, "Error Signing Up", ""));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-23, "Error Signing Up", e.getMessage()));
                        }
                    }
                });
    }
}

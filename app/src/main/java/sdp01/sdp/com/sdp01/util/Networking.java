package sdp01.sdp.com.sdp01.util;


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


    private final static String URL = "https://scala-sdp.herokuapp.com/";

    // Login with email and password
    public static void authenticateUser(final String email, final String password, final DataSourceRequestListner listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(URL + "auth/login")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String user_id, access_token;

                        try {
                            user_id = response.getString("user_id");
                            access_token = response.getString("access_token");

                            AuthInfo.setUserID(user_id);
                            AuthInfo.setUserEmail(email);
                            AuthInfo.setAccessToken(access_token);

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

//                            code = error.getJSONObject("error").getJSONArray("errors").getJSONObject(0).getString("message");
                            code = error.getString("message");
                            if (code.equals("EMAIL_NOT_FOUND")) {
                                listener.onError(new ErrorCode(-11, "EMAIL_NOT_FOUND", ""));
                            } else if (code.equals("INVALID_PASSWORD")) {
                                listener.onError(new ErrorCode(-12, "INVALID_PASSWORD", ""));
                            } else {
                                listener.onError(new ErrorCode(-13, "Error Signing In", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-13, "Error Signing In", e.getMessage()));
                        }
                    }
                });
    }

    // Sign Up new user.
    public static void signUp(final String username, final String phone, final String email, final String password, final DataSourceRequestListner listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("phone", phone);
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(URL + "auth/customer/register")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String user_id, access_token;

                        try {
                            user_id = response.getString("user_id");
                            access_token = response.getString("access_token");

                            AuthInfo.setUserID(user_id);
                            AuthInfo.setUserEmail(email);
                            AuthInfo.setUserName(username);
                            AuthInfo.setAccessToken(access_token);
                            AuthInfo.setPhone(phone);


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

//                            code = error.getJSONObject("error").getJSONArray("errors").getJSONObject(0).getString("message");
                            code = error.getString("message");
                            if (code.equals("EMAIL_EXISTS")) {
                                listener.onError(new ErrorCode(-21, "EMAIL_EXISTS", ""));
                            } else {
                                listener.onError(new ErrorCode(-23, "Error Signing Up", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-23, "Error Signing Up", e.getMessage()));
                        }
                    }
                });

    }

    // Sign Up new SP.
    public static void signUpSP(final String username, final String phone, final String email, final String password, final DataSourceRequestListner listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("phone", phone);
            jsonObject.put("username", username);
//            jsonObject.put("device", device);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(URL + "auth/sp/register")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String user_id, access_token;

                        try {
                            user_id = response.getString("user_id");
                            access_token = response.getString("access_token");

                            AuthInfo.setUserID(user_id);
                            AuthInfo.setUserEmail(email);
                            AuthInfo.setUserName(username);
                            AuthInfo.setAccessToken(access_token);
                            AuthInfo.setPhone(phone);


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

//                            code = error.getJSONObject("error").getJSONArray("errors").getJSONObject(0).getString("message");
                            code = error.getString("message");

                            if (code.equals("EMAIL_EXISTS")) {
                                listener.onError(new ErrorCode(-21, "EMAIL_EXISTS", ""));
                            } else {
                                listener.onError(new ErrorCode(-23, "Error Signing Up", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-23, "Error Signing Up", e.getMessage()));
                        }
                    }
                });
    }
}

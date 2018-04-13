package sdp01.sdp.com.sdp01.util;


import android.location.Location;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import sdp01.sdp.com.sdp01.data_source.DataSourceRequestListener;
import sdp01.sdp.com.sdp01.data_source.ErrorCode;

/**
 * Created by ibrahimaleidan on 20/02/2018.
 */

public class Networking {


    // Error Code -1, means NOT_AUTHENTICATED

    private final static String URL = "https://scala-sdp.herokuapp.com/";

    // Login with email and password
    public static void authenticateUser(final String email, final String password, final DataSourceRequestListener listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-13, "Error Signing In", e.getMessage()));
            return;
        }


        AndroidNetworking.post(URL + "auth/login")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String user_id, access_token, type;

                        try {
                            user_id = response.getString("user_id");
                            access_token = response.getString("access_token");
                            type = response.getString("type");

                            AuthInfo.setUserID(user_id);
                            AuthInfo.setUserEmail(email);
                            AuthInfo.setAccessToken(access_token);
                            AuthInfo.setUserType(type);


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
    public static void signUp(final String username, final String phone, final String email, final String password, final DataSourceRequestListener listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("phone", phone);
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-23, "Error Signing Up", e.getMessage()));
            return;
        }

        AndroidNetworking.post(URL + "auth/customer/register")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String user_id, access_token, type;

                        try {
                            user_id = response.getString("user_id");
                            access_token = response.getString("access_token");
                            type = response.getString("type");

                            AuthInfo.setUserID(user_id);
                            AuthInfo.setUserEmail(email);
                            AuthInfo.setUserName(username);
                            AuthInfo.setAccessToken(access_token);
                            AuthInfo.setPhone(phone);
                            AuthInfo.setUserType(type);

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
    public static void signUpSP(final String username, final String phone, final String email, final String password, final DataSourceRequestListener listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("phone", phone);
            jsonObject.put("username", username);
            jsonObject.put("device", "test"); //TODO: Change.
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-23, "Error Signing Up", e.getMessage()));
            return;
        }

        AndroidNetworking.post(URL + "auth/sp/register")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String user_id, access_token, type;

                        try {
                            user_id = response.getString("user_id");
                            access_token = response.getString("access_token");
                            type = response.getString("type");

                            AuthInfo.setUserID(user_id);
                            AuthInfo.setUserEmail(email);
                            AuthInfo.setUserName(username);
                            AuthInfo.setAccessToken(access_token);
                            AuthInfo.setPhone(phone);
                            AuthInfo.setUserType(type);


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

    // Get History
    public static void getHistory(final DataSourceRequestListener listener) {

        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-33, "Error Getting History", e.getMessage()));
            return;
        }

        AndroidNetworking.get(URL + "history")
//                .addHeaders("Authorization", access_token)
                .addQueryParameter(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

//                            code = error.getJSONObject("error").getJSONArray("errors").getJSONObject(0).getString("message");
                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-33, "Error Getting History", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-33, "Error Getting History", e.getMessage()));
                        }
                    }
                });
    }


    public static void getStatus(final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("user_id", user_id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            listener.onError(new ErrorCode(-43, "Error Getting Status", e.getMessage()));
//            return;
//        }


        AndroidNetworking.get(URL + "user/status?user_id={user_id}")
//                .addHeaders("Authorization", access_token)
//                .addQueryParameter(jsonObject)
                .addPathParameter("user_id", user_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-43, "Error Getting Status", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-43, "Error Getting Status", e.getMessage()));
                        }
                    }
                });

    }

    public static void getSPStatus(final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("user_id", user_id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            listener.onError(new ErrorCode(-43, "Error Getting Status", e.getMessage()));
//            return;
//        }


        AndroidNetworking.get(URL + "sp/status?user_id={user_id}")
//                .addHeaders("Authorization", access_token)
//                .addQueryParameter(jsonObject)
                .addPathParameter("user_id", user_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-43, "Error Getting Status", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-43, "Error Getting Status", e.getMessage()));
                        }
                    }
                });

    }

    //Customer APIs:
    public static void sendRequest(final String service, final Location location, final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }

        JSONObject locationJSON = new JSONObject();
        try {
            locationJSON.put("latitude", Double.toString(location.getLatitude()));
            locationJSON.put("longitude", Double.toString(location.getLongitude()));
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-103, "Error Sending Request", e.getMessage()));
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("service", service);
            jsonObject.put("location", locationJSON);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-103, "Error Sending Request", e.getMessage()));
            return;
        }


        AndroidNetworking.post(URL + "customer/request")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-103, "Error Sending Request", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-103, "Error Sending Request", e.getMessage()));
                        }
                    }
                });
    }


    public static void getBids(final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-53, "Error Getting Bids", e.getMessage()));
            return;
        }


        AndroidNetworking.get(URL + "customer/bids?user_id={user_id}")
//                .addHeaders("Authorization", access_token)
//                .addQueryParameter(jsonObject)
                .addPathParameter("user_id", user_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-53, "Error Getting Bids", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-53, "Error Getting Bids", e.getMessage()));
                        }
                    }
                });

    }

    public static void chooseBid(final String bid_id, final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("bid_id", bid_id);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-113, "Error Choosing Bid", e.getMessage()));
            return;
        }


        AndroidNetworking.post(URL + "customer/select_bid")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-113, "Error Choosing Bid", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-113, "Error Choosing Bid", e.getMessage()));
                        }
                    }
                });
    }


    public static void getService(final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("user_id", user_id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            listener.onError(new ErrorCode(-63, "Error Getting Service", e.getMessage()));
//            return;
//        }


        AndroidNetworking.get(URL + "customer/service?user_id={user_id}")
//                .addHeaders("Authorization", access_token)
//                .addQueryParameter(jsonObject)
                .addPathParameter("user_id", user_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-63, "Error Getting Service", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-63, "Error Getting Service", e.getMessage()));
                        }
                    }
                });

    }

    public static void sendPayment(final String payment, final String rating, final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("payment", payment);
            jsonObject.put("rating", rating);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-123, "Error Sending Payment", e.getMessage()));
            return;
        }


        AndroidNetworking.post(URL + "customer/pay")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-123, "Error Sending Payment", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-123, "Error Sending Payment", e.getMessage()));
                        }
                    }
                });
    }

    // SP APIs:
    public static void endService(final String request_id, final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("request_id", request_id);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-133, "Error Ending Service", e.getMessage()));
            return;
        }


        AndroidNetworking.post(URL + "sp/finish")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-133, "Error Ending Service", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-133, "Error Ending Service", e.getMessage()));
                        }
                    }
                });
    }

    public static void sendBid(final String request_id, final String price, final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("request_id", request_id);
            jsonObject.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(new ErrorCode(-143, "Error Sending Bid", e.getMessage()));
            return;
        }


        AndroidNetworking.post(URL + "sp/bid")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-143, "Error Sending Bid", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-143, "Error Sending Bid", e.getMessage()));
                        }
                    }
                });
    }

    public static void getRequests(final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("user_id", user_id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            listener.onError(new ErrorCode(-73, "Error Getting Requests", e.getMessage()));
//            return;
//        }


        AndroidNetworking.get(URL + "sp/requests?user_id={user_id}")
//                .addHeaders("Authorization", access_token)
//                .addQueryParameter(jsonObject)
                .addPathParameter("user_id", user_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-73, "Error Getting Requests", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-73, "Error Getting Requests", e.getMessage()));
                        }
                    }
                });

    }

    //TODO: Check if requst_id really needed.
    public static void getBidStatus(final String request_id, final DataSourceRequestListener listener) {
        String user_id = AuthInfo.getUserID();
        String access_token = AuthInfo.getAccessToken();

        if (user_id.isEmpty() || access_token.isEmpty()) {
            listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
            return;
        }

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("user_id", user_id);
//            jsonObject.put("request_id", request_id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            listener.onError(new ErrorCode(-83, "Error Getting Bid Status", e.getMessage()));
//            return;
//        }


        AndroidNetworking.get(URL + "sp/check_bid?user_id={user_id}&request_id={request_id}")
//                .addHeaders("Authorization", access_token)
//                .addQueryParameter(jsonObject)
                .addPathParameter("user_id", user_id)
                .addPathParameter("request_id", request_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        JSONObject error;
                        String code;

                        try {
                            error = new JSONObject(anError.getErrorBody());

                            code = error.getString("message");

                            if (code.equals("NOT_AUTHENTICATED")) {
                                listener.onError(new ErrorCode(-1, "NOT_AUTHENTICATED", ""));
                            } else {
                                listener.onError(new ErrorCode(-83, "Error Getting Bid Status", ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(new ErrorCode(-83, "Error Getting Bid Status", e.getMessage()));
                        }
                    }
                });

    }

}

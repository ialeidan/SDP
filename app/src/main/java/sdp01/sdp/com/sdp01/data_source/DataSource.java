package sdp01.sdp.com.sdp01.data_source;

import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import sdp01.sdp.com.sdp01.util.Networking;

/**
 * Created by ibrahimaleidan on 11/03/2018.
 */

public class DataSource {


    // MARK: - Authorization.

    public static void signinUser(String email, String password, final DataSourceRequestListner listner) {
        String userEmail = email.replaceAll("\\s+","");
        String userPassword = password.replaceAll("\\s+","");

        ErrorCode error = validationSignInFields(userEmail, userPassword);
        if (error != null) {
            listner.onError(error);
        } else {
            Networking.authenticateUser(userEmail, userPassword, new DataSourceRequestListner() {
                @Override
                public void onResponse(JSONObject response) {
                    listner.onResponse(response);
                }

                @Override
                public void onError(ErrorCode anError) {
                    listner.onError(anError);
                }
            });
        }

    }
    private static ErrorCode validationSignInFields(String email, String password){

        if (TextUtils.isEmpty(email)) {
            ErrorCode error = new ErrorCode(-102, "Email is required", "");
            return error;
        } else if (!(isEmailValid(email))){
            ErrorCode error = new ErrorCode(-102, "Please enter valid email", "");
            return error;
        }

        if (TextUtils.isEmpty(password)) {
            ErrorCode error = new ErrorCode(-103, "Password is required", "");
            return error;
        } else if (!(isPasswordValid(password))) {
            ErrorCode error = new ErrorCode(-103, "Password must be more than 5 symbols", "");
            return error;
        }
        return null;
    }


    private static ErrorCode validationSignUpFields(String name, String phone, String email, String password){

        if (TextUtils.isEmpty(name)) {
            ErrorCode error = new ErrorCode(-101, "Username is required", "");
            return error;
        }



        if (TextUtils.isEmpty(email)) {
            ErrorCode error = new ErrorCode(-102, "Email is required", "");
            return error;
        } else if (!(isEmailValid(email))){
            ErrorCode error = new ErrorCode(-102, "Please enter valid email", "");
            return error;
        }

        if (TextUtils.isEmpty(password)) {
            ErrorCode error = new ErrorCode(-103, "Password is required", "");
            return error;
        } else if (!(isPasswordValid(password))) {
            ErrorCode error = new ErrorCode(-103, "Password must be more than 5 symbols", "");
            return error;
        }

        return null;
    }



    // General
    private static boolean isEmailValid(String email){
        email = email.replaceAll("\\s+","");
        return (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    private static boolean isPasswordValid(String password) {
        password = password.replaceAll("\\s+","");
        return password.length() > 5;
    }
}

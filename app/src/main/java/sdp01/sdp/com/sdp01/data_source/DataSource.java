package sdp01.sdp.com.sdp01.data_source;

import android.text.TextUtils;
import org.json.JSONObject;
import sdp01.sdp.com.sdp01.util.Networking;

/**
 * Created by ibrahimaleidan on 11/03/2018.
 */

public class DataSource {


    // MARK: - Authorization.

    public static void signinUser(String email, String password, final DataSourceRequestListner listner) {
        String userEmail = email.replaceAll("\\s+", "");
        String userPassword = password.replaceAll("\\s+", "");

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

    private static ErrorCode validationSignInFields(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            return new ErrorCode(-102, "Email is required", "");
        } else if (!(isEmailValid(email))) {
            return new ErrorCode(-102, "Please enter valid email", "");
        }

        if (TextUtils.isEmpty(password)) {
            return new ErrorCode(-103, "Password is required", "");
        } else if (!(isPasswordValid(password))) {
            return new ErrorCode(-103, "Password must be more than 5 symbols", "");
        }
        return null;
    }

    public static void signUpUser(String username, String phone, String email, String password, final DataSourceRequestListner listner) {
        String userPhone = phone.replaceAll("\\s+", "");
        String userEmail = email.replaceAll("\\s+", "");
        String userPassword = password.replaceAll("\\s+", "");


        ErrorCode error = validationSignUpFields(username, userPhone, userEmail, userPassword);
        if (error != null) {
            listner.onError(error);
        } else {
            Networking.signUp(username, userPhone, userEmail, userPassword, new DataSourceRequestListner() {
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

    public static void signUpSP(String username, String phone, String email, String password, final DataSourceRequestListner listner) {
        String userPhone = phone.replaceAll("\\s+", "");
        String userEmail = email.replaceAll("\\s+", "");
        String userPassword = password.replaceAll("\\s+", "");

        ErrorCode error = validationSignUpFields(username, userPhone, userEmail, userPassword);
        if (error != null) {
            listner.onError(error);
        } else {
            Networking.signUpSP(username, userPhone, userEmail, userPassword, new DataSourceRequestListner() {
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

    private static ErrorCode validationSignUpFields(String name, String phone, String email, String password) {

        if (TextUtils.isEmpty(name)) {
            return new ErrorCode(-101, "Username is required", "");
        }

        if (TextUtils.isEmpty(phone)) {
            return new ErrorCode(-104, "Phone number is required", "");
        } else if (!(isPhoneValid(phone))) {
            return new ErrorCode(-104, "Please enter valid phone number", "");
        }

        if (TextUtils.isEmpty(email)) {
            return new ErrorCode(-102, "Email is required", "");
        } else if (!(isEmailValid(email))) {
            return new ErrorCode(-102, "Please enter valid email", "");
        }

        if (TextUtils.isEmpty(password)) {
            return new ErrorCode(-103, "Password is required", "");
        } else if (!(isPasswordValid(password))) {
            return new ErrorCode(-103, "Password must be more than 5 symbols", "");
        }

        return null;
    }


    // General
    private static boolean isEmailValid(String email) {
        email = email.replaceAll("\\s+", "");
        return (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private static boolean isPasswordValid(String password) {
        password = password.replaceAll("\\s+", "");
        return password.length() > 5;
    }

    private static boolean isPhoneValid(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}

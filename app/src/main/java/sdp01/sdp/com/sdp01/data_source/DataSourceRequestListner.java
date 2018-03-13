package sdp01.sdp.com.sdp01.data_source;

import org.json.JSONObject;

/**
 * Created by ibrahimaleidan on 11/03/2018.
 */

public interface DataSourceRequestListner {

    void onResponse(JSONObject response);

    void onError(ErrorCode anError);

}

package sdp01.sdp.com.sdp01.models;

import android.location.Location;

public class Request {

    private String request_id;
    private String customer_id;
    private String service;
    private Location location;


    public Request(String request_id, String customer_id, String service, Location location) {
        this.request_id = request_id;
        this.customer_id = customer_id;
        this.service = service;
        this.location = location;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

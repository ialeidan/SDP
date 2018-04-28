package sdp01.sdp.com.sdp01.models;

import android.location.Location;

public class Bid {

    private String bid_id;
    private String sp_id;
    private String price;
    private Location location;
    private Location sp_location;
    private String customer_id;
    private String request_id;
    private String status;

    public Bid(String bid_id, String sp_id, String price, Location location, Location sp_location, String customer_id, String request_id, String status) {
        this.bid_id = bid_id;
        this.sp_id = sp_id;
        this.price = price;
        this.location = location;
        this.sp_location = sp_location;
        this.customer_id = customer_id;
        this.request_id = request_id;
        this.status = status;
    }

    public String getBid_id() {
        return bid_id;
    }

    public void setBid_id(String bid_id) {
        this.bid_id = bid_id;
    }

    public String getSp_id() {
        return sp_id;
    }

    public void setSp_id(String sp_id) {
        this.sp_id = sp_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location getSp_location() {
        return sp_location;
    }

    public void setSp_location(Location sp_location) {
        this.sp_location = sp_location;
    }
}

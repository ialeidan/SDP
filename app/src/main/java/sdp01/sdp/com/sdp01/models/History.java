package sdp01.sdp.com.sdp01.models;


import android.location.Location;

public class History {

    private String history_id;
    private String customer_id;
    private String sp_id;
    private String status;
    private String rating;
    private String timestamp;
    private Location from;
    private Location to;

    public History(String history_id, String customer_id, String sp_id, String status, String rating, String timestamp, Location from, Location to) {
        this.history_id = history_id;
        this.customer_id = customer_id;
        this.sp_id = sp_id;
        this.status = status;
        this.rating = rating;
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
    }


    public String getHistory_id() {
        return history_id;
    }

    public void setHistory_id(String history_id) {
        this.history_id = history_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getSp_id() {
        return sp_id;
    }

    public void setSp_id(String sp_id) {
        this.sp_id = sp_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }
}

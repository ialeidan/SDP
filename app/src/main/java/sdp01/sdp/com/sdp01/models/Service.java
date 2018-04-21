package sdp01.sdp.com.sdp01.models;

import android.location.Location;

public class Service {

    private String service;
    private Location location;
    private Location sp_location;
    private Location cu_location;

    public Service(String service, Location location, Location sp_location, Location cu_location) {
        this.service = service;
        this.location = location;
        this.sp_location = sp_location;
        this.cu_location = cu_location;
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

    public Location getSp_location() {
        return sp_location;
    }

    public void setSp_location(Location sp_location) {
        this.sp_location = sp_location;
    }

    public Location getCu_location() {
        return cu_location;
    }

    public void setCu_location(Location cu_location) {
        this.cu_location = cu_location;
    }
}

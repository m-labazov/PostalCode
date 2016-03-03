package ua.home.postalcode.data;

public class PostalCode {

    private String code;
    private double longitude;
    private double latitude;

    public PostalCode() {}

    public PostalCode(String code, double longitude, double latitude) {
        this.code = code;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}

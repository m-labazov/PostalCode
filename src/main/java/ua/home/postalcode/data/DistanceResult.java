package ua.home.postalcode.data;

public class DistanceResult {
    private PostalCode postalCodeA;
    private PostalCode postalCodeB;
    private String unit = "km";
    private double distance;

    public DistanceResult(PostalCode postalCodeA, PostalCode postalCodeB, double distance) {
        this.postalCodeA = postalCodeA;
        this.postalCodeB = postalCodeB;
        this.distance = distance;
    }

    public String getUnit() {
        return unit;
    }

    public double getDistance() {
        return distance;
    }

    public PostalCode getPostalCodeA() {
        return postalCodeA;
    }

    public PostalCode getPostalCodeB() {
        return postalCodeB;
    }
}

package ua.home.postalcode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.home.postalcode.data.DistanceResult;
import ua.home.postalcode.data.PostalCode;
import ua.home.postalcode.repository.PostalCodeDao;

@Service
public class PostalCodeService {
    private final static double EARTH_RADIUS = 6371; // radius in kilometers

    @Autowired
    private PostalCodeDao postalCodeDao;

    public double calculateDistance(double latitude, double longitude, double latitude2, double
            longitude2) {
        // Using Haversine formula! See Wikipedia;
        double lon1Radians = Math.toRadians(longitude);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude);
        double lat2Radians = Math.toRadians(latitude2);
        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (EARTH_RADIUS * c);
    }

    public void update(PostalCode code) {
        postalCodeDao.update(code);
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private double square(double x) {
        return x * x;
    }

    public DistanceResult getDistance(String postalCodeTextA, String postalCodeTextB) {
        PostalCode postalCodeA = postalCodeDao.find(postalCodeTextA).orElse(null);
        PostalCode postalCodeB = postalCodeDao.find(postalCodeTextB).orElse(null);
        Assert.notNull(postalCodeA, "postal code '" + postalCodeTextA + "' not found");
        Assert.notNull(postalCodeB, "postal code '" + postalCodeTextB + "' not found");
        double distance = calculateDistance(postalCodeA.getLatitude(), postalCodeA.getLongitude(),
                postalCodeB.getLatitude(), postalCodeB.getLongitude());
        DistanceResult result = new DistanceResult(postalCodeA, postalCodeB, distance);
        return result;
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        Files.lines(FileSystems.getDefault().getPath("C:\\Users\\Кроха\\Downloads\\postcode-outcodes.csv")).forEach(s -> {
//            if (s.contains("id")) {
//            } else {
//                String[] values = s.split(",");
//                postalCodeDao.save(new PostalCode(values[1], Double.parseDouble(values[2]), Double.parseDouble(values[3])));
//            }
//        });
//
//    }
}

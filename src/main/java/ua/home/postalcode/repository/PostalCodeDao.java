package ua.home.postalcode.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import org.springframework.stereotype.Repository;
import ua.home.postalcode.data.PostalCode;

import javax.annotation.Resource;
import java.util.Optional;

@Repository
public class PostalCodeDao {
    public static final String COLLECTION_NAME = "postal-codes";
    public static final String LONGITUDE_FIELD = "longitude";
    public static final String LATITUDE_FIELD = "latitude";
    public static final String ID_FIELD = "_id";
    @Resource(name="db")
    private DB mongo;

    public void save(PostalCode record) {
        DBObject obj = getDbObject(record);
        mongo.getCollection(COLLECTION_NAME).save(obj);
    }

    public Optional<PostalCode> find(String code) {
        DBObject query = new BasicDBObject(ID_FIELD, code);
        DBObject obj = mongo.getCollection(COLLECTION_NAME).findOne(query);
        if (obj == null) {
            return Optional.empty();
        }
        PostalCode result = getPostalCode(obj);
        return Optional.of(result);
    }

    public void update(PostalCode code) {
        DBObject query = new BasicDBObject(ID_FIELD, code.getCode());
        mongo.getCollection(COLLECTION_NAME).update(query, getDbObject(code));
    }

    private PostalCode getPostalCode(DBObject obj) {
        PostalCode result = new PostalCode((String) obj.get(ID_FIELD), (Double) obj.get(LATITUDE_FIELD),
                (Double) obj.get(LONGITUDE_FIELD));
        return result;
    }

    private DBObject getDbObject(PostalCode record) {
        DBObject result = new BasicDBObject(ID_FIELD, record.getCode());
        result.put(LONGITUDE_FIELD, record.getLongitude());
        result.put(LATITUDE_FIELD, record.getLatitude());
        return result;
    }
}

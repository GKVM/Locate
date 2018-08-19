package config;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.mongodb.morphia.geo.Point;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import static org.mongodb.morphia.geo.GeoJson.point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends BasicDAO<User, ObjectId> {

    public UserDao(Datastore datastore) {
        super(datastore);
    }

    public Optional<User> getUser(ObjectId userId) {
        final User user = this.createQuery()
                .field("_id").equal(userId)
                .get();
        return Optional.ofNullable(user);
    }

    public ObjectId createUser(User user) {
        this.save(user);
        return user.getId();
    }

    public List<User> getUsers(){
        return this.createQuery().asList();
    }

    public List<User> getUsers(Float latitude, Float longitude, Integer range) {
        Point refPoint = point(latitude, longitude);
        List<User> d = this.createQuery().disableValidation().field("location")
                .near(refPoint, range).asList();
        return d;
    }

    public Optional<User> getUserByPhone(String phone) {
        final User user = this.createQuery()
                .field("phone").equal(phone)
                .field("is_deleted").equal(false)
                .get();
        return Optional.ofNullable(user);
    }
}

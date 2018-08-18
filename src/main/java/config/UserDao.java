package config;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.geo.Point;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import org.bson.Document;

import static org.mongodb.morphia.geo.PointBuilder.pointBuilder;


import java.util.Arrays;
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

    public void savePhoto(ObjectId id, List<Double> model) {
        final Query<User> query = this.createQuery()
                .field("_id").equal(id);
        if (model == null) {
            return;
        }
        final UpdateOperations<User> ops = this.createUpdateOperations();
        ops.set("model", model);
        final UpdateResults updateResults = this.update(query, ops);
    }

    public Optional<ObjectId> createUser(User user) {
        this.save(user);
        return Optional.of(user.getId());
    }


    /*{
   <location field>: {
     $near: {
       $geometry: {
          type: "Point" ,
          coordinates: [ <longitude> , <latitude> ]
       },
       $maxDistance: <distance in meters>,
       $minDistance: <distance in meters>
     }
   }
}*/

    public List<User> getUsers(){
        return this.createQuery().asList();
    }

    public List<User> getUsers(Float latitude, Float longitude, Integer range){
        Point point = pointBuilder().latitude(latitude.doubleValue()).longitude(longitude.doubleValue()).build();
        //this.createQuery();

        Document db = new Document(
                "$near",
                new Document().append("$geometry",
                        new Document().append("type", "Point")
                                .append("coordinates",Arrays.asList(latitude, longitude))
                )
                );
        return this.createQuery().field("location").near(1D,1D,1D, true).asList();
    }

    public Optional<User> getUserByPhone(String phone) {
        final User user = this.createQuery()
                .field("phone").equal(phone)
                .field("is_deleted").equal(false)
                .get();
        return Optional.ofNullable(user);
    }
}

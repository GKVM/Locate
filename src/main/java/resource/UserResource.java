package resource;

import config.User;
import config.UserDao;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.mongodb.morphia.geo.PointBuilder.pointBuilder;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
/*@Consumes(MediaType.APPLICATION_JSON)*/
public class UserResource {

    private UserDao userDao;

    public UserResource(UserDao userService) {
        this.userDao = userService;
    }

    @POST
    @Path("add")
    public String request() {

        User user = new User(new ObjectId(), "", "", pointBuilder().latitude(2F).longitude(2F).build());
        return userDao.createUser(user).toString();
    }

    @GET
    @Path("get/")
    public User get(
            @QueryParam("user_id") ObjectId userId
    ){
        return userDao.get(userId);
    }

    @GET
    @Path("all")
    public List<User> getUsers(){
        return userDao.getUsers();
    }

    @GET
    @Path("near")
    public List<User> getEntries(
            @NotNull @QueryParam("la") Float lat,
            @NotNull @QueryParam("lo") Float lon,
            @DefaultValue("10") @QueryParam("range") Integer range
    ) {
        return userDao.getUsers(lat, lon, range);
    }

}

package resource;

import com.mongodb.DBObject;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.util.JSON;
import config.User;
import config.UserDao;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserDao userDao;

    public UserResource(UserDao userService) {
        this.userDao = userService;
    }

    @GET
    @Path("add")
    public String request() {
        User user = new User(new ObjectId(), "", "", new double[]{2, 2});
        return userDao.createUser(user).toString();
    }

    @GET
    @Path("get")
    public User get(@QueryParam("user_id") ObjectId userId){
        return userDao.get(userId);
    }

    @GET
    @Path("all")
    public List<User> getUsers(){
        return userDao.getUsers();
    }

    @GET
    @Path("near")
    public Object getEntries(
            @NotNull @QueryParam("la") Float lat,
            @NotNull @QueryParam("lo") Float lon,
            @DefaultValue("10") @QueryParam("range") Integer range
    ) {
        return userDao.getUsers(lat, lon, range);
    }


    @GET
    @Path("process")
    public String exportData() throws Exception {
        String s =  readFileAsString("/Users/gopikrishnav.m./Downloads/rescue/d0.json");
        //System.out.println(s.exists());

        //System.out.println(s.substring(0,10));
        Document dbo = Document.parse(s);

        DBObject dbObject = (DBObject) JSON.parse(s);
        //Document d = dbo.get("data");
        return "Ok";
    }

    public static String readFileAsString(String fileName)throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}

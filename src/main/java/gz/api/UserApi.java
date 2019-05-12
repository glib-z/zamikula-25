package gz.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gz.model.Status;
import gz.model.User;
import gz.model.Message;
import gz.storage.SQLHiberStorage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/")
public class UserApi {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @GET
    public Response sayHello() {
        return Response.status(Response.Status.OK).entity("ZMServer v.0.5").build();
    }

    // ********** add_user **********
    @POST
    @Path("add_user/{name}/{date}/{time}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@PathParam("name") String name, @PathParam("date") String sDate,
                            @PathParam("time") String sTime) {
        return _addUser(name, _stringToTimestamp(sDate, sTime));
    }

    @POST
    @Path("add_user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserHtml(@FormParam("name") String name, @FormParam("date") String sDate,
                                @FormParam("time") String sTime) {
        return _addUser(name, _stringToTimestamp(sDate,sTime));
    }

    private Response _addUser(String name, Timestamp timestamp){
        User user = new User(-1, name, timestamp);
        try {
            SQLHiberStorage storage = new SQLHiberStorage();
            storage.addUser(user);
            storage.close();
            String message = "<ZMServer> User has been added to the database";
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.DONE, message, user, null)))
                    .build();
        } catch (Exception e) {
            String message = String.format("<ZMServer> Can't add record to the database. Exception: %s", e);
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.FAULT, message, null, null)))
                    .build();
        }
    }

    private Timestamp _stringToTimestamp(String sDate, String sTime) {
        java.util.Date juDate= null;
        try {
            juDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(sDate + " " + sTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert juDate != null;
        //System.out.println(">>>>>>>>>>>>>> " + sDate + " " + sTime + " juDate: " + juDate);
        return new Timestamp(juDate.getTime());
    }

    // ********** update_user **********
    @POST
    @Path("update_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(String inputJson) {
        User inputMessage = gson.fromJson(inputJson, User.class);
        return _updateUser(inputMessage.getId(), inputMessage.getName(), inputMessage.getBirth());
    }

    @POST
    @Path("update_user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserHtml(@FormParam("id") int id, @FormParam("name") String name,
                                   @FormParam("date") String sDate, @FormParam("time") String sTime) {
        return _updateUser(id, name, _stringToTimestamp(sDate, sTime));
    }

    private Response _updateUser(int id, String name, Timestamp t) {
        try {
            User user = new User(id, name, t);
            SQLHiberStorage storage = new SQLHiberStorage();
            storage.updateUser(user);
            storage.close();
            String message = "<ZMServer> This user has been updated";
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.DONE, message, user, null)))
                    .build();
        } catch (Exception e) {
            String message = String.format("<ZMServer> Can't update record with ID=%d. Exception: %s", id, e);
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.FAULT, message, null, null)))
                    .build();
        }
    }

    // ********** remove_user **********
    @DELETE
    @Path("remove_user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@PathParam("id") int id) {
        return _removeUser(id);
    }

    @POST
    @Path("remove_user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUserHtml(@FormParam("id") int id){
        return _removeUser(id);
    }

    private Response _removeUser(int id){
        try {
            SQLHiberStorage storage = new SQLHiberStorage();
            User user = storage.getUserById(id);
            storage.removeUser(id);
            storage.close();
            String message = "<ZMServer> This record has been removed from the database.";
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.DONE, message, user, null))).build();
        } catch (Exception e) {
            String message = String.format("<ZMServer> Can't remove record with ID=%d. Exception: %s", id, e);
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.FAULT, message, null, null)))
                    .build();
        }
    }

    // ********** remove_all_users **********
    @DELETE
    @Path("remove_all_users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeAllUsers(){
        return _removeAllUsers();
    }

    @POST
    @Path("remove_all_users")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeAllUsersHtml(){
        return _removeAllUsers();
    }

    private Response _removeAllUsers(){
        try {
            SQLHiberStorage storage = new SQLHiberStorage();
            storage.removeAll();
            storage.close();
            String message = "<ZMServer> All records have been removed from the database.";
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.DONE, message, null, null)))
                    .build();
        } catch (Exception e) {
            String message = String.format("<ZMServer> Can't delete records from the database. Exception: %s", e);
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.FAULT, message, null, null)))
                    .build();
        }
    }

    // ********** get_all_users **********
    @GET
    @Path("get_all_users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        try {
            SQLHiberStorage storage = new SQLHiberStorage();
            List<User> userList = storage.getAllUsers();
            storage.close();
            String message = "<ZMServer> Requested all records from the database";
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.DONE, message, null, userList)))
                    .build();
        } catch (Exception e) {
            String message = String.format("<ZMServer> Can't get records from the database. Exception: %s", e);
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.FAULT, message, null, null)))
                    .build();
        }
    }

    // ********** get_user_by_id **********
    @GET
    @Path("get_user_by_id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        return _getUserById(id);
    }

    @POST
    @Path("get_user_by_id")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByIdH(@FormParam("id2") int id) {
        return _getUserById(id);
    }

    private Response _getUserById(int id){
        try {
            SQLHiberStorage storage = new SQLHiberStorage();
            User user = storage.getUserById(id);
            storage.close();
            String message = "<ZMServer> Requested record with ID=" + id;
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.DONE, message, user, null))).build();
        } catch (Exception e) {
            String message = String.format("<ZMServer> Requested record with ID=%d. Exception: %s", id, e);
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.FAULT, message, null, null)))
                    .build();
        }
    }

    // ********** get_user_by_name **********
    @GET
    @Path("get_user_by_name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByName(@PathParam("name") String name) {
        return _getUserByName(name);
    }

    @POST
    @Path("get_user_by_name")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByNameHtml(@FormParam("name") String name) {
        return _getUserByName(name);
    }

    private Response _getUserByName(String name){
        try {
            SQLHiberStorage storage = new SQLHiberStorage();
            User user = storage.getUserByName(name);
            storage.close();
            String message = "<ZMServer> Requested record with name=" + name;
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.DONE, message, user, null))).build();
        } catch (Exception e) {
            String message = String.format("<ZMServer> Requested record with NAME=%s. Exception: %s", name, e);
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.FAULT, message, null, null)))
                    .build();
        }
    }

    // ********** get_all_users_by_date_birth **********
    @POST
    @Path("get_all_users_by_date_birth")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsersByDateBirthHtml(@FormParam("date") String sDate, @FormParam("time") String sTime,
                                               @FormParam("date1") String sDate1, @FormParam("time1") String sTime1) {
        return _getAllUserByDateBirth(_stringToTimestamp(sDate,sTime), _stringToTimestamp(sDate1,sTime1));
    }

    private Response _getAllUserByDateBirth(Timestamp timestampFrom, Timestamp timestampTo){
        try {
            SQLHiberStorage storage = new SQLHiberStorage();
            List<User> userList = storage.getByAge(timestampFrom, timestampTo);
            storage.close();
            String message = "<ZMServer> Requested record with timestamp from " + timestampFrom + " to " + timestampTo;
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.DONE, message, null, userList))).build();
        } catch (Exception e) {
            String message = String.format("<ZMServer> Requested record with timestamps. Exception: %s", e);
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(new Message(Status.FAULT, message, null, null)))
                    .build();
        }
    }



    //===== ^ CHECKED ^ =====


/*    @GET
    @Path("get_all_users_by_age")
    public Response getAllUsersByAge(String inputMessage) {
        try {
            //System.out.println("<ZMServer> /get_user_by_id" + inputMessage);
            String resultJson = gson.toJson(inputMessage);
            return Response.status(Response.Status.OK).entity(resultJson).build();
        } catch (Exception e) {
            String resultJson = gson.toJson(new Message("<ZMServer> get_user_by_name Exception: " + e));
            return Response.status(Response.Status.OK).entity(resultJson).build();
        }
    }
 */


/*    @POST
    @Path("get_all_users_by_age")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsersByAgeHtml(@FormParam("from") int minAge, @FormParam("to") int maxAge) {


        return _getAllUserByDateBirth(_stringToTimestamp(sDate,sTime), _stringToTimestamp(sDate1,sTime1));
    }
*/


}
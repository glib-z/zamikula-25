package gz.api;

import gz.model.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gz.model.User;
import gz.utills.UserDao;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class UserApi {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @GET
    public Response sayHello() {
        String result = "ZMServer >> " +
                "Available requests for this server:  " +
                " POST ./add_user  " +
                " POST ./remove_user  " +
                " POST ./update_user  " +
                " POST ./remove_all_users  " +
                " GET ./get_user_by_id  " +
                " GET ./get_user_by_name  " +
                " GET ./get_all_users";
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Path("add_user")
    public Response addUser(String inputJson) {
        String resultText;
        try {
            User inputMessage = gson.fromJson(inputJson, User.class);
            resultText = "<ZMServer> Client send message add_user " + inputMessage;
            UserDao userDao = new UserDao();
            userDao.insert(inputMessage);
            userDao.close();
        } catch (Exception e) {
            resultText = "<ZMServer> add_user Exception: " + e;
        }
        Message returnMessage = new Message(resultText);
        String resultJson = gson.toJson(returnMessage);
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @POST
    @Path("remove_user")
    public Response removeUser(String inputJson) {
        String resultText;
        try {
            User inputMessage = gson.fromJson(inputJson, User.class);
            resultText = "<ZMServer> Client send message remove_user " + inputMessage;
            UserDao userDao = new UserDao();
            userDao.delete(inputMessage);
            userDao.close();
        } catch (Exception e) {
            resultText = "<ZMServer> remove_user Exception: " + e;
        }
        Message returnMessage = new Message(resultText);
        String resultJson = gson.toJson(returnMessage);
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @POST
    @Path("update_user")
    public Response updateUser(String inputJson) {
        String resultText;
        try {
            User inputMessage = gson.fromJson(inputJson, User.class);
            //System.out.println("<ZMServer> /update_user" + inputMessage);
            resultText = "<ZMServer> Client send message update_user " + inputMessage;
            UserDao userDao = new UserDao();
            userDao.update(inputMessage);
            userDao.close();
        } catch (Exception e) {
            resultText = "<ZMServer> update_user Exception: " + e;
        }
        Message returnMessage = new Message(resultText);
        String resultJson = gson.toJson(returnMessage);
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @POST
    @Path("remove_all_users")
    public Response removeAllUsers() {
        String resultText;
        try {
            resultText = "<ZMServer> Client send message remove_all_users";
            UserDao userDao = new UserDao();
            userDao.clear();
            userDao.close();
        } catch (Exception e) {
            resultText = "<ZMServer> remove_all_users Exception: " + e;
        }
        Message returnMessage = new Message(resultText);
        String resultJson = gson.toJson(returnMessage);
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @GET
    @Path("get_user_by_id")
    public Response getUserByID(String inputJson) {
        try {
            User inputMessage = gson.fromJson(inputJson, User.class);
            //System.out.println("<ZMServer> /get_user_by_id" + inputMessage);
            UserDao userDao = new UserDao();
            userDao.close();
            String resultJson = gson.toJson(userDao.getByID(inputMessage.getId()));
            return Response.status(Response.Status.OK).entity(resultJson).build();
        } catch (Exception e) {
            String resultJson = gson.toJson(new Message("<ZMServer> get_user_by_id Exception: " + e));
            return Response.status(Response.Status.OK).entity(resultJson).build();
        }
    }

    @GET
    @Path("get_user_by_name")
    public Response getUserByName(String inputJson) {
        try {
            User inputMessage = gson.fromJson(inputJson, User.class);
            //System.out.println("<ZMServer> /get_user_by_id" + inputMessage);
            UserDao userDao = new UserDao();
            String resultJson = gson.toJson(userDao.getByName(inputMessage.getName()));
            userDao.close();
            return Response.status(Response.Status.OK).entity(resultJson).build();
        } catch (Exception e) {
            String resultJson = gson.toJson(new Message("<ZMServer> get_user_by_name Exception: " + e));
            return Response.status(Response.Status.OK).entity(resultJson).build();
        }
    }

    @GET
    @Path("get_all_users")
    public Response getAllUsers() {
        try {
            //System.out.println("<ZMServer> /get_all_users" + inputMessage);
            UserDao userDao = new UserDao();
            String resultJson = gson.toJson(userDao.getAll());
            userDao.close();
            return Response.status(Response.Status.OK).entity(resultJson).build();
        } catch (Exception e) {
            String resultJson = gson.toJson(new Message("<ZMServer> get_user_by_name Exception: " + e));
            return Response.status(Response.Status.OK).entity(resultJson).build();
        }
    }

    @GET
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



}

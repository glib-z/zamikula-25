package gz.model;

import java.util.List;

public class Message {

    private Status request_status;
    private String message;
    private User user;
    private List<User> userList;

    public Message(Status request_status, String message, User user, List<User> userList) {
        this.request_status = request_status;
        this.message = message;
        this.user = user;
        this.userList = userList;
    }

    public Message() {
    }

    public void setRequest_status(Status request_status) {
        this.request_status = request_status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Status getRequest_status() {
        return request_status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        return "Message{" +
                "request_status=" + request_status +
                ", message='" + message + '\'' +
                ", user=" + user +
                ", userList=" + userList +
                '}';
    }
}

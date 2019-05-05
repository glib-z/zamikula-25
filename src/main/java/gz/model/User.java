package gz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "_id")
    private int id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "d_birth", nullable = false, length = 10)
    private Timestamp date_birth;

    public User(int id, String name, Timestamp date_birth) {
        this.id = id;
        this.name = name;
        this.date_birth = date_birth;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getDate_birth() {
        return date_birth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate_birth(Timestamp date_birth) {
        this.date_birth = date_birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date_birth='" + date_birth + '\'' +
                '}';
    }

}

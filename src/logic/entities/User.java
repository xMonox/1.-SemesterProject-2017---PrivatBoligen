package logic.entities;

//Used to create User objects for the data layer.
public class User {
    private int id;
    private String username;
    private String password;
    private String role;

    //Used when fetching a single User from db, to fetch Users by id, to search Users and to get all Users
    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //Used to create User
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //Used whenever we just need the id.
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() { return role; }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) { this.role = role; }
}

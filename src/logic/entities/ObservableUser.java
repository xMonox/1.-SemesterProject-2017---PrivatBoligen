package logic.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Used for creating User objects for Observable list.
public class ObservableUser {
    //Alle
    private IntegerProperty userId;
    private StringProperty username;
    private StringProperty password;
    private StringProperty role;

    public ObservableUser(int userId, String username, String password, String role) {
        this.userId     = new SimpleIntegerProperty();
        this.username   = new SimpleStringProperty();
        this.password   = new SimpleStringProperty();
        this.role       = new SimpleStringProperty();

        setUserId(userId);
        setUsername(username);
        setPassword(password);
        setRole(role);
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }
}

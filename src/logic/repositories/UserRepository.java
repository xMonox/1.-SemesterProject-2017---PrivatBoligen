package logic.repositories;

import dataaccess.IDataAccessFacade;
import logic.entities.ObservableUser;
import logic.entities.User;
import logic.mapper.UserMapper;
import logic.services.PasswordService;

import java.util.List;

//Handles creation of User Objects / Lists.
public class UserRepository {
    //Alle har været over metoderne i repositoriet
    private User user;
    private IDataAccessFacade dataAccess;
    private PasswordService passwordService;
    private UserMapper mapper;

    //Data access password service and mapper dependencies injected here.
    public UserRepository(IDataAccessFacade dataAccess, PasswordService ps, UserMapper mapper) {
        this.user = null;
        this.dataAccess = dataAccess;
        this.passwordService = ps;
        this.mapper = mapper;

    }

    //Used in the Controller to pass the Active user to layers when needed.
    public User getUser() {
        return user;
    }

    //Used by Login service to set the active user
    public void setUser(User user) {
        this.user = user;
    }

    //Creates a user, returns the user , sends it to the database.
    public User createUser(String username, String password, String role){
        User user = new User(username, passwordService.getHashedPassword(password), role);
        return dataAccess.createUser(user);
    }

    // Returns a list of all users from the database
    public List<User> getAllUsers(){
        return dataAccess.getAllUsers();
    }

    //Gets all users from the database, sends the list to the mapper, that returns an observable list of users. Returns the observable list.
    public List<ObservableUser> getAllObservableUsers() {
        List<User> list = getAllUsers();

        List<ObservableUser> obslist = mapper.mapUsers(list);
        return obslist;
    }

    //Updates the password on a useraccount. Enrypts the password, sends the new password to the database.
    public void updateUserPassword(int userId, String password){
        password = passwordService.getHashedPassword(password);
        dataAccess.updateUserPassword(userId, password);

    }
    //Used to get User by Id
    public User getUserById(int userId){
        return dataAccess.getUserById(userId);
    }

    //Takes a String as parameter (from GUI text field), creates a list of matches from the database, returns a mapped list of
    //ObservableUser objects.
    public List<ObservableUser> searchUser(String searchString){
        List<User> list = dataAccess.searchUser(searchString);

        return mapper.mapUsers(list);
    }

    //Checks if the user has Admin role. If so, returns true. If not returns false.
    public Boolean isOtherAdmin(ObservableUser observableUser){
        if(observableUser.getRole().equals("Administrator")){
            return true;
        }
        return false;
    }
}

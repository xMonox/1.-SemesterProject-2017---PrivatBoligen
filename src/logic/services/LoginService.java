package logic.services;

import dataaccess.IDataAccessFacade;
import logic.entities.User;
import logic.repositories.UserRepository;


public class LoginService {
    private UserRepository userRepository;
    private PasswordService passwordService;
    private IDataAccessFacade dataAccess;

    //Bertram
    //Data access, user repository and password service dependencies injected here.
    public LoginService(UserRepository userRepository, IDataAccessFacade dataAccess, PasswordService passwordService) {
        this.dataAccess = dataAccess;
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }
    //Bertram
    //Used for login. Creates a User object, fetches user from database, hashes inputted password to check hashed entry in database.
    //Checks if user was a valid user (did the object get created = it exists in the db), and if so, sets the active user.
    //Returns the User object
    public User loginUser(String username, String password) {
         User user = dataAccess.fetchUser(username, passwordService.getHashedPassword(password));
         if (user != null) {
             userRepository.setUser(user);
         }
         return user;
    }
    //Bertram
    //Used to set the active user. Gets the logged in user by id, sets the active user and returns it.
    public User setUserFromId(int userId){
        User user = dataAccess.getUserById(userId);
        userRepository.setUser(user);
        return user;
    }

    //Julie, Bertram
    //Checks if the user has Admin role. If so, returns true. If not returns false.
    public Boolean isAdmin(){
        if(userRepository.getUser().getRole().equals("Administrator")){
            return true;
        }
        return false;
    }
}

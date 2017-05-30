package logic.mapper;


import logic.entities.ObservableUser;
import logic.entities.User;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;


public class UserMapper {
    //Julie
    //Takes a List of User objects, and returns a List of ObservableUser objects.
    public List<ObservableUser> mapUsers (List<User> userList){
        List<ObservableUser> userResult = new LinkedList<>();
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {

            User user = iterator.next();
            userResult.add(new ObservableUser(user.getId(), user.getUsername(), user.getPassword(), user.getRole()));
        }

        return userResult;
    }

}

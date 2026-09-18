package main.java.Midllewares;

import main.java.Enums.UserRole;
import main.java.Models.User;

public class AdminMiddleware {

    public boolean isAdmin(User loggedUser){
        UserRole role = loggedUser.getRole() ;

        if(role != UserRole.ADMIN){
           throw  new IllegalArgumentException("You dont have access to this action") ;
        }
        return true ;
    }
}

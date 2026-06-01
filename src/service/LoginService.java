package service;

import database.QueryManager;
import model.User;

public class LoginService {
    private User currentUser;
    
    public boolean authenticate(String username, String password) {
        if (QueryManager.validateUser(username, password)) {
            currentUser = QueryManager.getUser(username);
            return true;
        }
        return false;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean register(String username, String password, String email, String fullName) {
        return QueryManager.registerUser(username, password, email, fullName);
    }
}
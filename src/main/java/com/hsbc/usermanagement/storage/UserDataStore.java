package com.hsbc.usermanagement.storage;

import com.hsbc.usermanagement.exceptions.RoleNotFoundException;
import com.hsbc.usermanagement.exceptions.UserNotFoundException;
import com.hsbc.usermanagement.models.User;

import java.util.List;

public interface UserDataStore {

    public boolean checkUserExists(String userName);
    public void saveUser(User user) throws Exception;
    public void deleteUser(String userName) throws UserNotFoundException;
    public void addRoleToUser(String userName, String roleName) throws UserNotFoundException, RoleNotFoundException;
    public boolean checkUserRole(String roleName, String userName) throws UserNotFoundException;
    public User getUser(String username);
}

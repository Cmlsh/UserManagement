package com.hsbc.usermanagement.storage;

import com.hsbc.usermanagement.constants.Constants;
import com.hsbc.usermanagement.exceptions.RoleNotFoundException;
import com.hsbc.usermanagement.exceptions.UserNotFoundException;
import com.hsbc.usermanagement.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryDataStorage implements UserDataStore, RoleDataStore{

    /*
    * Thread safe list
    * */
    private List<User> users = Collections.synchronizedList(new ArrayList<User>());
    private List<String> roles = Collections.synchronizedList(new ArrayList<String>());

    @Override
    public boolean checkUserExists(String userName) {
        return users.stream().anyMatch(x -> x.getUsername().equals(userName));
    }


    @Override
    public User getUser(String username) {
        return users.stream().filter(x-> x.getUsername().equals(username)).findFirst().get();
    }

    @Override
    public void saveUser(User user) throws Exception {
        if(checkUserExists(user.getUsername())) {
            throw new Exception(Constants.USER_ALREADY_EXISTS);
        } else {
            users.add(user);
        }
    }

    @Override
    public void deleteUser(String userName) throws UserNotFoundException {

        if(!checkUserExists(userName)) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        } else {
            users.removeIf(x-> x.getUsername().equals(userName));
        }
    }

    @Override
    public void addRoleToUser(String userName, String roleName) throws UserNotFoundException, RoleNotFoundException {

        if(!checkUserExists(userName)) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        } else {
            Optional optional = users.stream().filter(x-> x.getUsername().equals(userName)).findFirst();
            User user = (User) optional.get();



            boolean isRolePresent = user.getRoles().stream().anyMatch(role -> role.equals(roleName));
            if(!isRolePresent) {
                users.stream().filter(x-> x.getUsername().equals(userName))
                        .map(x -> x.getRoles().add(roleName)).collect(Collectors.toList());
            } else {
                throw new RoleNotFoundException("Role doesn't exists");
            }
        }
    }

    @Override
    public boolean checkUserRole(String roleName, String userName) throws UserNotFoundException {

        if(!checkUserExists(userName)) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        } else {
            Optional optional = users.stream().filter(x -> x.getUsername().equals(userName)).findFirst();
            User user = (User) optional.get();
            boolean ifPresent = user.getRoles().stream().anyMatch(x-> x.equals(userName));
            return ifPresent;
        }

    }

    @Override
    public void deleteRole(String roleName) throws RoleNotFoundException {
        if(!checkIfRoleExists(roleName)) {
            throw new RoleNotFoundException(Constants.ROLE_NOT_FOUND);
        } else {
            roles.removeIf(role -> role.equals(roleName));
        }
    }

    @Override
    public void createRole(String roleName) throws Exception {
        if(!checkIfRoleExists(roleName)) {
            roles.add(roleName);
        } else {
            throw new Exception("Role already exists");
        }
    }

    @Override
    public List getAllRoles() {
        return roles;
    }

    @Override
    public boolean checkIfRoleExists(String roleName) {

        return roles.stream().anyMatch(role -> role.equals(roleName));
    }
}

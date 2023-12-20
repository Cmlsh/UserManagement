package com.hsbc.usermanagement.main;

import com.hsbc.usermanagement.constants.Constants;
import com.hsbc.usermanagement.exceptions.AuthenticationException;
import com.hsbc.usermanagement.exceptions.InvalidAuthTokenException;
import com.hsbc.usermanagement.exceptions.RoleNotFoundException;
import com.hsbc.usermanagement.exceptions.UserNotFoundException;
import com.hsbc.usermanagement.models.User;
import com.hsbc.usermanagement.security.TokenManager;
import com.hsbc.usermanagement.security.TokenMetadata;
import com.hsbc.usermanagement.storage.InMemoryDataStorage;
import com.hsbc.usermanagement.utils.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

public class Main {

    public InMemoryDataStorage dataStorage = new InMemoryDataStorage();
    public static void main(String[] args) throws Exception {

    }

    public User getUser(String userName) {
        return dataStorage.getUser(userName);
    }
    public boolean checkUserExists(String userName) {
        return dataStorage.checkUserExists(userName);
    }

    public boolean checkRoleExists(String roleName) {
        return dataStorage.checkIfRoleExists(roleName);
    }

    public void createUser(String userName, String password) throws Exception {

        if(userName.isEmpty() || password.isEmpty()) {
            throw new Exception("Username or password is empty");
        }

        User user = new User(userName, StringUtils.getHash(password));
        dataStorage.saveUser(user);

    }

    public void deleteUser(String userName) throws UserNotFoundException {

        dataStorage.deleteUser(userName);
    }

    public void createRole(String role) {

        try {
            dataStorage.createRole(role);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRole(String role) {

        try {
            dataStorage.deleteRole(role);
        } catch (RoleNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRoleToUser(String username, String role) throws UserNotFoundException, RoleNotFoundException {

        dataStorage.addRoleToUser(username, role);

    }

    public String authenticate(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, UserNotFoundException, AuthenticationException {

        User user = dataStorage.getUser(username);
        if(user == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        String hashedPassword = StringUtils.getHash(password);

        if(hashedPassword.equals(user.getPassword())) {
            String token = TokenManager.getInstance().createToekn(username);
            return token;
        } else {
            throw new AuthenticationException("Invalid username and password.");
        }
    }

    public void invalidateToken(String token) throws InvalidAuthTokenException {

        TokenManager.getInstance().invalidateToken(token);
    }

    public boolean isTokenValid(String token) throws InvalidAuthTokenException {

        return TokenManager.getInstance().isTokenValid(token);
    }

    public boolean checkRole(String role, String token) throws InvalidAuthTokenException {

        TokenMetadata tokenMetadata = TokenManager.getInstance().getTokenUser(token);

        User user = dataStorage.getUser(tokenMetadata.getAssociatedUser());
        Optional foundRole = user.getRoles().stream().filter(x -> x.equals(role)).findFirst();
        if(foundRole.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public List getAllRoles(String token) throws InvalidAuthTokenException {

        TokenMetadata tokenMetadata = TokenManager.getInstance().getTokenUser(token);

        User user = dataStorage.getUser(tokenMetadata.getAssociatedUser());
        return user.getRoles();
    }

}

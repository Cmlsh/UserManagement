package com.hsbc.usermanagement.storage;

import com.hsbc.usermanagement.exceptions.RoleNotFoundException;

import java.util.List;

public interface RoleDataStore {

    public void deleteRole(String roleName) throws RoleNotFoundException;
    public void createRole(String roleName) throws Exception;
    public List getAllRoles();
    public boolean checkIfRoleExists(String roleName);


}

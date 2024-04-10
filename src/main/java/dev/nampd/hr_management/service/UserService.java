package dev.nampd.hr_management.service;

import dev.nampd.hr_management.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    User getUserById(Long id);

    List<User> getUserByFirstName(String firstName);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);

    /**
     * Assign a role to a user
     *
     * @param username tên người dùng(email)
     * @param roleName tên vai trò
     */

    User addUser(User user);

    List<User> getAllUser();

    void assignRoleAndDepartmentToUser(String username, String roleName, Long departmentId);
    //remove user from department
    void removeUserFromDepartmentAndUnassignRole(String username);
}

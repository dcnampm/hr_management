package dev.nampd.hr_management.service;

import dev.nampd.hr_management.model.dto.UserDto;
import dev.nampd.hr_management.model.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    @PreAuthorize("hasAnyAuthority(\"USER.READ\") or hasAnyRole('ROLE_TPNS')")
    UserDto getUserById(Long id);
    @PreAuthorize("hasAnyAuthority(\"USER.READ\") or hasAnyRole('ROLE_TPNS')")
    List<UserDto> getUserByFirstName(String firstName);
    @PreAuthorize("hasAnyAuthority(\"USER.UPDATE\") or hasAnyRole('ROLE_TPNS')")
    User updateUser(Long id, User updatedUser);
    @PreAuthorize("hasAnyAuthority(\"USER.DELETE\") or hasAnyRole('ROLE_TPNS')")
    void deleteUser(Long id);
    @PreAuthorize("hasAnyAuthority(\"USER.CREATE\") or hasAnyRole('ROLE_TPNS')")
    User addUser(User user);
    @PreAuthorize("hasAnyAuthority(\"USER.READ\") or hasAnyRole('ROLE_TPNS')")
    List<UserDto> getAllUsers();
    @PreAuthorize("hasAnyAuthority(\"USER.UPDATE\") or hasAnyRole('ROLE_TPNS')")
    void assignRoleAndDepartmentToUser(String username, String roleName, Long departmentId);
    //remove user from department
    @PreAuthorize("hasAnyAuthority(\"USER.UPDATE\") or hasAnyRole('ROLE_TPNS')")
    void removeUserFromDepartmentAndUnassignRole(String username);
}

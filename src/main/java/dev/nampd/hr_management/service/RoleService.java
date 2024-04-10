package dev.nampd.hr_management.service;

import dev.nampd.hr_management.model.entity.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role, Long departmentId);

    void deleteRole(Long roleId);
    Role getRoleByName(String roleName);
}

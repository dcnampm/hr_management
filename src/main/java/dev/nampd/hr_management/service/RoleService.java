package dev.nampd.hr_management.service;

import dev.nampd.hr_management.model.entity.Role;

public interface RoleService {
    Role createRole(Role role, Long departmentId);

    void deleteRole(Long roleId);

}

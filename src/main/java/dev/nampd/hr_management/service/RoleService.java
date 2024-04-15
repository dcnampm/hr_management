package dev.nampd.hr_management.service;

import dev.nampd.hr_management.model.entity.Permission;
import dev.nampd.hr_management.model.entity.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

public interface RoleService {
    @PreAuthorize("hasAnyAuthority(\"ROLE.CREATE\") or hasAnyRole('ROLE_TPNS')")
    Role createRole(Role role, Long departmentId);
    @PreAuthorize("hasAnyAuthority(\"ROLE.DELETE\") or hasAnyRole('ROLE_TPNS')")
    void deleteRole(Long roleId);
    @PreAuthorize("hasAnyAuthority(\"ROLE.READ\") or hasAnyRole('ROLE_TPNS')")
    Role getRoleByName(String roleName);
    void createPermissions();
    @PreAuthorize("hasAnyAuthority(\"ROLE.UPDATE\") or hasAnyRole('ROLE_TPNS')")
    Role updateRole(Long roleId, Role updatedRole, Set<Long> permissionIds);
    @PreAuthorize("hasAnyAuthority(\"ROLE.READ\") or hasAnyRole('ROLE_TPNS')")
    Set<Permission> getPermissionsByRoleId(Long roleId);
}

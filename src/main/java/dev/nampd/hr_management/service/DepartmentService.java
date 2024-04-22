package dev.nampd.hr_management.service;

import dev.nampd.hr_management.model.dto.DepartmentDto;
import dev.nampd.hr_management.model.entity.Department;
import dev.nampd.hr_management.model.entity.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface DepartmentService {
    @PreAuthorize("hasAnyAuthority(\"DEPARTMENT.CREATE\") or hasAnyRole('ROLE_TPNS')")
    Department createDepartment(Department department);
    @PreAuthorize("hasAnyAuthority(\"DEPARTMENT.READ\") or hasAnyRole('ROLE_TPNS')")
    List<DepartmentDto> getAllDepartments();
    @PreAuthorize("hasAnyAuthority(\"DEPARTMENT.READ\") or hasAnyRole('ROLE_TPNS')")
    DepartmentDto getDepartmentById(Long id);
    @PreAuthorize("hasAnyAuthority(\"DEPARTMENT.READ\") or hasAnyRole('ROLE_TPNS')")
    DepartmentDto getDepartmentByAlias(String alias);
    @PreAuthorize("hasAnyAuthority(\"DEPARTMENT.UPDATE\") or hasAnyRole('ROLE_TPNS')")
    DepartmentDto updateDepartment(Long id, DepartmentDto updatedDepartmentDto);
    @PreAuthorize("hasAnyAuthority(\"DEPARTMENT.DELETE\") or hasAnyRole('ROLE_TPNS')")
    void deleteDepartment(Long id);
    @PreAuthorize("hasAnyAuthority(\"DEPARTMENT.READ\") or hasAnyRole('ROLE_TPNS')")
    List<Role> getRolesInDepartment(Long departmentId);
}

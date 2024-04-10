package dev.nampd.hr_management.service;

import dev.nampd.hr_management.model.entity.Department;
import dev.nampd.hr_management.model.entity.Role;

import java.util.List;

public interface DepartmentService {
    Department createDepartment(Department department);

    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    Department getDepartmentByAlias(String alias);

    Department updateDepartment(Long id, Department updatedDepartment);

    void deleteDepartment(Long id);

    List<Role> getRolesInDepartment(Long departmentId);
}

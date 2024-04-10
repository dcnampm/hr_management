package dev.nampd.hr_management.service.impl;

import dev.nampd.hr_management.model.entity.Department;
import dev.nampd.hr_management.model.entity.Role;
import dev.nampd.hr_management.repository.DepartmentRepository;
import dev.nampd.hr_management.repository.RoleRepository;
import dev.nampd.hr_management.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    public RoleServiceImpl(RoleRepository roleRepository, DepartmentRepository departmentRepository) {
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Role createRole(Role role, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department with id " + departmentId + " not found"));

        Role existingRole = roleRepository.findByNameAndDepartmentId(role.getName(), departmentId);
        if (existingRole != null) {
            throw new IllegalArgumentException("Role '" + role.getName() + "' already exists in department with id " + departmentId);
        }

        role.setDepartment(department);
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        Role roleToBeDeleted = roleRepository.findById(roleId).orElseThrow(() -> new NoSuchElementException("Role not found"));
        roleRepository.deleteById(roleId);
    }

}

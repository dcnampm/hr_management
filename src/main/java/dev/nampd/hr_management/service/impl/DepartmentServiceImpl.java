package dev.nampd.hr_management.service.impl;

import dev.nampd.hr_management.model.entity.Department;
import dev.nampd.hr_management.model.entity.Role;
import dev.nampd.hr_management.repository.DepartmentRepository;
import dev.nampd.hr_management.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new IllegalArgumentException("Department already exists");
        }
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }

    @Override
    public Department getDepartmentByAlias(String alias) {
        return departmentRepository.findByAlias(alias)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }

    @Override
    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setAlias(updatedDepartment.getAlias());
        existingDepartment.setRoles(updatedDepartment.getRoles());

        return departmentRepository.save(existingDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if (existingDepartment.isPresent()) {
            departmentRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Department not found with id: " + id);
        }
    }

    @Override
    public List<Role> getRolesInDepartment(Long departmentId) {
        Optional<Department> existingDepartment = departmentRepository.findById(departmentId);
        if (existingDepartment.isPresent()) {
            Department department = existingDepartment.get();
            return department.getRoles();
        } else {
            throw new NoSuchElementException("Department not found");
        }
    }
}

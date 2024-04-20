package dev.nampd.hr_management.service.impl;

import dev.nampd.hr_management.model.entity.Department;
import dev.nampd.hr_management.model.entity.Role;
import dev.nampd.hr_management.repository.DepartmentRepository;
import dev.nampd.hr_management.service.DepartmentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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
    @Cacheable(value = "departments")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    @Cacheable(value = "departments", key = "#departmentId")
    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }

    @Override
    @Cacheable(value = "departments", key = "#alias")
    public Department getDepartmentByAlias(String alias) {
        return departmentRepository.findByAlias(alias)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }

    @Override
    @CachePut(value = "departments", key = "#departmentId")
    public Department updateDepartment(Long departmentId, Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setAlias(updatedDepartment.getAlias());
//        existingDepartment.setRoles(updatedDepartment.getRoles());

        return departmentRepository.save(existingDepartment);
    }

    @Override
    @CacheEvict(value = "departments", allEntries = true)
    public void deleteDepartment(Long departmentId) {
        Optional<Department> existingDepartment = departmentRepository.findById(departmentId);
        if (existingDepartment.isPresent()) {
            departmentRepository.deleteById(departmentId);
        } else {
            throw new NoSuchElementException("Department not found with id: " + departmentId);
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

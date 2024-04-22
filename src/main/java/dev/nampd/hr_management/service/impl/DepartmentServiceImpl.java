package dev.nampd.hr_management.service.impl;

import dev.nampd.hr_management.mapper.DepartmentMapper;
import dev.nampd.hr_management.model.dto.DepartmentDto;
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
import java.util.stream.Collectors;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
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
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDepartmentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "departments", key = "#departmentId")
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        return departmentMapper.toDepartmentDto(department);
    }

    @Override
    @Cacheable(value = "departments", key = "#alias")
    public DepartmentDto getDepartmentByAlias(String alias) {
        Department department = departmentRepository.findByAlias(alias)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        return departmentMapper.toDepartmentDto(department);
    }

    @Override
    @CachePut(value = "departments", key = "#departmentId")
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartmentDto) {
        Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        existingDepartment.setName(updatedDepartmentDto.getName());
        existingDepartment.setAlias(updatedDepartmentDto.getAlias());

        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return departmentMapper.toDepartmentDto(updatedDepartment);
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

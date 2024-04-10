package dev.nampd.hr_management.repository;

import dev.nampd.hr_management.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);
    Optional<Department> findByAlias(String alias);

//    Set<Role> findRolesInDepartmentById(Long departmentId);
}

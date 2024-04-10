package dev.nampd.hr_management.repository;

import dev.nampd.hr_management.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    Role findByName(String roleName);

    Role findByNameAndDepartmentId(String name, Long departmentId);

//    Set<Role> findRolesByDepartmentId(Long departmentId);
}

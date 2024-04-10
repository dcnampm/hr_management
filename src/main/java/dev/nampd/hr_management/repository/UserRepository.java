package dev.nampd.hr_management.repository;

import dev.nampd.hr_management.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRoleName(String roleName);

    List<User> findByFirstName(String firstName);
}

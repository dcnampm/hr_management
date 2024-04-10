package dev.nampd.hr_management.service.impl;

import dev.nampd.hr_management.model.entity.Department;
import dev.nampd.hr_management.model.entity.Role;
import dev.nampd.hr_management.model.entity.User;
import dev.nampd.hr_management.repository.DepartmentRepository;
import dev.nampd.hr_management.repository.RoleRepository;
import dev.nampd.hr_management.repository.UserRepository;
import dev.nampd.hr_management.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public User addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Username already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public List<User> getUserByFirstName(String firstName) {
        List<User> foundUsers = userRepository.findByFirstName(firstName);

        if (foundUsers != null && !foundUsers.isEmpty()) {
            return foundUsers;
        } else {
            throw new NoSuchElementException("Not found users with first name: " + firstName);
        }
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setBirthday(updatedUser.getBirthday());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    public void assignRoleAndDepartmentToUser(String username, String roleName, Long departmentId) {
        User user = userRepository.findByEmail(username);
        Role role = roleRepository.findByName(roleName);
        Department department = departmentRepository.findById(departmentId).orElse(null);

        if (user != null && role != null && department != null) {
            user.setRole(role);
            user.setDepartment(department);
            userRepository.save(user);
        }
    }

    //remove user from department
    @Override
    public void removeUserFromDepartmentAndUnassignRole(String username) {
        User user = userRepository.findByEmail(username);

        if (user != null) {
            user.setDepartment(null);
            user.setRole(null);
            userRepository.save(user);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

}

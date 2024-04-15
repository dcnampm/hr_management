package dev.nampd.hr_management.service.impl;

import dev.nampd.hr_management.model.entity.Department;
import dev.nampd.hr_management.model.entity.Role;
import dev.nampd.hr_management.model.entity.User;
import dev.nampd.hr_management.repository.DepartmentRepository;
import dev.nampd.hr_management.repository.RoleRepository;
import dev.nampd.hr_management.repository.UserRepository;
import dev.nampd.hr_management.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

//        Department department = departmentRepository.findById(user.getDepartmentId())
//                .orElseThrow(() -> new NoSuchElementException("Department not found"));
//        Role role = roleRepository.findById(user.getRoleId())
//                .orElseThrow(() -> new NoSuchElementException("Role not found"));
//        user.setDepartment(department);
//        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
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
        Optional<User> foundUser = userRepository.findByEmail(username);
        Role role = roleRepository.findByName(roleName);
        Department department = departmentRepository.findById(departmentId).orElse(null);

        if (foundUser.isPresent() && role != null && department != null) {
            User user = foundUser.get();
            user.setRole(role);
            user.setDepartment(department);
            userRepository.save(user);
        }
    }

    //remove user from department
    @Override
    public void removeUserFromDepartmentAndUnassignRole(String username) {
        Optional<User> foundUser = userRepository.findByEmail(username);

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            user.setDepartment(null);
            user.setRole(null);
            userRepository.save(user);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

}

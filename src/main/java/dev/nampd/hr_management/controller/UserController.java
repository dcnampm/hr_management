package dev.nampd.hr_management.controller;

import dev.nampd.hr_management.model.GenericResponse;
import dev.nampd.hr_management.model.dto.UserDto;
import dev.nampd.hr_management.model.entity.User;
import dev.nampd.hr_management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse<User>> addUser(@RequestBody User user) {
        try {
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new GenericResponse<>(userService.addUser(user), 201, "Created user successfully", true));
            return new ResponseEntity<>(new GenericResponse<>(userService.addUser(user)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(null, 400, "Failed to create user: " + e.getMessage(), false));
        }
    }

    @GetMapping()
    public ResponseEntity<GenericResponse<List<UserDto>>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse<>(userService.getAllUsers()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GenericResponse<UserDto>> getUserById(@PathVariable Long userId) {
        try {
            UserDto userDto = userService.getUserById(userId);
            return ResponseEntity.ok(new GenericResponse<>(userDto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(null, 400, "Failed to get user: " + e.getMessage(), false));
        }
    }

    @GetMapping("/search/{firstName}")
    public ResponseEntity<GenericResponse<List<UserDto>>> getUserByFirstName(@PathVariable String firstName) {
        try {
            List<UserDto> userDtos = userService.getUserByFirstName(firstName);
            return ResponseEntity.ok(new GenericResponse<>(userDtos));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(null, 400, "Failed to get users: " + e.getMessage(), false));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<User>> updateUser(@PathVariable(name = "id") Long userId, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok(new GenericResponse<>(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new GenericResponse<>(null, 400, "Failed to update user: " + e.getMessage(), false));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteUser(@PathVariable(name = "id") Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse<>(null, 500, "Failed to delete user: " + e.getMessage(), false));
        }
    }

    @Operation(
            summary = "Add user to a department and assign role to user"
    )
    @PutMapping("/assignRole/{username}")
    public ResponseEntity<String> assignRoleToUser(@PathVariable String username,
                                                   @RequestParam String roleName,
                                                   @RequestParam Long departmentId) {
        userService.assignRoleAndDepartmentToUser(username, roleName, departmentId);
        return ResponseEntity.ok("Assigning user to department and assigning role to user " + username + " successfully");
    }

    @Operation(
            summary = "Unassign user's role and remove user from department"
    )
    @PutMapping("/remove/{username}")
    public ResponseEntity<String> removeUserFromDepartmentAndUnassginRole(@PathVariable String username) {
        userService.removeUserFromDepartmentAndUnassignRole(username);
        return ResponseEntity.ok("Remove user from department successfully");
    }
}

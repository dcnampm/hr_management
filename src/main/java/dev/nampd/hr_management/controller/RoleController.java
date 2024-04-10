package dev.nampd.hr_management.controller;

import dev.nampd.hr_management.model.GenericResponse;
import dev.nampd.hr_management.model.entity.Department;
import dev.nampd.hr_management.model.entity.Role;
import dev.nampd.hr_management.service.DepartmentService;
import dev.nampd.hr_management.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse<Role>> createRole(@RequestBody Role role, @RequestParam Long departmentId) {
        try {
            Role createdRole = roleService.createRole(role, departmentId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new GenericResponse<>(createdRole, 201, "Created role successfully", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(null, 400, "Failed to create role: " + e.getMessage(), false));
        }
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<GenericResponse<String>> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }
}

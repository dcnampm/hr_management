package dev.nampd.hr_management.controller;

import dev.nampd.hr_management.model.GenericResponse;
import dev.nampd.hr_management.model.entity.Department;
import dev.nampd.hr_management.model.entity.Role;
import dev.nampd.hr_management.service.DepartmentService;
import dev.nampd.hr_management.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService, RoleService roleService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse<Department>> createDepartment(@RequestBody Department department) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new GenericResponse<>(departmentService.createDepartment(department), 201, "Created department successfully", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(null, 400, "Failed to create department: " + e.getMessage(), false));
        }
    }

    @GetMapping()
    public ResponseEntity<GenericResponse<List<Department>>> getAllDepartments() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse<>(departmentService.getAllDepartments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<Department>> getDepartmentByID(@PathVariable(name = "id") Long departmentId) {
        try {
            Department department = departmentService.getDepartmentById(departmentId);
            return ResponseEntity.ok(new GenericResponse<>(department));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(null, 400, "Failed to get department: " + e.getMessage(), false));
        }
    }

    @GetMapping("/search/{alias}")
    public ResponseEntity<GenericResponse<Department>> getDepartmentByAlias(@PathVariable(name = "alias") String alias) {
        try {
            Department department = departmentService.getDepartmentByAlias(alias);
            return ResponseEntity.ok(new GenericResponse<>(department));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(null, 400, "Failed to get department: " + e.getMessage(), false));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<Department>> updateDepartment(@PathVariable(name = "id") Long departmentId, @RequestBody Department updatedDepartment) {
        try {
            Department department = departmentService.updateDepartment(departmentId, updatedDepartment);
            return ResponseEntity.ok(new GenericResponse<>(department));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new GenericResponse<>(null, 400, "Failed to update department: " + e.getMessage(), false));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteDepartment(@PathVariable(name = "id") Long departmentId) {
        try {
            departmentService.deleteDepartment(departmentId);
            return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse<>(null, 500, "Failed to delete department: " + e.getMessage(), false));
        }
    }

    @GetMapping("/roles/{departmentId}")
    public ResponseEntity<GenericResponse<List<Role>>> getRolesInDepartmentById(@PathVariable Long departmentId) {
        List<Role> roles = departmentService.getRolesInDepartment(departmentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse<>(roles));
    }

}

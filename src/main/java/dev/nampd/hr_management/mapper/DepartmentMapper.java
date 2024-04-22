package dev.nampd.hr_management.mapper;

import dev.nampd.hr_management.model.dto.DepartmentDto;
import dev.nampd.hr_management.model.dto.UserDetailsDto;
import dev.nampd.hr_management.model.entity.Department;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentMapper {
    private final UserMapper userMapper;

    public DepartmentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Department toDepartment(DepartmentDto dto) {
        var department = new Department();
        department.setName(dto.getName());
        department.setAlias(dto.getAlias());

        return department;
    }

    public DepartmentDto toDepartmentDto(Department department) {
        Set<UserDetailsDto> userDetailsDtos = department.getUsers()
                .stream()
                .map(userMapper::toUserDetailsDto)
                .collect(Collectors.toSet());

        return new DepartmentDto(
                department.getName(),
                department.getAlias(),
                userDetailsDtos
        );
    }

}

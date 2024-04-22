package dev.nampd.hr_management.mapper;

import dev.nampd.hr_management.model.dto.UserDetailsDto;
import dev.nampd.hr_management.model.dto.UserDto;
import dev.nampd.hr_management.model.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                user.getPhone(),
                user.getAddress(),
                user.getBirthday(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().getName() : null,
                user.getDepartment() != null ? user.getDepartment().getName() : null
        );
    }

    public UserDetailsDto toUserDetailsDto(User user) {
        return new UserDetailsDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().getName() : null
        );
    }
}

package dev.nampd.hr_management.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDto implements Serializable {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Boolean gender;
    private final String phone;
    private final String address;
    private final LocalDate birthday;
    private final String email;
    private final String roleName;
    private final String departmentName;
}

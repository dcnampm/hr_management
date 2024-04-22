package dev.nampd.hr_management.model.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private final String name;
    private final String description;
}

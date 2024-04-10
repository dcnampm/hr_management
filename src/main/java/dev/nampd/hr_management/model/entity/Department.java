package dev.nampd.hr_management.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(
        name = "departments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}, name = "name_unique"),
                @UniqueConstraint(columnNames = {"alias"}, name = "alias_unique"),
        }
)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String alias;

    @OneToMany(mappedBy = "department", targetEntity = User.class, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<User> users;

    @OneToMany(mappedBy = "department", targetEntity = Role.class, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Role> roles;
}

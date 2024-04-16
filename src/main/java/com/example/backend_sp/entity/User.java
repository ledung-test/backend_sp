package com.example.backend_sp.entity;


import com.example.backend_sp.entity.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    String username;
    String email;
    String password;
    String avatarUrl;
    Date dateOfBirth;
    boolean activated;
    @Enumerated(EnumType.STRING)
    private Role role;

}

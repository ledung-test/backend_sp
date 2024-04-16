package com.example.backend_sp.response;

import com.example.backend_sp.entity.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;
    String username;
    String email;
    String avatarUrl;
    Date dateOfBirth;
    boolean activated;
    Role role;
    Date createdAt;
}

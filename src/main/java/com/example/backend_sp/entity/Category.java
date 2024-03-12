package com.example.backend_sp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false)
    String name;

    @Column(name = "is_activated")
    boolean activated;

    @Column(name = "is_deleted")
    boolean deleted;

}

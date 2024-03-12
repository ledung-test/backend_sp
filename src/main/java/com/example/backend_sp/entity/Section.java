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
@Table(name = "sections")
public class Section extends BaseEntity{

    @Column(nullable = false)
    String name;

    Integer position;

    @Column(name = "is_activated")
    boolean activated;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    Course course;

}

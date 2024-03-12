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
@Table(name = "lectures")
public class Lecture extends BaseEntity {

    @Column(nullable = false)
    String name;

    String video_url;

    String document_url;

    Integer position;

    @Column(name = "is_activated")
    boolean activated;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id")
    Section section;
}

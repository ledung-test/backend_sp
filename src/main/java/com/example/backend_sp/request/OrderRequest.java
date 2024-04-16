package com.example.backend_sp.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Integer user_id;
    String orderStatus;
    String paymentMethod;
    Set<Integer> listCourseId;
}

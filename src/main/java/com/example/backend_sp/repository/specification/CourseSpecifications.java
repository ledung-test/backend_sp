package com.example.backend_sp.repository.specification;

import com.example.backend_sp.entity.Course;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CourseSpecifications {

    public static Specification<Course> withMinimumRating(Double minimumRating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minimumRating);
    }

    public static Specification<Course> sortByCreationDate() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return null;
        };
    }

    public static Specification<Course> sortByNumberOfStudents() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("totalStudents")));
            return null;
        };
    }

    public static Specification<Course> sortAlphabetically() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("name")));
            return null;
        };
    }

    public static Specification<Course> hasCategoryIds(List<Integer> categoryIds) {
        return (root, query, criteriaBuilder) -> {
            return root.get("category").get("id").in(categoryIds);
        };
    }

    public static Specification<Course> hasPrice(List<String> priceOptions) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (priceOptions.contains("price-paid")) {
                predicates.add(criteriaBuilder.gt(root.get("price"), 0));
            }
            if (priceOptions.contains("price-free")) {
                predicates.add(criteriaBuilder.equal(root.get("price"), 0));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}

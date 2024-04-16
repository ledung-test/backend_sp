package com.example.backend_sp.repository;

import com.example.backend_sp.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    List<Discount> findAllByEndDateAfter(Date currentDate);
}

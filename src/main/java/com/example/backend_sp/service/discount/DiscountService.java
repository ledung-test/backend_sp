package com.example.backend_sp.service.discount;

import com.example.backend_sp.entity.Discount;
import com.example.backend_sp.request.DiscountRequest;

public interface DiscountService {
    Discount create(DiscountRequest request);
}

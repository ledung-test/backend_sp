package com.example.backend_sp.service.discount;

import com.example.backend_sp.entity.Discount;
import com.example.backend_sp.request.DiscountRequest;
import com.example.backend_sp.response.DiscountResponse;

import java.util.List;

public interface DiscountService {

    List<DiscountResponse> getAllDiscounts();

    List<DiscountResponse> getAllDiscountsByActivatedTrue();
    Discount create(DiscountRequest request);

    Discount update(DiscountRequest request, Integer discountId);
}

package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Discount;
import com.example.backend_sp.request.DiscountRequest;

public class DiscountMapper {
    public static void mapDiscountRequestDiscount(DiscountRequest request, Discount discount){
        discount.setDiscountName(request.getDiscountName());
        discount.setDiscountType(request.getDiscountType());
        discount.setDiscountValue(request.getDiscountValue());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
    }
}

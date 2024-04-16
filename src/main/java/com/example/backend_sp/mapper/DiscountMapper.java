package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Discount;
import com.example.backend_sp.entity.enums.DiscountType;
import com.example.backend_sp.request.DiscountRequest;
import com.example.backend_sp.response.DiscountResponse;

public class DiscountMapper {
    public static void mapDiscountRequestDiscount(DiscountRequest request, Discount discount){
        discount.setDiscountName(request.getDiscountName());
        if (request.getDiscountType().equals(DiscountType.PERCENTAGE)){
            discount.setDiscountType(DiscountType.PERCENTAGE);
        }else {
            discount.setDiscountType(DiscountType.FIXED);
        }
        discount.setDiscountValue(request.getDiscountValue());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
    }

    public static void mapDiscountToDiscountResponse(Discount discount, DiscountResponse response){
        response.setId(discount.getId());
        response.setDiscountName(discount.getDiscountName());
        response.setDiscountType(discount.getDiscountType());
        response.setDiscountValue(discount.getDiscountValue());
        response.setStartDate(discount.getStartDate());
        response.setEndDate(discount.getEndDate());
        response.setCreatedAt(discount.getCreatedAt());
        response.setUpdatedAt(discount.getUpdatedAt());
    }
}

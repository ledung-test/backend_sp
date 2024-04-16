package com.example.backend_sp.utils;

import com.example.backend_sp.entity.*;
import com.example.backend_sp.entity.enums.DiscountType;
import com.example.backend_sp.repository.CourseDiscountRepository;
import com.example.backend_sp.response.CourseResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;

public class DiscountUtils {
    public static void setDiscountToCourseResponse(CourseDiscountRepository courseDiscountRepository, Course course, CourseResponse courseResponse){
        Optional<CourseDiscount> courseDiscount = isDiscountFromCourse(courseDiscountRepository, course);
        if (courseDiscount.isPresent()){
            Discount discount = courseDiscount.get().getDiscount();
            courseResponse.setDiscount(calculateDiscountAmount(discount.getDiscountValue(), course.getPrice(), discount.getDiscountType()));
            courseResponse.setDiscount_id(discount.getId());
        }else{
            courseResponse.setDiscount(null);
        }
    }
    public static BigDecimal getDiscountFromCourse(CourseDiscountRepository courseDiscountRepository, Course course){
        Optional<CourseDiscount> courseDiscount = isDiscountFromCourse(courseDiscountRepository, course);
        if (courseDiscount.isPresent()){
            Discount discount = courseDiscount.get().getDiscount();
            return calculateDiscountAmount(discount.getDiscountValue(), course.getPrice(), discount.getDiscountType());
        }else{
            return BigDecimal.ZERO;
        }
    }
    public static Optional<CourseDiscount> isDiscountFromCourse(CourseDiscountRepository courseDiscountRepository, Course course){
        return courseDiscountRepository.findByCourseIdAndDiscountEndDateAfter(course.getId(), new Date());
    }
    public static BigDecimal calculateDiscountAmount(BigDecimal discountValue, BigDecimal price, DiscountType discountType) {
        if (discountType.equals(DiscountType.PERCENTAGE)) {
            BigDecimal discountPercentage = discountValue.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            return price.subtract(price.multiply(discountPercentage));
        } else if (discountType.equals(DiscountType.FIXED)) {
                return price.subtract(discountValue);
        } else {
            throw new RuntimeException("Loại giảm không được áp dụng.");
        }
    }
}

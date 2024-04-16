package com.example.backend_sp.service.discount;

import com.example.backend_sp.entity.Discount;
import com.example.backend_sp.mapper.DiscountMapper;
import com.example.backend_sp.repository.DiscountRepository;
import com.example.backend_sp.request.DiscountRequest;
import com.example.backend_sp.response.DiscountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService{

    private final DiscountRepository repository;

    public void mapListDiscountToListDiscountResponse(List<Discount> discounts, List<DiscountResponse> discountResponses){
        if (!discounts.isEmpty()){
            for (Discount discount : discounts) {
                DiscountResponse discountResponse = new DiscountResponse();
                DiscountMapper.mapDiscountToDiscountResponse(discount, discountResponse);
                discountResponses.add(discountResponse);
            }
        }
    }

    @Override
    public List<DiscountResponse> getAllDiscounts() {
        List<Discount> discounts = repository.findAll();
        List<DiscountResponse> discountResponses = new ArrayList<>();
        mapListDiscountToListDiscountResponse(discounts, discountResponses);
        return discountResponses;
    }

    @Override
    public List<DiscountResponse> getAllDiscountsByActivatedTrue() {
        List<Discount> discounts = repository.findAllByEndDateAfter(new Date());
        List<DiscountResponse> discountResponses = new ArrayList<>();
        mapListDiscountToListDiscountResponse(discounts, discountResponses);
        return discountResponses;
    }

    @Override
    public Discount create(DiscountRequest request) {
        if (request.getEndDate().before(request.getStartDate())){
            throw new RuntimeException("Ngày hết hạn phải sau ngày bắt đầu");
        }
        Discount discount = new Discount();
        DiscountMapper.mapDiscountRequestDiscount(request, discount);
        return repository.save(discount);
    }

    @Override
    public Discount update(DiscountRequest request, Integer discountId) {
        if (request.getEndDate().before(request.getStartDate())){
            throw new RuntimeException("Ngày hết hạn phải sau ngày bắt đầu");
        }
        Discount discount = repository.findById(discountId)
                .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại"));
        DiscountMapper.mapDiscountRequestDiscount(request, discount);
        return repository.save(discount);
    }
}

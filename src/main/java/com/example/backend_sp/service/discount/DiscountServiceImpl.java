package com.example.backend_sp.service.discount;

import com.example.backend_sp.entity.Discount;
import com.example.backend_sp.mapper.DiscountMapper;
import com.example.backend_sp.repository.DiscountRepository;
import com.example.backend_sp.request.DiscountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService{

    private final DiscountRepository repository;
    @Override
    public Discount create(DiscountRequest request) {
        Discount discount = new Discount();
        DiscountMapper.mapDiscountRequestDiscount(request, discount);
        return repository.save(discount);
    }
}

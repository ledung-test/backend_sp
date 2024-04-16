package com.example.backend_sp.service.cart;

import com.example.backend_sp.entity.Cart;
import com.example.backend_sp.entity.CartItem;
import com.example.backend_sp.entity.Course;
import com.example.backend_sp.mapper.CourseMapper;
import com.example.backend_sp.repository.CartItemRepository;
import com.example.backend_sp.repository.CartRepository;
import com.example.backend_sp.repository.CourseDiscountRepository;
import com.example.backend_sp.repository.CourseRepository;
import com.example.backend_sp.request.CartItemRequest;
import com.example.backend_sp.response.CartItemResponse;
import com.example.backend_sp.response.CartResponse;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.utils.DiscountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final CartItemRepository cartItemRepository;
    private final CourseRepository courseRepository;

    private final CourseDiscountRepository courseDiscountRepository;

    //Lấy danh sách khóa học trong giỏ hàng theo ID giỏ hàng
    @Override
    public CartResponse getCoursesFromCart(Integer id) {
        if (!repository.existsCartByUserId(id)) {
            throw new RuntimeException("Giỏ hàng không tồn tại");
        } else {
            Cart cart = repository.findByUserId(id);
            List<CartItem> cartItemList = cartItemRepository.findByCartId(cart.getId());
            List<CartItemResponse> cartItemResponseList = new ArrayList<>();
            BigDecimal totalPrice = getTotalPrice(cartItemList);
            BigDecimal totalDiscount = BigDecimal.ZERO;
            BigDecimal totalDiscountedPrice = BigDecimal.ZERO;
            int percentDiscount = 0;
            if (!cartItemList.isEmpty()) {
                for (CartItem cartItem : cartItemList) {
                    CourseResponse courseResponse = new CourseResponse();
                    Course course = cartItem.getCourse();
                    DiscountUtils.setDiscountToCourseResponse(courseDiscountRepository, course, courseResponse);
                    CourseMapper.mapCourseToCourseResponse(course, courseResponse);
                    if (courseResponse.getDiscount() != null) {
                        totalDiscount = course.getPrice().subtract(totalDiscount.add(courseResponse.getDiscount()));
                    }
                    CartItemResponse cartItemResponse = CartItemResponse.builder()
                            .id(cartItem.getId())
                            .course(courseResponse)
                            .build();
                    cartItemResponseList.add(cartItemResponse);
                }
                totalDiscountedPrice = getTotalDiscountedPrice(totalPrice, totalDiscount);
                if (!totalDiscount.equals(BigDecimal.ZERO)) {
                    percentDiscount = getPercentDiscount(totalPrice, totalDiscount);
                }
            }
            return CartResponse.builder()
                    .cartItems(cartItemResponseList)
                    .totalPrice(totalPrice)
                    .totalDiscount(totalDiscount)
                    .totalDiscountedPrice(totalDiscountedPrice)
                    .percentDiscount(percentDiscount)
                    .build();
        }
    }

    @Override
    public BigDecimal getTotalPrice(List<CartItem> cartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalPrice = totalPrice.add(cartItem.getCourse().getPrice());
        }
        return totalPrice;
    }

    @Override
    public BigDecimal getTotalDiscountedPrice(BigDecimal totalPrice, BigDecimal totalDiscount) {
        return totalPrice.subtract(totalDiscount);
    }

    @Override
    public int getPercentDiscount(BigDecimal totalPrice, BigDecimal totalDiscount) {
        BigDecimal discountPercentage = totalDiscount.divide(totalPrice, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
        return discountPercentage.setScale(0, RoundingMode.HALF_UP).intValue();
    }

    //Lưu một khóa học vào giỏ hàng
    @Override
    public CartItem save(CartItemRequest request) {
        if (!cartItemRepository.existsCartItemByCartIdAndCourseId(request.getCart_id(), request.getCourse_id())) {
            Cart cart = repository.findById(request.getCart_id())
                    .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại"));
            Course course = courseRepository.findById(request.getCourse_id())
                    .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .course(course)
                    .build();
            return cartItemRepository.save(cartItem);
        } else {
            throw new RuntimeException("Khóa học đã có trong giỏ hàng");
        }

    }

    //Xóa khóa học khỏi giỏ hàng
    @Override
    public void deleteById(Integer id) {
        if (cartItemRepository.existsCartItemById(id)) {
            cartItemRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy khóa học trong giỏ hàng");
        }
    }

    //Lấy ID giỏ hàng theo User ID
    @Override
    public Integer getIdCartByUserId(Integer userId) {
        if (repository.existsCartByUserId(userId)) {
            Cart cart = repository.findByUserId(userId);
            return cart.getId();
        } else {
            throw new RuntimeException("Không tìm thấy giỏ hàng");
        }
    }
}

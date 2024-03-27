package com.example.backend_sp.service.cart;

import com.example.backend_sp.entity.Cart;
import com.example.backend_sp.entity.CartItem;
import com.example.backend_sp.entity.Course;
import com.example.backend_sp.mapper.CourseMapper;
import com.example.backend_sp.repository.CartItemRepository;
import com.example.backend_sp.repository.CartRepository;
import com.example.backend_sp.repository.CourseRepository;
import com.example.backend_sp.request.CartItemRequest;
import com.example.backend_sp.response.CartItemResponse;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final CartItemRepository cartItemRepository;
    private final CourseRepository courseRepository;

    //Lấy danh sách khóa học trong giỏ hàng theo ID giỏ hàng
    @Override
    public List<CartItemResponse> getCoursesFromCart(Integer id) {
        if (repository.existsCartByUserId(id)) {
            Cart cart = repository.findByUserId(id);
            List<CartItem> cartItemList = cartItemRepository.findByCart_Id(cart.getId());
            List<CartItemResponse> cartItemResponseList = new ArrayList<>();
            if (!cartItemList.isEmpty()) {
                for (CartItem cartItem : cartItemList) {
                    CourseResponse courseResponse = new CourseResponse();
                    CourseMapper.mapCourseToCourseResponse(cartItem.getCourse(), courseResponse);
                    CartItemResponse cartItemResponse = CartItemResponse.builder()
                            .id(cartItem.getId())
                            .course(courseResponse)
                            .build();
                    cartItemResponseList.add(cartItemResponse);
                }
            }
            return cartItemResponseList;

        } else {
            throw new RuntimeException("Giỏ hàng không tồn tại");
        }
    }

    //Lưu một khóa học vào giỏ hàng
    @Override
    public CartItem save(CartItemRequest request) {
        if (!cartItemRepository.existsCartItemByCart_IdAndCourse_Id(request.getCart_id(), request.getCourse_id())) {
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

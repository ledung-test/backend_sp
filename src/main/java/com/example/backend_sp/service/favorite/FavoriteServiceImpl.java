package com.example.backend_sp.service.favorite;

import com.example.backend_sp.entity.*;
import com.example.backend_sp.mapper.CourseMapper;
import com.example.backend_sp.repository.CourseRepository;
import com.example.backend_sp.repository.FavoriteRepository;
import com.example.backend_sp.repository.UserRepository;
import com.example.backend_sp.request.FavoriteRequest;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.response.FavoriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository repository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    //Lấy danh sách yêu thích theo User ID
    @Override
    public List<FavoriteResponse> getFavoriteByUserId(Integer id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        List<Favorite> favorites = repository.findByUserId(id);
        List<FavoriteResponse> favoriteResponseList = new ArrayList<>();
        if (!favorites.isEmpty()) {
            for (Favorite favorite : favorites) {
                CourseResponse courseResponse = new CourseResponse();
                CourseMapper.mapCourseToCourseResponse(favorite.getCourse(), courseResponse);
                FavoriteResponse favoriteResponse = FavoriteResponse.builder()
                        .id(favorite.getId())
                        .course(courseResponse)
                        .build();
                favoriteResponseList.add(favoriteResponse);
            }
        }
        return favoriteResponseList;
    }

    //Thêm khóa học vào danh sách yêu thích
    @Override
    public Favorite save(FavoriteRequest request) {
        if (!repository.existsByUserIdAndCourseId(request.getUser_id(), request.getCourse_id())) {
            User user = userRepository.findById(request.getUser_id())
                    .orElseThrow(() -> new RuntimeException("User không tồn tại"));
            Course course = courseRepository.findById(request.getCourse_id())
                    .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));
            Favorite favorite = Favorite.builder()
                    .user(user)
                    .course(course)
                    .build();
            return repository.save(favorite);
        } else {
            throw new RuntimeException("Khóa học đã có trong danh sách yêu thích");
        }
    }

    //Xóa khóa học khỏi danh sách yêu thích
    @Override
    public void deleteById(Integer id) {
        if (repository.existsFavoriteById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy trong danh sách yêu thích");
        }
    }
}

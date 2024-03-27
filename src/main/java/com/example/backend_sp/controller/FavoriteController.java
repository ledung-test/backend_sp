package com.example.backend_sp.controller;

import com.example.backend_sp.request.FavoriteRequest;
import com.example.backend_sp.response.FavoriteResponse;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.favorite.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FavoriteController {
    private final FavoriteService service;

    //GET: Lấy danh sách khóa học yêu thích theo User ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCoursesFromFavorite(@PathVariable Integer id) {
        List<FavoriteResponse> favoriteResponseList = service.getFavoriteByUserId(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy danh sách yêu thích thành công")
                .status(HttpStatus.OK)
                .data(favoriteResponseList)
                .build());
    }

    //POST: Thêm mới một khóa học vào danh sách yêu thích
    @PostMapping()
    public ResponseEntity<ResponseObject> insertCourseToFavorite(@RequestBody @Valid FavoriteRequest request) {
        service.save(request);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Thêm vào danh sách yêu thích thành công")
                .status(HttpStatus.CREATED)
                .build());
    }

    //DELETE: Xóa một khóa học khỏi danh sách yêu thích
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCourseFromFavorite(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Xóa khỏi danh sách yêu thích thành công")
                .status(HttpStatus.OK)
                .build());
    }
}

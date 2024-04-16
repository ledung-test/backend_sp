package com.example.backend_sp.controller;

import com.example.backend_sp.request.UserRequest;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.response.UserResponse;
import com.example.backend_sp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService service;
    @GetMapping()
    public ResponseEntity<ResponseObject> findAllByRoleUser(){
        List<UserResponse> users = service.findAllByRoleUser();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy danh sách user thành công.")
                        .status(HttpStatus.OK)
                        .data(users)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Integer id){
        UserResponse user = service.getUserById(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy user thành công.")
                        .status(HttpStatus.OK)
                        .data(user)
                        .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateActivated(
            @PathVariable Integer id,
            @RequestBody UserRequest request){
        service.updateActivated(id, request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật user thành công.")
                        .status(HttpStatus.OK)
                        .build());
    }

}

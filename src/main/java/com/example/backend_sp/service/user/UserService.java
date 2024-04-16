package com.example.backend_sp.service.user;


import com.example.backend_sp.entity.User;
import com.example.backend_sp.request.UserRequest;
import com.example.backend_sp.response.UserResponse;

import java.util.List;

public interface UserService {
    User findById(Integer id);

    List<UserResponse> findAllByRoleUser();

    void updateActivated(Integer id, UserRequest request);

    UserResponse getUserById(Integer id);
}

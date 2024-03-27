package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.User;
import com.example.backend_sp.request.UserRequest;
import com.example.backend_sp.response.UserResponse;

public class UserMapper {
    public static void mapUserRequestToUser(UserRequest request, User user){
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
    }

    public static void mapUserToUserResponse(User user, UserResponse response){
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
    }

}

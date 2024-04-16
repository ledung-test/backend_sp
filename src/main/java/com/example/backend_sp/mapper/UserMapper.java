package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.User;
import com.example.backend_sp.response.UserResponse;

import java.util.List;

public class UserMapper {
    public static void mapUserToUserResponse(User user, UserResponse response){
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setActivated(user.isActivated());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
    }

    public static void mapUserListToUserResponseList(List<User> users, List<UserResponse> userResponseList){
        if (!users.isEmpty()){
            for (User user : users) {
                UserResponse userResponse = new UserResponse();
                UserMapper.mapUserToUserResponse(user, userResponse);
                userResponseList.add(userResponse);
            }
        }
    }
}

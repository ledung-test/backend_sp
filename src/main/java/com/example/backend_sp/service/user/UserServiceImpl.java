package com.example.backend_sp.service.user;


import com.example.backend_sp.entity.enums.Role;
import com.example.backend_sp.entity.User;
import com.example.backend_sp.mapper.UserMapper;
import com.example.backend_sp.repository.UserRepository;
import com.example.backend_sp.request.UserRequest;
import com.example.backend_sp.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public User findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
    }

    @Override
    public List<UserResponse> findAllByRoleUser() {
        List<User> users = repository.findAllByRole(Role.USER);
        List<UserResponse> userResponseList = new ArrayList<>();
        UserMapper.mapUserListToUserResponseList(users, userResponseList);
        return userResponseList;
    }

    @Override
    public void updateActivated(Integer id, UserRequest request) {
        User user = findById(id);
        user.setActivated(request.isActivated());
        repository.save(user);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = findById(id);
        UserResponse userResponse = new UserResponse();
        UserMapper.mapUserToUserResponse(user, userResponse);
        return userResponse;
    }
}

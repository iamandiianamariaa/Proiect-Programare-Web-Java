package com.example.shop.service;

import com.example.shop.domain.User;
import com.example.shop.dto.UserDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.UserMapper;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public Boolean checkIfUserHasAdminRole(Long adminId) {
        if (userRepository.findById(adminId).isPresent())
            return userRepository.checkIfUserIsAdmin(adminId);
        else
            throw EntityNotFoundException.builder().entityId(adminId).entityName("User").build();
    }

    public Boolean checkIfUserHasUserRole(Long userId) {
        if (userRepository.findById(userId).isPresent())
            return userRepository.checkIfUserIsUser(userId);
        else
            throw EntityNotFoundException.builder().entityId(userId).entityName("User").build();
    }

    public UserDto create(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        user.setAccountCreated(LocalDateTime.now());
        User savedReview = userRepository.save(user);

        return userMapper.mapToDto(savedReview);
    }

    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(userId)
                        .entityName("User")
                        .build()
                );
    }

    public User update(Long userId, UserDto userDto) {
        User user = getById(userId);
        User updatedUser = userMapper.update(userDto, user);

        return userRepository.save(updatedUser);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        getById(id);
        userRepository.deleteById(id);
    }
}

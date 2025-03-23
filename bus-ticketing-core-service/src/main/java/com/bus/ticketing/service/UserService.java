package com.bus.ticketing.service;

import com.bus.ticketing.model.dto.User;
import com.bus.ticketing.model.entity.UserEntity;
import com.bus.ticketing.model.mapper.UserMapper;
import com.bus.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();

    public User readUser(String identification) {
        UserEntity userEntity = userRepository.findByIdentificationNumber(identification)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + identification));
        return userMapper.convertToDto(userEntity);
    }

    public User readUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userMapper.convertToDto(userEntity);
    }

    public List<User> readUsers(Pageable pageable) {
        return userMapper.convertToDtoList(userRepository.findAll(pageable).getContent());
    }

    public User createUser(User user) {
        UserEntity userEntity = userMapper.convertToEntity(user);
        userEntity = userRepository.save(userEntity);
        return userMapper.convertToDto(userEntity);
    }

    public User updateUser(Long id, User user) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        UserEntity updatedUser = userMapper.convertToEntity(user);
        updatedUser.setId(id);
        updatedUser = userRepository.save(updatedUser);

        return userMapper.convertToDto(updatedUser);
    }
}
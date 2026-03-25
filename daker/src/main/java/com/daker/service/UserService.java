package com.daker.service;

import com.daker.domain.dto.request.UserRequestDTO;
import com.daker.domain.dto.response.UserResponseDTO;
import com.daker.domain.entity.User;
import com.daker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO.LoginDTO login(UserRequestDTO.LoginDTO request) {
        User user = userRepository.findIdByLoginIdandPassword(request.getLoginId(), request.getPassword());

        return UserResponseDTO.LoginDTO.builder()
                .userId(user.getId())
                .build();
    }

    public void signup(UserRequestDTO.SignupDTO request) {
        User user = User.builder()
            .email(request.getEmail())
            .loginId(request.getLoginId())
            .password(request.getPassword())
            .name(request.getName())
            .nickname(request.getNickName()).build();
    
        userRepository.save(user);
    }
}

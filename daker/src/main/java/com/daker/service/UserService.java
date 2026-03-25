package com.daker.service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO.LoginDTO login(UserRequestDTO.LoginDTO request) {
        User user = userRepository.findIdByLoginIdandPassword;

        return UserResponseDTO.LoginDTO.builder
                .userId(user.getId())
                .build();
    }

    public void signup(UserRequestDTO.SignupDTO request) {
        User user = User.builder
            .email(request.getEmail())
            .loginId(request.getLoginId())
            .password(request.getPassword())
            .name(request.getName())
            .nickname(request.getNickName()).build();
    
        userRepository.save(user);
    }
}

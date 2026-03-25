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
}

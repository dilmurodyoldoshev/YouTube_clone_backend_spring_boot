package uz.app.pdptube.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pdptube.dto.UserAuthRequestDTO;
import uz.app.pdptube.dto.UserDTO;
import uz.app.pdptube.dto.UserResponseDTO;
import uz.app.pdptube.entity.User;
import uz.app.pdptube.filter.JwtProvider;
import uz.app.pdptube.payload.ResponseMessage;
import uz.app.pdptube.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    @Override
    public ResponseMessage signUp(UserDTO userDTO) {
        boolean existsByEmail = userRepository.existsByEmail(userDTO.getEmail());
        if (existsByEmail) {
            return new ResponseMessage(false, "email already exists", userDTO.getEmail());
        }

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .age(userDTO.getAge())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
        User savedUser = userRepository.save(user);
        UserResponseDTO userResponseDTO = toUserResponseDTO(savedUser);
        return new ResponseMessage(true, "success", userResponseDTO);
    }

    @Override
    public ResponseMessage signIn(UserAuthRequestDTO userAuthRequestDTO) {
        User user = userRepository.findByEmail(userAuthRequestDTO.getEmail()).orElseThrow(
                () -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(userAuthRequestDTO.getPassword())) {
            return new ResponseMessage(false, "Invalid email or Password", null);
        }

        return new ResponseMessage(true, "Sign-in successfully", jwtProvider.generateToken(user.getEmail()));
    }

    private UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(Long.valueOf(user.getId()));
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        return dto;
    }

}

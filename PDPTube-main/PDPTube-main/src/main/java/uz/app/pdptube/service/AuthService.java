package uz.app.pdptube.service;

import uz.app.pdptube.dto.UserAuthRequestDTO;
import uz.app.pdptube.dto.UserDTO;
import uz.app.pdptube.payload.ResponseMessage;

public interface AuthService {
    ResponseMessage signUp(UserDTO userDTO);

    ResponseMessage signIn(UserAuthRequestDTO userAuthRequestDTO);
}

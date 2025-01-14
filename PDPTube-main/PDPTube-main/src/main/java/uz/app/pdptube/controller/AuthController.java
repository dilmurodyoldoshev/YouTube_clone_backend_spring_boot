package uz.app.pdptube.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.app.pdptube.dto.UserAuthRequestDTO;
import uz.app.pdptube.dto.UserDTO;
import uz.app.pdptube.payload.ResponseMessage;
import uz.app.pdptube.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public HttpEntity<ResponseMessage> signUp(@RequestBody UserDTO userDTO) {
        ResponseMessage signUp = authService.signUp(userDTO);
        return ResponseEntity.status(signUp.success() ? 200 : 409).body(signUp);
    }

    @PostMapping("/sign-in")
    public HttpEntity<ResponseMessage> signIn(@RequestBody UserAuthRequestDTO userAuthRequestDTO) {
        ResponseMessage responseMessage = authService.signIn(userAuthRequestDTO);
        return ResponseEntity.status(responseMessage.success() ? 200 : 409).body(responseMessage);
    }

}

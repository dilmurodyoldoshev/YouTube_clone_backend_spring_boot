package uz.app.pdptube.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAuthRequestDTO {

    private String email;
    private String password;
}

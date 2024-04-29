package comspringboot.challengewl.controllers.mapper;

import comspringboot.challengewl.controllers.dtos.AuthRequestDTO;
import comspringboot.challengewl.controllers.dtos.AuthResponseDTO;
import comspringboot.challengewl.models.LoginModel;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public LoginModel toModel(AuthRequestDTO authRequestDTO) {
        LoginModel loginModel = new LoginModel();
        loginModel.setEmail(authRequestDTO.email());
        loginModel.setPassword(authRequestDTO.password());
        return loginModel;
    }

    public AuthResponseDTO toDTO(String tokenJwt) {
        return new AuthResponseDTO(
                tokenJwt
        );
    }
}

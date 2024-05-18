package comspringboot.challengewl.controllers.mapper;

import comspringboot.challengewl.controllers.dtos.LoginRequestDTO;
import comspringboot.challengewl.controllers.dtos.LoginResponseDTO;
import comspringboot.challengewl.models.LoginModel;
import comspringboot.challengewl.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class CooworkMapper {

    public LoginModel toModel(LoginRequestDTO loginRequestDTO) {
        LoginModel loginModel = new LoginModel();
        loginModel.setEmail(loginRequestDTO.email());
        loginModel.setPassword(loginRequestDTO.password());
        return loginModel;
    }

    public LoginResponseDTO toDTO(String accessToken, String refreshToken, UserModel user) {
        user.setPassword(null);
        user.setPasswordConfirmation(null);
        return new LoginResponseDTO(
                accessToken,
                refreshToken,
                user
        );
    }
}

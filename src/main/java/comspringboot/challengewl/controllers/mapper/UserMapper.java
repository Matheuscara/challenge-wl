package comspringboot.challengewl.controllers.mapper;

import comspringboot.challengewl.controllers.dtos.UserRequestDTO;
import comspringboot.challengewl.controllers.dtos.UserResponseDTO;
import comspringboot.challengewl.enums.TypeUsers;
import comspringboot.challengewl.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserModel toModel(UserRequestDTO dto) {
        UserModel userModel = new UserModel();
        userModel.setEmail(dto.email());
        userModel.setFirstName(dto.firstName());
        userModel.setLastName(dto.lastName());
        userModel.setBirthDate(dto.birthDate());
        userModel.setPassword(dto.password());
        userModel.setCpf(dto.cpf());
        userModel.setImage(dto.image());
        userModel.setRole(TypeUsers.ADMIN);
        userModel.setPhoneNumber(dto.phoneNumber());
        return userModel;
    }

    public UserResponseDTO toDTO(UserModel userModel) {
        return new UserResponseDTO(
                userModel.getId(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                userModel.getBirthDate(),
                userModel.getCpf(),
                userModel.getImage(),
                userModel.getPhoneNumber(),
                userModel.getRole()
        );
    }
}

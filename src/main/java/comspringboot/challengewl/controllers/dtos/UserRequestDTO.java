package comspringboot.challengewl.controllers.dtos;

import comspringboot.challengewl.enums.TypeUsers;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record UserRequestDTO(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String email,

        @NotNull
        Date birthDate,

        TypeUsers typeUser,

        @NotBlank
        String password,

        @NotBlank
        String cpf,

        @NotBlank
        String image,

        @NotBlank
        String phoneNumber
        ) {

}
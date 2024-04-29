package comspringboot.challengewl.controllers.dtos;

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
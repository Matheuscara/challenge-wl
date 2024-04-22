package comspringboot.challengewl.controllers.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String email
) {

}
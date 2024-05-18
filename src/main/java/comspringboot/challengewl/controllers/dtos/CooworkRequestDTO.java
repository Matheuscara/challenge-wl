package comspringboot.challengewl.controllers.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank
        String email,

        @NotBlank
        String password
) {

}
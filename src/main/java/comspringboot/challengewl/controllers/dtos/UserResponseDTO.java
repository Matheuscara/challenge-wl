package comspringboot.challengewl.controllers.dtos;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email
) {}

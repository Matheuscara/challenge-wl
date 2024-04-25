package comspringboot.challengewl.controllers.dtos;

import comspringboot.challengewl.enums.TypeUsers;

import java.util.Date;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        Date birthDate,
        TypeUsers typeUser,
        String cpf,
        String image,
        String phoneNumber
) {}

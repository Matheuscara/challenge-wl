package comspringboot.challengewl.controllers.dtos;

import comspringboot.challengewl.models.AddressModel;

public record CooworkResponseDTO(
        Number id,
        String name,
        String imageCoowork,
        String administrator,
        String email,
        String admImage,
        String phoneNumber,
        AddressModel address,
        boolean coffe,
        boolean safeBox,
        boolean meetingRoom
        ) {
}


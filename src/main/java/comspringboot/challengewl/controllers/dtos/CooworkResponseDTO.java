package comspringboot.challengewl.controllers.dtos;

import comspringboot.challengewl.models.CooworkModel;

public record CooworkResponseDTO(
        CooworkModel[] cooworks
) {

}
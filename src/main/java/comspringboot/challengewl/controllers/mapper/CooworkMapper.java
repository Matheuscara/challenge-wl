package comspringboot.challengewl.controllers.mapper;

import comspringboot.challengewl.controllers.dtos.CooworkRequestDTO;
import comspringboot.challengewl.models.CooworkModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import comspringboot.challengewl.controllers.dtos.CooworkResponseDTO;

import java.util.List;

@Mapper
public interface testMapper {
    testMapper INSTANCE = Mappers.getMapper(testMapper.class);

    @Mapping(target = "user", ignore = true)
    CooworkModel toModel(CooworkRequestDTO cooworkRequestDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "imageCoowork", source = "logo")
    @Mapping(target = "administrator", source = "user.firstName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "admImage", source = "user.image")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "address", source = "address")
    CooworkResponseDTO toDTO(CooworkModel cooworkModels);
    List<CooworkResponseDTO> toDTOList(List<CooworkModel> cooworkModels);
}

package comspringboot.challengewl.controllers;

import comspringboot.challengewl.controllers.dtos.UserRequestDTO;
import comspringboot.challengewl.controllers.dtos.UserResponseDTO;
import comspringboot.challengewl.controllers.mapper.UserMapper;
import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController()
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            method = "Get",
            description = "Select user in database",
            responses = @ApiResponse(
                    description = "Return object of user",
                    content = @Content(schema = @Schema(implementation = UserModel.class))
            )
    )
    @GetMapping("{id}")
    public UserResponseDTO FindByID(@PathVariable(value = "id") long id) {
        final UserModel user = userService.findById(id);
        return userMapper.toDTO(user);
    }

    @Operation(
            method = "Put",
            description = "Change parameters user in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
            ),
            responses = @ApiResponse(
                    description = "Return object of user",
                    content = @Content(schema = @Schema(implementation = UserModel.class))
            )
    )
    @ResponseStatus(OK)
    @PutMapping("/{id}")
    public UserResponseDTO Update(@RequestBody @Valid UserRequestDTO userRequestBody, @PathVariable(value = "id") long id) {
        final UserModel user = userService.update(userMapper.toModel(userRequestBody), id);
        return userMapper.toDTO(user);
    }

    @Operation(
            method = "Delete",
            description = "Delete user in database"
    )
    @ResponseStatus(OK)
    @DeleteMapping("/{id}")
    public void Delete(@PathVariable(value = "id") long id) {
        userService.remove(id);
    }
}

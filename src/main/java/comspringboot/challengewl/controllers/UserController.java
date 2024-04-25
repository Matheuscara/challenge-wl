package comspringboot.challengewl.controllers;

import comspringboot.challengewl.controllers.dtos.UserRequestDTO;
import comspringboot.challengewl.controllers.dtos.UserResponseDTO;
import comspringboot.challengewl.controllers.mapper.UserMapper;
import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.*;

@RestController()
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            method = "Post",
            description = "Create user in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
            ),
            responses = @ApiResponse(
                    description = "Return object of user, with relation in links",
                    content = @Content(schema = @Schema(implementation = UserModel.class)),
                    links = @Link(
                          name = "Detalhes",
                            description = "Relation to go detalhes of user created",
                            operationRef = "Get"

                    )
            )
    )
    @ResponseStatus(CREATED)
    @PostMapping
    public UserResponseDTO save(@RequestBody @Valid UserRequestDTO request) {
        final UserModel user = userService.save(userMapper.toModel(request));
        return userMapper.toDTO(user);
    }

    @Operation(
            method = "Get",
            description = "Select user in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = false
            ),
            responses = @ApiResponse(
                    description = "Return object of user",
                    content = @Content(schema = @Schema(implementation = UserModel.class))
            )
    )
    @GetMapping("{id}")
    public UserResponseDTO FindUserByID(@PathVariable(value = "id") long id) {
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
        final UserModel user = userService.update(userRequestBody, id);
        return userMapper.toDTO(user);
    }

    @Operation(
            method = "Delete",
            description = "Delete user in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = false
            )
    )
    @ResponseStatus(OK)
    @DeleteMapping("/{id}")
    public void Delete(@PathVariable(value = "id") long id) {
        userService.remove(id);
    }
}

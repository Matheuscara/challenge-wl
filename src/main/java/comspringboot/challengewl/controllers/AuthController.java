package comspringboot.challengewl.controllers;

import comspringboot.challengewl.controllers.dtos.AuthRequestDTO;
import comspringboot.challengewl.controllers.dtos.AuthResponseDTO;
import comspringboot.challengewl.controllers.dtos.UserRequestDTO;
import comspringboot.challengewl.controllers.mapper.AuthMapper;
import comspringboot.challengewl.controllers.mapper.UserMapper;
import comspringboot.challengewl.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController()
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;

    @Operation(
            method = "Post",
            description = "Create user in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
            ),
            responses = @ApiResponse(
                    description = "Return jwt token",
                    content = @Content(schema = @Schema(implementation = AuthMapper.class))
            )
    )
    @ResponseStatus(CREATED)
    @PostMapping("/save")
    public AuthResponseDTO save(@RequestBody @Valid UserRequestDTO request) {
        final String tokenJwt = userService.save(userMapper.toModel(request));
        return authMapper.toDTO(tokenJwt);
    }

    @Operation(
            method = "Post",
            description = "Authenticate user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
            ),
            responses = @ApiResponse(
                    description = "Return jwt token",
                    content = @Content(schema = @Schema(implementation = AuthMapper.class))
            )
    )
    @ResponseStatus(CREATED)
    @PostMapping
    public AuthResponseDTO authenticate(@Valid @RequestBody AuthRequestDTO request) {
        final String tokenJwt = userService.authenticate(authMapper.toModel(request));
        return authMapper.toDTO(tokenJwt);
    }
}

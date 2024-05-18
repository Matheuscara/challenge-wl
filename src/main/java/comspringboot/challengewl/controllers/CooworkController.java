package comspringboot.challengewl.controllers;

import comspringboot.challengewl.configurations.JwtService;
import comspringboot.challengewl.controllers.dtos.LoginRequestDTO;
import comspringboot.challengewl.controllers.dtos.LoginResponseDTO;
import comspringboot.challengewl.controllers.dtos.UserRequestDTO;
import comspringboot.challengewl.controllers.mapper.LoginMapper;
import comspringboot.challengewl.controllers.mapper.UserMapper;
import comspringboot.challengewl.models.LoginModel;
import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@RestController()
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final LoginMapper loginMapper;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Operation(
            method = "Post",
            description = "Create user in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
            ),
            responses = @ApiResponse(
                    description = "Return jwt token",
                    content = @Content(schema = @Schema(implementation = LoginMapper.class))
            )
    )
    @ResponseStatus(CREATED)
    @PostMapping("/save")
    public HttpStatus save(@RequestBody @Valid UserRequestDTO request) {
        userService.save(userMapper.toModel(request));
        return HttpStatus.OK;
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
                    content = @Content(schema = @Schema(implementation = LoginMapper.class))
            )
    )
    @ResponseStatus(CREATED)
    @PostMapping
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        final LoginModel login = loginMapper.toModel(request);
        final String accessToken = userService.authenticate(login);
        final String refreshToken = userService.refreshToken(accessToken);
        final UserModel userModel = userService.findByEmail(login.getEmail());
        return loginMapper.toDTO(accessToken, refreshToken, userModel);
    }

    @PostMapping("/refresh")
    public LoginResponseDTO refreshToken(@RequestHeader("Authorization") String token) {
        final String tokenJwt = userService.refreshToken(token);
        final String username = jwtService.extractUsername(token);
        final UserModel userModel = userService.findByEmail(username);
        return loginMapper.toDTO("", tokenJwt, userModel);
    }

    @GetMapping("/validate")
    public HttpStatus validate(@RequestHeader("Authorization") String token) {
        userService.refreshToken(token);
        return HttpStatus.OK;
    }
}

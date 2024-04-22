package comspringboot.challengewl.controllers;

import comspringboot.challengewl.controllers.dtos.UserRequestDTO;
import comspringboot.challengewl.controllers.dtos.UserResponseDTO;
import comspringboot.challengewl.controllers.mapper.UserMapper;
import comspringboot.challengewl.exceptions.errors.UserErrorResponse;
import comspringboot.challengewl.exceptions.UserConflictException;
import comspringboot.challengewl.exceptions.UserNotFoundException;
import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
        final UserModel user = userService.createUser(userMapper.toModel(request));
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
    public ResponseEntity<Object> FindUserByID(@PathVariable(value = "id") int id) {
        Optional<UserModel> userSearch = userService.findById(id);
        // Verify user find
        if(userSearch.isPresent()) {
            // Return user and OK status
            return ResponseEntity.status(OK).body(userSearch.get());
        }
        // Return bad request and NOT FOUND status
        throw new UserNotFoundException("User id not found - " + id);
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
    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateUser(@RequestBody @Valid UserRequestDTO userRequestBody, @PathVariable(value = "id") int id) {
        // Search user in database by id, and return optional value
        Optional<UserModel> userSearch = userService.findById(id);
        // Verify user if exist
        if(userSearch.isPresent()) {
            // If it exists, create new model of User
            UserModel userUpdate = userSearch.get();
            // Replace parameters with beanUtils
            BeanUtils.copyProperties(userRequestBody, userUpdate);
            // Update User
            userService.updateUser(userUpdate);
            // Return http response to client
            return ResponseEntity.status(OK).body("User with id: " + userSearch.get().getId() + " updated");
        }
        // If user not exist, return http error response
        throw new UserNotFoundException("User id not found - " + id);
    }

    @Operation(
            method = "Delete",
            description = "Delete user in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = false
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteUser(@PathVariable(value = "id") int id) {
        // Search user in database by id, and return optional value
        Optional<UserModel> userSearch = userService.findById(id);
        // Verify user if exist
        if(userSearch.isPresent()) {
            // If it exists, create new model of User
            UserModel user = userSearch.get();
            // delete User in dataBase
            userService.removeUser(user);
            // Return response http to client
            return ResponseEntity.status(OK).body("User with id: " + userSearch.get().getId() + " deleted");
        }
        // If user not exist, return http error response
        throw new UserNotFoundException("User id not found - " + id);
    }


    // Add an exception handler using @ExceptionHandler
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException exc) {
        // Create a UserErrorResponse
        UserErrorResponse errorResponse = new UserErrorResponse();
        // Set UserErrorResponse
        errorResponse.setStatus(NOT_FOUND.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        // return ResponseEntity
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    // Add an exception handler using @ExceptionHandler
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserConflictException exc) {
        // Create a UserErrorResponse
        UserErrorResponse errorResponse = new UserErrorResponse();
        // Set UserErrorResponse
        errorResponse.setStatus(CONFLICT.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        // return ResponseEntity
        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    // Ass another exception handler ... to catch any exception
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception exc) {
        // Create a UserErrorResponse
        UserErrorResponse errorResponse = new UserErrorResponse();
        // Set UserErrorResponse
        errorResponse.setStatus(BAD_REQUEST.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        // return ResponseEntity
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
}

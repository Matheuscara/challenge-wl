package comspringboot.challengewl.controllers;

import comspringboot.challengewl.dtos.UserRequestDto;
import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            method = "Post",
            description = "Create user in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDto.class))
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public UserModel SaveUser(@RequestBody @Valid UserRequestDto userRequestBody) {
        // Search user in database by id, and return optional value
        Optional<UserModel> userExist = userService.findByEmail(userRequestBody.email());
        // If user existed, return error
        if (userExist.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, userRequestBody.email());
        }
        // Create UserModel
        UserModel userCreated = new UserModel();
        // Copy properties in Body to UserModel created
        BeanUtils.copyProperties(userRequestBody, userCreated);
        // Save model in database
        userService.createUser(userCreated);
        // Reference a link to user details
        return userCreated.add(linkTo(methodOn(UserController.class).FindUserByID(userCreated.getId())).withRel("Detalhes"));
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
    @GetMapping("/{id}")
    public ResponseEntity<Object> FindUserByID(@PathVariable(value = "id") int id) {
        // Search user in database by id, and return optional value
        Optional<UserModel> userSearch = userService.findById(id);
        // Verify user find
        if(userSearch.isPresent()) {
            // Return user and OK status
            return ResponseEntity.status(HttpStatus.OK).body(userSearch.get());
        }
        // Return bad request and NOT FOUND status
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @Operation(
            method = "Put",
            description = "Change parameters user in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDto.class))
            ),
            responses = @ApiResponse(
                    description = "Return object of user",
                    content = @Content(schema = @Schema(implementation = UserModel.class))
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateUser(@RequestBody @Valid UserRequestDto userRequestBody, @PathVariable(value = "id") int id) {
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
            return ResponseEntity.status(HttpStatus.OK).body("User with id: " + userSearch.get().getId() + " updated");
        }
        // If user not exist, return http error response
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
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
            return ResponseEntity.status(HttpStatus.OK).body("User with id: " + userSearch.get().getId() + " deleted");
        }
        // If user not exist, return http error response
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}

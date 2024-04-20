package comspringboot.challengewl.controllers;

import comspringboot.challengewl.dtos.PeopleRequestDto;
import comspringboot.challengewl.models.PeopleModel;
import comspringboot.challengewl.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public PeopleModel SavePeople(@RequestBody @Valid PeopleRequestDto peopleRequestBody) {
        // Create PeopleModel
        PeopleModel peopleCreated = new PeopleModel();
        // Copy properties in Body to PeopleModel created
        BeanUtils.copyProperties(peopleRequestBody, peopleCreated);
        // Save model in database
        peopleService.create(peopleCreated);
        // Reference a link to people details
        return peopleCreated.add(linkTo(methodOn(PeopleController.class).FindPeopleByID(peopleCreated.getId())).withRel("Detalhes"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> FindPeopleByID(@PathVariable(value = "id") int id) {
        // Search people in database by id, and return optional value
        Optional<PeopleModel> peopleSearch = peopleService.findById(id);
        // Verify people find
        if(peopleSearch.isPresent()) {
            // Return people and OK status
            return ResponseEntity.status(HttpStatus.OK).body(peopleSearch.get());
        }
        // Return bad request and NOT FOUND status
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("People not found");
    }

}

package comspringboot.challengewl.services;

import comspringboot.challengewl.models.PeopleModel;
import comspringboot.challengewl.repositories.PeopleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public PeopleModel create(PeopleModel people) {
        return peopleRepository.save(people);
    }

    public Optional<PeopleModel> findById(int id) {
        return peopleRepository.findById(id);
    }

}
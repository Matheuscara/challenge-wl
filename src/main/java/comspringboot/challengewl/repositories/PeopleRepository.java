package comspringboot.challengewl.repositories;

import comspringboot.challengewl.models.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleModel, Integer> {
}

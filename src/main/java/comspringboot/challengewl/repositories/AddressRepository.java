package comspringboot.challengewl.repositories;

import comspringboot.challengewl.models.AdressModel;
import comspringboot.challengewl.models.CooworkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends JpaRepository<AdressModel, Long> {
}

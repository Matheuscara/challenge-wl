package comspringboot.challengewl.repositories;

import comspringboot.challengewl.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE user_jpa SET email = :email, first_name = :firstName, last_name = :lastName WHERE id = :userId", nativeQuery = true)
    void updateUser(@Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("userId") int userId);

    @Query(value = "SELECT * FROM user_jpa WHERE email = :email", nativeQuery = true)
    Optional<UserModel> findByEmail(String email);
}

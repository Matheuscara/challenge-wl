package comspringboot.challengewl.services;

import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    };

    @Transactional
    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    };

    public Optional<UserModel> findById(int id) {
        return userRepository.findById(id);
    };

    @Transactional
    public void updateUser(UserModel user) {
        userRepository.updateUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getId());
    };

    @Transactional
    public void removeUser(UserModel user) {
        userRepository.delete(user);
    };

    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    };
}
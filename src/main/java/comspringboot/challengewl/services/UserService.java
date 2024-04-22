package comspringboot.challengewl.services;

import comspringboot.challengewl.exceptions.UserConflictException;
import comspringboot.challengewl.exceptions.UserNotFoundException;
import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserModel createUser(UserModel user) {
        if (findByEmail(user.getEmail()) != null) {
            throw new UserConflictException("user conflict exception");
        }
        return userRepository.save(user);
    }

    public UserModel findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found!"));
    }

//    @Transactional
//    public void updateUser(UserModel user) {
//        userRepository.updateUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getId());
//    }

    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found!"));
    }
}
package comspringboot.challengewl.services;

import comspringboot.challengewl.controllers.dtos.UserRequestDTO;
import comspringboot.challengewl.exceptions.UserConflictException;
import comspringboot.challengewl.exceptions.UserNotFoundException;
import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserModel save(UserModel user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserConflictException("User existed in database!");
        }
        return userRepository.save(user);
    }

    public UserModel findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    @Transactional
    public UserModel update(UserRequestDTO userProperty, Long id) {
        final UserModel user = findById(id);
        BeanUtils.copyProperties(userProperty, user);
        userRepository.save(user);
        return user;
    }

    public void remove(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }
}
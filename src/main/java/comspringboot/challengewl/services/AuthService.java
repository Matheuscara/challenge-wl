package comspringboot.challengewl.services;

import comspringboot.challengewl.configurations.JwtService;
import comspringboot.challengewl.exceptions.UserConflictException;
import comspringboot.challengewl.exceptions.UserNotFoundException;
import comspringboot.challengewl.exceptions.UserPasswordNotAccepted;
import comspringboot.challengewl.models.LoginModel;
import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String save(UserModel user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserConflictException("User existed in database!");
        }

        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new UserPasswordNotAccepted("Invalid Credentials!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordConfirmation(passwordEncoder.encode(user.getPasswordConfirmation()));

        userRepository.save(user);
        return generateToken(user);
    }

    public UserModel findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    public UserModel findUserByToken(String token) {
        String username = jwtService.extractUsername(token);
        return findByEmail(username);
    }

    @Transactional
    public UserModel update(UserModel user, Long id) {
        final UserModel userFind = findById(id);
        BeanUtils.copyProperties(user, userFind);
        userRepository.save(userFind);
        return userFind;
    }

    public void remove(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    public String authenticate(LoginModel user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        var userFind = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found!"));
        return generateToken(userFind);
    }

    private String generateToken(UserModel user) {
        return jwtService.generateToken(user);
    }

    public String refreshToken(String token) {
        UserModel user = userRepository.findByEmail(jwtService.extractUsername(token))
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        return jwtService.generateRefreshToken(user);
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    public String splitToken(String token) {
        String[] stringSplited = token.split("\\s+");
        if (stringSplited.length != 2 && !stringSplited[0].equalsIgnoreCase("Bearer")) {
            new Exception("Invalid token");
        }
        return stringSplited[1];
    }
}
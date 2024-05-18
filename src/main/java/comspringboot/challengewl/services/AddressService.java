package comspringboot.challengewl.services;

import comspringboot.challengewl.configurations.JwtService;
import comspringboot.challengewl.exceptions.UserNotFoundException;
import comspringboot.challengewl.models.AddressModel;
import comspringboot.challengewl.repositories.AddressRepository;
import comspringboot.challengewl.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AddressRepository addressRepository;

    public AddressModel findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Adress not found!"));
    }
}
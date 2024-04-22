package comspringboot.challengewl.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;
    // Mock repository
    @MockBean
    private UserRepository userRepository;

    UserModel createUserModel() {
        // Mock of user
        UserModel mockUser = new UserModel();
        mockUser.setId(1);
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        return mockUser;
    }

    @Test
    public void testUserById() {
        // Mock of user
        UserModel mockUser = createUserModel();
        // SpyOn ang mock findById in userRepository
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        // Start function in userService
        Optional<UserModel> user = userService.findById(1);
        // Spy and assert result
        assertEquals("John", user.get().getFirstName());
        assertEquals("Doe", user.get().getLastName());
    }

    @Test
    public void testPostCreateUser() {
        // Mock of user
        UserModel mockUser = createUserModel();
        // SpyOn ang mock save in userRepository
        Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);
        // Start function in userService
        UserModel user = userService.createUser(mockUser);
        // Spy and assert result
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void testPutUpdateUser() {
        // Mock of user
        UserModel mockUser = createUserModel();
        // Start function in userService
        userService.updateUser(mockUser);
        // Spy and assert result
        Mockito.verify(userRepository, Mockito.times(1))
                .updateUser(
                        mockUser.getEmail(),
                        mockUser.getFirstName(),
                        mockUser.getLastName(),
                        mockUser.getId());
    }

    @Test
    public void testDeleteRemoveUser() {
        // Mock of user
        UserModel mockUser = createUserModel();
        // Start function in userService
        userService.removeUser(mockUser);
        // Spy and assert result
        Mockito.verify(userRepository, Mockito.times(1))
                .delete(Mockito.any());
    }

}

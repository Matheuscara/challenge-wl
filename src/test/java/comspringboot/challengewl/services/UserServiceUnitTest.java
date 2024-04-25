package comspringboot.challengewl.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import comspringboot.challengewl.models.UserModel;
import comspringboot.challengewl.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Random;

@SpringBootTest
@RequiredArgsConstructor
public class UserServiceUnitTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    UserModel createModel() {
        UserModel mockUser = new UserModel();
        mockUser.setId(new Random().nextLong());
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        return mockUser;
    }

    @Test
    public void testUserById() {
        UserModel mockUser = createModel();
        Mockito.when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        UserModel user = userService.findById(mockUser.getId());
        Mockito.verify(userRepository, Mockito.times(1))
                .findById(Mockito.any());
    }

    @Test
    public void testPostCreate() {
        UserModel mockUser = createModel();
        Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);
        UserModel user = userService.save(mockUser);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void testPutUpdateUser() {
        UserModel mockUser = createModel();
        Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);
        userService.update(Mockito.any(), mockUser.getId());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testDeleteRemoveUser() {
        UserModel mockUser = createModel();
        userService.remove(mockUser.getId());
        Mockito.verify(userRepository, Mockito.times(1)).delete(Mockito.any());
    }

}

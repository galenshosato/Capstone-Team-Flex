package learn.gig_economy.domain;

import learn.gig_economy.data.UserRepository;
import learn.gig_economy.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    void shouldAddValidUser() {
        User user = new User(0, "John Moore", "john@something.com", new BigDecimal("100.00"));
        when(repository.addUser(any(User.class))).thenReturn(user);

        Result<User> result = service.addUser(user);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("john@something.com", result.getPayload().getEmail());
    }

    @Test
    void shouldNotAddInvalidUser() {
        User user = new User(0, "", "", new BigDecimal("100.00"));

        Result<User> result = service.addUser(user);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Email cannot be blank."));
        assertNull(result.getPayload());
    }

    @Test
    void shouldFindUserById() {
        User user = new User(1, "John Moore", "john@something.com", new BigDecimal("100.00"));
        when(repository.findById(1)).thenReturn(user);

        User result = service.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }

    @Test
    void shouldNotFindUserById() {
        when(repository.findById(any(Integer.class))).thenReturn(null);
        User result = service.findById(999);

        assertNull(result);
    }

    @Test
    void shouldUpdateUser() {
        User user = new User(1, "John Updated", "john@something.com", new BigDecimal("200.00"));
        when(repository.updateUser(any(User.class))).thenReturn(true);
        when(repository.findById(1)).thenReturn(user);

        Result<User> result = service.updateUser(user);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("John Updated", result.getPayload().getName());
    }

    @Test
    void shouldNotUpdateUser() {
        User user = new User(-1, "John Updated", "john@something.com", new BigDecimal("200.00"));
        when(repository.updateUser(any(User.class))).thenReturn(false);

        Result<User> result = service.updateUser(user);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertTrue(result.getMessages().contains("userId must be set for `update` operation"));
    }

    @Test
    void shouldDeleteUser() {
        when(repository.deleteUser(1)).thenReturn(true);

        boolean result = service.deleteById(1);

        assertTrue(result);
    }

    @Test
    void shouldNotDeleteUser() {
        when(repository.deleteUser(any(Integer.class))).thenReturn(false);

        boolean result = service.deleteById(999);

        assertFalse(result);
    }

}
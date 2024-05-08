package learn.gig_economy.data;

import learn.gig_economy.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {

    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    // add tests
    @Test
    void shouldAddUser() {
        User user = new User(0, "Test User", "test@example.com", BigDecimal.valueOf(1000.00));
        User result = repository.addUser(user);
        assertNotNull(result);
        assertEquals("Test User", result.getName());
    }

    @Test
    void shouldFindUserByEmail() {
        User user = repository.findByEmail("galen@example.com");
        assertNotNull(user);
        assertEquals("Updated Galen", user.getName());
    }

    @Test
    void shouldFindAllUsers() {
        assertTrue(repository.findAll().size() >= 3);
    }

    @Test
    void shouldUpdateUser() {
        User user = repository.findByEmail("galen@example.com");
        user.setName("Updated Galen");
        user.setBank(BigDecimal.valueOf(1400.00).setScale(2, RoundingMode.HALF_UP));
        assertTrue(repository.updateUser(user));
        User updatedUser = repository.findByEmail("galen@example.com");
        assertEquals("Updated Galen", updatedUser.getName());
        assertEquals(BigDecimal.valueOf(1400.00).setScale(2, RoundingMode.HALF_UP), updatedUser.getBank());
    }

    @Test
    void shouldDeleteUser() {
        User user = repository.findByEmail("delete@example.com");
        assertTrue(repository.deleteUser(user.getUserId()));
        assertThrows(Exception.class, () -> repository.findByEmail("delete@example.com"));
    }

    @Test
    void shouldNotAddUserWithDuplicateEmail() {
        User duplicateUser = new User(0, "Galen", "galen@example.com", BigDecimal.valueOf(1300.00));
        assertThrows(DuplicateKeyException.class, () -> repository.addUser(duplicateUser));
    }

    @Test
    void shouldNotDeleteUserWithDependencies() {
        User user = repository.findByEmail("galen@example.com");
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.deleteUser(user.getUserId()));
        assertTrue(exception.getMessage().contains("foreign key constraint fails"));
    }
// if we decide not allowing negative balance (bank)
//    @Test
//    void shouldNotUpdateUserWithInvalidData() {
//        User user = repository.findByEmail("galen@example.com");
//        user.setBank(BigDecimal.valueOf(-100.00));
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> repository.updateUser(user));
//        assertTrue(exception.getMessage().contains("Bank balance cannot be negative"));
//    }

    @Test
    void shouldNotFindNonExistentUser() {
        assertThrows(EmptyResultDataAccessException.class, () -> repository.findByEmail("nonexistent@example.com"));
    }

    @Test
    void shouldFindUserById() {
        int userId = 1;
        User foundUser = repository.findById(userId);
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getUserId());
    }
}
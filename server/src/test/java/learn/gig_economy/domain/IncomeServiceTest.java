package learn.gig_economy.domain;

import learn.gig_economy.data.IncomeRepository;
import learn.gig_economy.data.UserRepository;
import learn.gig_economy.models.Income;
import learn.gig_economy.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class IncomeServiceTest {

    @Autowired
    IncomeService service;

    @MockBean
    IncomeRepository repository;

    @MockBean
    UserRepository userRepository;

    @Test
    void shouldAddValidIncome() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Income income = new Income(0, "Travel", new BigDecimal("100.00"), "description", date, 1);
        User mockUser = new User(1, "John", "john@example.com", new BigDecimal("500.00"));

        when(userRepository.findById(1)).thenReturn(mockUser);

        when(repository.addIncome(any(Income.class))).thenReturn(income);

        Result<Income> result = service.addIncome(income);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("description", result.getPayload().getDescription());
        assertEquals(new BigDecimal("600.00"), mockUser.getBank());
    }

    @Test
    void shouldUpdateValidIncome() {
        Income originalIncome = new Income(1, "Freelance Work", new BigDecimal("200.00"), "Initial description", LocalDate.now(), 1);
        User user = new User(1, "John Doe", "john@example.com", new BigDecimal("1000.00"));

        when(repository.findById(1)).thenReturn(originalIncome);
        when(userRepository.findById(1)).thenReturn(user);
        when(repository.updateIncome(any(Income.class))).thenReturn(true);
        when(userRepository.updateUser(any(User.class))).thenReturn(true);

        Result<Income> result = service.updateIncome(originalIncome);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("Initial description", result.getPayload().getDescription());
    }

    @Test
    void shouldNotUpdateNonexistentIncome() {
        Income income = new Income(999, "Nonexistent", new BigDecimal("100.00"), "Does not exist", LocalDate.now(), 1);
        when(repository.updateIncome(any(Income.class))).thenReturn(false);

        Result<Income> result = service.updateIncome(income);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("incomeId: 999, not found"));
    }

    @Test
    void shouldFindIncomeById() {
        Income income = new Income(1, "Cold calling", new BigDecimal("3000.00"), "Monthly salary", LocalDate.now(), 1);
        when(repository.findById(1)).thenReturn(income);

        Income result = service.findById(1);

        assertEquals(1, result.getIncomeId());
        assertNotNull(result);
        assertEquals("Monthly salary", result.getDescription());
    }

    @Test
    void shouldNotFindIncomeById() {
        when(repository.findById(anyInt())).thenReturn(null);

        Income result = service.findById(999);

        assertNull(result);
    }

    @Test
    void shouldDeleteIncome() {
        Income mockIncome = new Income(1, "Freelance Job", new BigDecimal("200.00"), "Freelance income", LocalDate.now(), 1);
        User mockUser = new User(1, "John", "john@example.com", new BigDecimal("1000.00"));

        when(repository.findById(1)).thenReturn(mockIncome);
        when(userRepository.findById(1)).thenReturn(mockUser);
        when(userRepository.updateUser(any(User.class))).thenReturn(true);
        when(repository.deleteIncome(1)).thenReturn(true);

        boolean result = service.deleteById(1);

        assertTrue(result);
        verify(userRepository).updateUser(mockUser);
        verify(repository).deleteIncome(1);
    }

    @Test
    void shouldNotDeleteNonexistentIncome() {
        when(repository.findById(999)).thenReturn(null);
        when(repository.deleteIncome(999)).thenReturn(false);

        boolean result = service.deleteById(999);

        assertFalse(result);
        verify(repository).findById(999);
        verify(repository, never()).deleteIncome(999);
    }

    @Test
    void shouldFindByYear() {
        Income income = new Income();
        when(repository.findByYear(2024, 1)).thenReturn(Arrays.asList(income));

        List<Income> incomes = service.findIncomesByYear(2024, 1);

        assertNotNull(incomes);
        assertFalse(incomes.isEmpty());
        assertEquals(1, incomes.size());
        verify(repository).findByYear(2024, 1);
    }

    @Test
    void shouldFindByMonthAndYear() {
        Income income = new Income();
        when(repository.findByMonthAndYear(4, 2024, 1)).thenReturn(Arrays.asList(income));

        List<Income> incomes = service.findIncomesByMonthAndYear(4, 2024, 1);

        assertNotNull(incomes);
        assertFalse(incomes.isEmpty());
        assertEquals(1, incomes.size());
        verify(repository).findByMonthAndYear(4, 2024, 1);
    }

    Income makeIncome() {
        Income income = new Income();
        income.setIncomeId(1);
        income.setName("tax return");
        income.setAmount(new BigDecimal("130.00"));
        income.setDescription("This is a description");
        income.setDate(LocalDate.of(2024, 5, 3));
        income.setUserId(0);
        return income;
    }

}
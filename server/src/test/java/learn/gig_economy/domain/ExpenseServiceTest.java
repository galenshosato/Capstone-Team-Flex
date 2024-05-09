package learn.gig_economy.domain;

import learn.gig_economy.data.ExpenseRepository;
import learn.gig_economy.data.UserRepository;
import learn.gig_economy.models.Expense;
import learn.gig_economy.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExpenseServiceTest {

    @Autowired
    ExpenseService service;

    @MockBean
    ExpenseRepository repository;

    @MockBean
    UserRepository userRepository;


    @Test
    void shouldAddValidExpense() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Expense expense = new Expense(0, "Internet Bill", new BigDecimal("12"), "description", date, 1, 1);
        User originalUser = new User(1, "John", "john@example.com", new BigDecimal("1000.00"));

        BigDecimal newBankBalance = originalUser.getBank().subtract(expense.getAmount());
        User updatedUser = new User(1, "John", "john@example.com", newBankBalance);

        when(repository.addExpense(any(Expense.class))).thenReturn(expense);

        when(userRepository.findById(1)).thenReturn(originalUser);

        Result<Expense> result = service.addExpense(expense);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("Internet Bill", result.getPayload().getName());

        assertEquals(newBankBalance, updatedUser.getBank());
    }

    @Test
    void shouldNotAddExpenseWithInvalidData() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Expense expense = new Expense(0, "Joe", new BigDecimal(-12), "description", date, 1, 1);
        Result<Expense> result = service.addExpense(expense);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Amount balance cannot be negative."));
    }

    @Test
    void shouldFindExpenseById() {
        when(repository.findById(1)).thenReturn(makeExpense());
        Expense foundExpense = service.findById(1);

        assertNotNull(foundExpense);
        assertEquals("Internet Bill", foundExpense.getName());
    }

    @Test
    void shouldNotFindExpenseWhenIdNotExists() {
        when(repository.findById(anyInt())).thenReturn(null);
        Expense foundExpense = service.findById(999);

        assertNull(foundExpense);
    }

    @Test
    void shouldFindAllExpenses() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Expense expense = new Expense(0, "Internet Bill", new BigDecimal(12), "description", date, 1, 1);
        when(repository.findAll()).thenReturn(Collections.singletonList(expense));
        List<Expense> expenses = service.findAll();

        assertFalse(expenses.isEmpty());
        assertEquals(1, expenses.size());
        assertEquals(expense, expenses.get(0));
    }

    @Test
    void shouldUpdateExpense() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Expense originalExpense = new Expense(1, "Internet Bill", new BigDecimal("50.00"), "Initial description", date, 1, 1);
        Expense updatedExpense = new Expense(1, "Internet Bill", new BigDecimal("60.00"), "Updated Description", date, 1, 1);
        User user = new User(1, "John", "john@example.com", new BigDecimal("1000.00"));

        when(repository.findById(1)).thenReturn(originalExpense);
        when(userRepository.findById(1)).thenReturn(user);

        when(repository.updateExpense(any(Expense.class))).thenAnswer(invocation -> {
            Expense passedExpense = invocation.getArgument(0);

            assertEquals("Updated Description", passedExpense.getDescription());
            assertEquals(new BigDecimal("60.00"), passedExpense.getAmount());
            return true;
        });

        Result<Expense> result = service.updateExpense(updatedExpense);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("Updated Description", result.getPayload().getDescription());

    }

    @Test
    void shouldNotUpdateNonexistentExpense() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Expense expense = new Expense(999, "Internet Bill", new BigDecimal("12.00"), "description", date, 1, 1);
        when(repository.findById(999)).thenReturn(null);
        when(repository.updateExpense(any(Expense.class))).thenReturn(false);

        Result<Expense> result = service.updateExpense(expense);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Expense not found"));
    }

    @Test
    void shouldDeleteExpenseById() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Expense expense = new Expense(1, "Internet Bill", new BigDecimal("12"), "description", date, 1, 1);
        User user = new User(1, "John Doe", "john@example.com", new BigDecimal("1000.00"));

        when(repository.findById(1)).thenReturn(expense);
        when(userRepository.findById(1)).thenReturn(user);
        when(repository.deleteExpense(1)).thenReturn(true);

        boolean result = service.deleteById(1);

        assertTrue(result);

        assertEquals(new BigDecimal("1012.00"), user.getBank());
    }

    @Test
    void shouldNotDeleteExpenseWhenIdNotExists() {
        when(repository.deleteExpense(anyInt())).thenReturn(false);
        boolean result = service.deleteById(999);

        assertFalse(result);
    }

    @Test
    void shouldFindByYear() {
        Expense expense = new Expense();
        when(repository.findByYear(2024, 1)).thenReturn(Arrays.asList(expense));

        List<Expense> expenses = service.findExpensesByYear(2024, 1);

        assertNotNull(expenses);
        assertFalse(expenses.isEmpty());
        assertEquals(1, expenses.size());
        verify(repository).findByYear(2024, 1);
    }

    @Test
    void shouldFindByMonthAndYear() {
        Expense expense = new Expense();
        when(repository.findByMonthAndYear(4, 2024, 1)).thenReturn(Arrays.asList(expense));

        List<Expense> expenses = service.findExpensesByMonthAndYear(4, 2024, 1);

        assertNotNull(expenses);
        assertFalse(expenses.isEmpty());
        assertEquals(1, expenses.size());
        verify(repository).findByMonthAndYear(4, 2024, 1);
    }

    Expense makeExpense(){
            Expense expense;
        expense = new Expense();
        expense.setExpenseId(1);
        expense.setName("Internet Bill");
        expense.setDescription("Monthly payment");
        expense.setAmount(new BigDecimal("60.00"));
        expense.setDate(LocalDate.of(2024, 6, 3));
        expense.setUserId(1);
        expense.setGoalId(1);
        return expense;
    }
}
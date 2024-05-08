package learn.gig_economy.domain;

import learn.gig_economy.data.ExpenseRepository;
import learn.gig_economy.models.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExpenseServiceTest {

    @Autowired
    ExpenseService service;

    @MockBean
    ExpenseRepository repository;

    @Test
    void shouldAddValidExpense() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Expense expense = new Expense(0, "Internet Bill", new BigDecimal(12), "description", date, 1, 1);
        when(repository.addExpense(any(Expense.class))).thenReturn(expense);
        Result<Expense> result = service.addExpense(expense);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("Internet Bill", result.getPayload().getName());
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
        Expense foundExpense = service.findById(999); // Non-existent ID

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
        Expense expense = new Expense(1, "Internet Bill", new BigDecimal(12), "description", date, 1, 1);
        when(repository.updateExpense(any(Expense.class))).thenReturn(true);
        expense.setDescription("Updated Description");
        Result<Expense> result = service.updateExpense(expense);

        assertTrue(result.isSuccess());
        assertEquals("Updated Description", result.getPayload().getDescription());
    }

    @Test
    void shouldNotUpdateNonexistentExpense() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Expense expense = new Expense(1, "Internet Bill", new BigDecimal(12), "description", date, 1, 1);
        when(repository.updateExpense(any(Expense.class))).thenReturn(false);
        Result<Expense> result = service.updateExpense(expense);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Expense not found or update failed"));
    }

    @Test
    void shouldDeleteExpenseById() {
        when(repository.deleteExpense(1)).thenReturn(true);
        boolean result = service.deleteById(1);

        assertTrue(result);
    }

    @Test
    void shouldNotDeleteExpenseWhenIdNotExists() {
        when(repository.deleteExpense(anyInt())).thenReturn(false);
        boolean result = service.deleteById(999);

        assertFalse(result);
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
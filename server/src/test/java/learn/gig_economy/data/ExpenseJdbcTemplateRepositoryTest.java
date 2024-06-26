package learn.gig_economy.data;

import learn.gig_economy.models.Expense;
import learn.gig_economy.models.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExpenseJdbcTemplateRepositoryTest {

    @Autowired
    ExpenseJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldAddValidExpense() {
        Expense expense = new Expense();
        expense.setName("Office Supplies");
        expense.setAmount(new BigDecimal("120.00"));
        expense.setDescription("Stationery");
        expense.setDate(LocalDate.now());
        expense.setUserId(1);
        expense.setGoalId(1);
        Expense addedExpense = repository.addExpense(expense);
        assertNotNull(addedExpense);
        assertTrue(addedExpense.getExpenseId() > 0);
    }

    @Test
    void shouldFindAllExpenses() {
        List<Expense> expenses = repository.findAll();
        assertFalse(expenses.isEmpty());
    }

    @Test
    void shouldUpdateExpense() {
        List<Expense> expenses = repository.findAll();
        assertFalse(expenses.isEmpty());
        Expense expense = expenses.get(0);
        expense.setAmount(new BigDecimal("150.00"));
        assertTrue(repository.updateExpense(expense));
    }

    @Test
    void shouldDeleteExpense() {
        List<Expense> expenses = repository.findAll();
        assertFalse(expenses.isEmpty());
        boolean deleted = repository.deleteExpense(expenses.get(0).getExpenseId());
        assertTrue(deleted);
    }

    @Test
    void shouldNotDeleteNonexistentExpense() {
        boolean result = repository.deleteExpense(9999);
        assertFalse(result);
    }
    @Test
    void shouldFindExpenseById() {
        int expenseId = 1;
        Expense foundExpense = repository.findById(expenseId);
        assertNotNull(foundExpense);
        assertEquals(expenseId, foundExpense.getExpenseId());
    }


    @Test
    void shouldFindExpensesByYear() {
        List<Expense> results = repository.findByYear(2024, 1);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());

    }

    @Test
    void shouldNotFindExpensesByYear() {
        List<Expense> results = repository.findByYear(2029, 1);
        assertTrue(results.isEmpty());
        assertEquals(0, results.size());
    }

    @Test
    void shouldFindExpensesByMonthAndYear() {

        List<Expense> results = repository.findByMonthAndYear(4, 2023, 3);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void shouldNotFindExpensesByMonthAndYear() {

        List<Expense> results = repository.findByMonthAndYear(4, 2027, 1);
        assertTrue(results.isEmpty());
        assertEquals(0, results.size());
    }
}
package learn.gig_economy.data;

import learn.gig_economy.models.Expense;
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
        expense.setUserId(1);  // Assuming user_id 1 is valid and exists in the database
        expense.setGoalId(1);  // Assuming goal_id 1 is valid and exists in the database
        Expense addedExpense = repository.addExpense(expense);
        assertNotNull(addedExpense);
        assertTrue(addedExpense.getExpenseId() > 0);
    }

    @Test
    void shouldFindAllExpenses() {
        List<Expense> expenses = repository.findAll();
        assertFalse(expenses.isEmpty());  // Check that the result is not empty
    }

    @Test
    void shouldUpdateExpense() {
        List<Expense> expenses = repository.findAll();
        assertFalse(expenses.isEmpty());
        Expense expense = expenses.get(0);
        expense.setAmount(new BigDecimal("150.00"));
        assertTrue(repository.updateExpense(expense));  // Check that the update returns true
    }

    @Test
    void shouldDeleteExpense() {
        List<Expense> expenses = repository.findAll();
        assertFalse(expenses.isEmpty());
        boolean deleted = repository.deleteExpense(expenses.get(0).getExpenseId());
        assertTrue(deleted);  // Ensure the expense is deleted
    }

    @Test
    void shouldNotDeleteNonexistentExpense() {
        boolean result = repository.deleteExpense(9999);  // Assuming 9999 is a non-existent expense ID
        assertFalse(result);  // Deletion should fail
    }


}
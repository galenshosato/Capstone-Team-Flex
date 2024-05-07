package learn.gig_economy.data;

import learn.gig_economy.models.Goal;
import learn.gig_economy.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GoalJdbcTemplateRepositoryTest {

    @Autowired
    GoalJdbcTemplateRepository repository;

    @Autowired
    ExpenseJdbcTemplateRepository expenseJdbcTemplateRepository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldAddValidGoal() {
        Goal goal = new Goal();
        goal.setDescription("Save for vacation");
        goal.setPercentage(new BigDecimal("15.00"));
        goal.setUserId(1);
        Goal addedGoal = repository.addGoal(goal);
        assertNotNull(addedGoal);
        assertTrue(addedGoal.getGoalId() > 0);
    }

    @Test
    void shouldFindAllGoals() {
        List<Goal> goals = repository.findAll();
        assertFalse(goals.isEmpty());
    }

    @Test
    void shouldUpdateGoal() {
        List<Goal> goals = repository.findAll();
        assertFalse(goals.isEmpty());
        Goal goal = goals.get(0);
        goal.setDescription("Updated Save for holiday");
        goal.setPercentage(new BigDecimal("20.00"));
        assertTrue(repository.updateGoal(goal));
    }

    @Test
    void shouldDeleteGoal() {
        List<Goal> goals = repository.findAll();
        assertFalse(goals.isEmpty());
        //deleting expense first
        expenseJdbcTemplateRepository.deleteExpense(2);
        boolean deleted = repository.deleteGoal(goals.get(1).getGoalId());
        assertTrue(deleted);
    }

    @Test
    void shouldNotDeleteNonexistentGoal() {
        boolean result = repository.deleteGoal(999);
        assertFalse(result);
    }

    @Test
    void shouldFindGoalById() {
        int goalId = 1;
        Goal foundGoal = repository.findById(goalId);
        assertNotNull(foundGoal);
        assertEquals(goalId, foundGoal.getGoalId());
    }
}
package learn.gig_economy.domain;

import learn.gig_economy.data.GoalRepository;
import learn.gig_economy.models.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GoalServiceTest {

    @Autowired
    GoalService service;

    @MockBean
    GoalRepository repository;

    @Test
    void shouldAddValidGoal() {

       Goal goal = new Goal(0, "description", new BigDecimal(10), 1);
        when(repository.addGoal(any(Goal.class))).thenReturn(goal);
        Result<Goal> result = service.addGoal(goal);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("description", result.getPayload().getDescription());
    }

    @Test
    void shouldNotAddInvalidGoal() {
        Goal goal = new Goal(0, "description", new BigDecimal(10), 1);
        goal.setPercentage(new BigDecimal("0"));
        Result<Goal> result = service.addGoal(goal);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Percentage cannot be under 1% or over 99%."));
    }

    @Test
    void shouldFindAllGoals() {
      Goal goal = new Goal(0, "description", new BigDecimal(10), 1);
        when(repository.findAll()).thenReturn(Arrays.asList(goal));
        List<Goal> goals = service.findAll();

        assertFalse(goals.isEmpty());
        assertEquals(1, goals.size());
        assertEquals(goal, goals.get(0));
    }

    @Test
    void shouldFindGoalById() {
        when(repository.findById(1)).thenReturn(makeGoal());
        Goal foundGoal = service.findById(1);

        assertNotNull(foundGoal);
        assertEquals("description", foundGoal.getDescription());
    }

    @Test
    void shouldNotFindGoalByIdWhenNotExists() {
        when(repository.findById(anyInt())).thenReturn(null);
        Goal foundGoal = service.findById(999);

        assertNull(foundGoal);
    }

    @Test
    void shouldUpdateGoal() {
        when(repository.updateGoal(any(Goal.class))).thenReturn(true);
        Result<Goal> result = service.updateGoal(makeGoal());

        assertTrue(result.isSuccess());
        assertEquals("description", result.getPayload().getDescription());
    }

    @Test
    void shouldNotUpdateGoalWhenNotFound() {
        Goal goal = new Goal(999, "description", new BigDecimal(10), 1);
        when(repository.updateGoal(any(Goal.class))).thenReturn(false);
        Result<Goal> result = service.updateGoal(goal);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Goal not found or update failed"));
    }

    @Test
    void shouldDeleteGoal() {
        when(repository.deleteGoal(1)).thenReturn(true);
        boolean deleted = service.deleteById(1);

        assertTrue(deleted);
    }

    @Test
    void shouldNotDeleteGoalWhenNotFound() {
        when(repository.deleteGoal(999)).thenReturn(false);
        boolean deleted = service.deleteById(999);

        assertFalse(deleted);
    }

    Goal makeGoal(){
      Goal goal;
        goal = new Goal(1, "description", new BigDecimal(10), 1);
        return goal;
    }

}
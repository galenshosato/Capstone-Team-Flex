package learn.gig_economy.domain;

import learn.gig_economy.data.GoalRepository;
import learn.gig_economy.models.Goal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GoalService {

    private final GoalRepository repository;

    public GoalService(GoalRepository repository) {
        this.repository = repository;
    }

    public Result<Goal> addGoal(Goal goal) {
        Result<Goal> result = validate(goal);
        if (!result.isSuccess()) {
            return result;
        }
        if (goal.getGoalId() != 0) {
            result.addMessage("goalId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        goal = repository.addGoal(goal);

        result.setPayload(goal);
        return result;
    }

    public Goal findById(int goalId) { return repository.findById(goalId);}

    public List<Goal> findAll() {
        return repository.findAll();
    }

    public List<Goal> findAllByUserId(int userId) {
        return repository.findAllByUserId(userId);
    }

    public Result<Goal> updateGoal(Goal goal) {
        Result<Goal> validation = validate(goal);
        if (!validation.isSuccess()) {
            return validation;
        }
        if (goal.getGoalId() == 0) {
            validation.addMessage("goalId must be set for `update` operation", ResultType.INVALID);
            return validation;
        }
        boolean updated = repository.updateGoal(goal);
        if (!updated) {
            validation.addMessage("Goal not found or update failed", ResultType.NOT_FOUND);
            return validation;
        }
        validation.setPayload(goal);
        return validation;
    }

    public boolean deleteById(int goalId) {
        return repository.deleteGoal(goalId);
    }

    private Result<Goal> validate(Goal goal) {
        Result<Goal> result = new Result<>();

        if (goal == null) {
            result.addMessage("Goal cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(goal.getDescription())) {
            result.addMessage("Description cannot be blank.", ResultType.INVALID);
        }

        if (goal.getPercentage().compareTo(new BigDecimal("1")) < 0 || goal.getPercentage().compareTo(new BigDecimal("99")) > 0) {
            result.addMessage("Percentage cannot be under 1% or over 99%.", ResultType.INVALID);
        }

        if (!result.getMessages().isEmpty()) {
            result.setType(ResultType.INVALID);
        }

        return result;
    }
}

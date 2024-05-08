package learn.gig_economy.data;

import learn.gig_economy.models.Goal;

import java.util.List;

public interface GoalRepository {

    Goal addGoal(Goal goal);

    Goal findById(int goalId);

    List<Goal> findAll();

    List<Goal> findAllByUserId(int userId);

    boolean updateGoal(Goal goal);

    boolean deleteGoal(int goalId);
}

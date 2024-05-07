package learn.gig_economy.data;

import learn.gig_economy.models.Goal;

import java.util.List;

public interface GoalRepository {

    Goal addGoal(Goal goal);

    List<Goal> findAll();

    boolean updateGoal(Goal goal);

    boolean deleteGoal(int goalId);
}

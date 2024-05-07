package learn.gig_economy.data.mappers;

import learn.gig_economy.models.Goal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoalMapper implements RowMapper<Goal> {

    @Override
    public Goal mapRow(ResultSet resultSet, int i) throws SQLException {
        Goal goal = new Goal();
        goal.setGoalId(resultSet.getInt("goal_id"));
        goal.setDescription(resultSet.getString("description"));
        goal.setPercentage(resultSet.getBigDecimal("percentage"));
        goal.setUserId(resultSet.getInt("user_id"));
        return goal;
    }
}

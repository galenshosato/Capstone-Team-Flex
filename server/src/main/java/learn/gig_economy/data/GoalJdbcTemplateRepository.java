package learn.gig_economy.data;

import learn.gig_economy.data.mappers.GoalMapper;
import learn.gig_economy.models.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class GoalJdbcTemplateRepository implements GoalRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GoalJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Goal addGoal(Goal goal) {
        final String sql = "INSERT INTO goal (description, percentage, user_id) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"goal_id"});
            ps.setString(1, goal.getDescription());
            ps.setBigDecimal(2, goal.getPercentage());
            ps.setInt(3, goal.getUserId());
            return ps;
        }, keyHolder);
        goal.setGoalId(keyHolder.getKey().intValue());
        return goal;
    }
    @Override
    public Goal findById(int goalId) {
        final String sql = "SELECT goal_id, description, percentage, user_id FROM goal WHERE goal_id = ?;";
        return jdbcTemplate.queryForObject(sql, new GoalMapper(), goalId);
    }
    @Override
    public List<Goal> findAll() {
        final String sql = "SELECT goal_id, description, percentage, user_id FROM goal;";
        return jdbcTemplate.query(sql, new GoalMapper());
    }

    @Override
    public boolean updateGoal(Goal goal) {
        final String sql = "UPDATE goal SET description = ?, percentage = ?, user_id = ? WHERE goal_id = ?;";
        return jdbcTemplate.update(sql,
                goal.getDescription(),
                goal.getPercentage(),
                goal.getUserId(),
                goal.getGoalId()) > 0;
    }

    @Override
    public boolean deleteGoal(int goalId) {
        final String sql = "DELETE FROM goal WHERE goal_id = ?;";
        return jdbcTemplate.update(sql, goalId) > 0;
    }

}

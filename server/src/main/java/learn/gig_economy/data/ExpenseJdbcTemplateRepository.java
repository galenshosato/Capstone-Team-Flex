package learn.gig_economy.data;

import learn.gig_economy.data.mappers.ExpenseMapper;
import learn.gig_economy.models.Expense;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ExpenseJdbcTemplateRepository implements ExpenseRepository{

    private final JdbcTemplate jdbcTemplate;

    public ExpenseJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Expense addExpense(Expense expense) {
        final String sql = "INSERT INTO expense (name, amount, description, date, user_id, goal_id) VALUES (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, expense.getName());
            ps.setBigDecimal(2, expense.getAmount());
            ps.setString(3, expense.getDescription());
            ps.setDate(4, java.sql.Date.valueOf(expense.getDate()));
            ps.setInt(5, expense.getUserId());
            ps.setInt(6, expense.getGoalId());
            return ps;
        }, keyHolder);
        expense.setExpenseId(keyHolder.getKey().intValue());
        return expense;
    }
    @Override
    public Expense findById(int expenseId) {
        final String sql = "SELECT expense_id, name, amount, description, date, user_id, goal_id FROM expense WHERE expense_id = ?;";
        return jdbcTemplate.queryForObject(sql, new ExpenseMapper(), expenseId);
    }

    @Override
    public List<Expense> findAll() {
        final String sql = "SELECT expense_id, name, amount, description, date, user_id, goal_id FROM expense;";
        return jdbcTemplate.query(sql, new ExpenseMapper());
    }

    @Override
    public List<Expense> findAllByUserId(int userId) {
        final String sql = "SELECT expense_id, name, amount, description, date, user_id, goal_id FROM expense WHERE user_id = ?;";
        return jdbcTemplate.query(sql, new ExpenseMapper(), userId);
    }

    @Override
    public List<Expense> findByYear(int year, int userId){
        final String sql = "SELECT expense_id, name, amount, description, date, user_id, goal_id FROM expense WHERE YEAR(date) = ? AND user_id = ?;";
        return jdbcTemplate.query(sql, new ExpenseMapper(), year, userId);
    }

    @Override
    public List<Expense> findByMonthAndYear(int month, int year, int userId){
        final String sql = "SELECT expense_id, name, amount, description, date, user_id, goal_id FROM expense WHERE YEAR(date) = ? AND MONTH(date) = ? AND user_id = ?;";
        return jdbcTemplate.query(sql, new ExpenseMapper(), year, month, userId);
    }

    @Override
    public boolean updateExpense(Expense expense) {
        final String sql = "UPDATE expense SET name = ?, amount = ?, description = ?, date = ?, user_id = ?, goal_id = ? WHERE expense_id = ?;";
        return jdbcTemplate.update(sql,
                expense.getName(),
                expense.getAmount(),
                expense.getDescription(),
                java.sql.Date.valueOf(expense.getDate()),
                expense.getUserId(),
                expense.getGoalId(),
                expense.getExpenseId()) > 0;
    }

    @Override
    public boolean deleteExpense(int expenseId) {
        final String sql = "DELETE FROM expense WHERE expense_id = ?;";
        return jdbcTemplate.update(sql, expenseId) > 0;
    }

}

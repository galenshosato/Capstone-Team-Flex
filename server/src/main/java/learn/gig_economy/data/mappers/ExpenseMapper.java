package learn.gig_economy.data.mappers;

import learn.gig_economy.models.Expense;
import learn.gig_economy.models.Goal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseMapper implements RowMapper<Expense> {

    @Override
    public Expense mapRow(ResultSet resultSet, int i) throws SQLException {
        Expense expense = new Expense();
        expense.setExpenseId(resultSet.getInt("expense_id"));
        expense.setName(resultSet.getString("name"));
        expense.setAmount(resultSet.getBigDecimal("amount"));
        expense.setDescription(resultSet.getString("description"));
        expense.setDate(resultSet.getDate("date").toLocalDate());
        expense.setUserId(resultSet.getInt("user_id"));
        expense.setGoalId(resultSet.getInt("goal_id"));
        return expense;
    }
}

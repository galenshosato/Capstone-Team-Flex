package learn.gig_economy.data.mappers;

import learn.gig_economy.models.Income;
import learn.gig_economy.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeMapper implements RowMapper<Income> {

    @Override
    public Income mapRow(ResultSet resultSet, int i) throws SQLException {
        Income income = new Income();
        income.setIncomeId(resultSet.getInt("income_id"));
        income.setName(resultSet.getString("name"));
        income.setAmount(resultSet.getBigDecimal("amount"));
        income.setDescription(resultSet.getString("description"));
        income.setDate(resultSet.getDate("date").toLocalDate());
        income.setUserId(resultSet.getInt("user_id"));
        return income;
    }
}

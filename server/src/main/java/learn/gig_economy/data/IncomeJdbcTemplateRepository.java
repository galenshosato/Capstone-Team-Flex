package learn.gig_economy.data;

import learn.gig_economy.data.mappers.IncomeMapper;
import learn.gig_economy.models.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class IncomeJdbcTemplateRepository implements IncomeRepository {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public IncomeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Income addIncome(Income income) {
        final String sql = "INSERT INTO income (name, amount, description, date, user_id) VALUES (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, income.getName());
                ps.setBigDecimal(2, income.getAmount());
                ps.setString(3, income.getDescription());
                ps.setDate(4, java.sql.Date.valueOf(income.getDate()));
                ps.setInt(5, income.getUserId());
                return ps;
            }, keyHolder);

            if (rowsAffected <= 0) {
                return null;
            }

            income.setIncomeId(keyHolder.getKey().intValue());
            return income;
    }

    @Override
    public Income findById(int incomeId) {
        final String sql = "SELECT income_id, name, amount, description, date, user_id FROM income WHERE income_id = ?;";
        return jdbcTemplate.queryForObject(sql, new IncomeMapper(), incomeId);
    }

    @Override
    public List<Income> findAll() {
        final String sql = "SELECT income_id, name, amount, description, date, user_id FROM income;";
        return jdbcTemplate.query(sql, new IncomeMapper());
    }
    @Override
    public List<Income> findAllByUserId(int userId) {
        final String sql = "SELECT income_id, name, amount, description, date, user_id FROM income WHERE user_id = ?;";
        return jdbcTemplate.query(sql, new IncomeMapper(), userId);
    }
    @Override
    public List<Income> findByYear(int year){
        final String sql = "SELECT income_id, name, amount, description, date, user_id FROM income WHERE YEAR(date) = ?;";
        return jdbcTemplate.query(sql, new IncomeMapper(), year);
    }

    @Override
    public List<Income> findByMonthAndYear(int month, int year){
        final String sql = "SELECT income_id, name, amount, description, date, user_id FROM income WHERE YEAR(date) = ? AND MONTH(date) = ?;";
        return jdbcTemplate.query(sql, new IncomeMapper(), year, month);
    }

    @Override
    public boolean updateIncome(Income income) {
        final String sql = "UPDATE income SET name = ?, amount = ?, description = ?, date = ?, user_id = ? WHERE income_id = ?;";
        return jdbcTemplate.update(sql,
                income.getName(),
                income.getAmount(),
                income.getDescription(),
                java.sql.Date.valueOf(income.getDate()),
                income.getUserId(),
                income.getIncomeId()) > 0;
    }
    @Override
    public boolean deleteIncome(int incomeId) {
        final String sql = "DELETE FROM income WHERE income_id = ?;";
        return jdbcTemplate.update(sql, incomeId) > 0;
    }



}

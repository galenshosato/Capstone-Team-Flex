package learn.gig_economy.data;

import learn.gig_economy.data.mappers.UserMapper;
import learn.gig_economy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public User addUser(User user) {
        final String sql = "INSERT INTO user (name, email, bank) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getBank());
        return user;
    }
    @Override
    public User findByEmail(String email) {
        final String sql = "SELECT user_id, name, email, bank FROM user WHERE email = ?;";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), email);
    }

    @Override
    public User findById(int userId) {
        final String sql = "SELECT user_id, name, email, bank FROM user WHERE user_id = ?;";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), userId);
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT user_id, name, email, bank FROM user;";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    public boolean updateUser(User user) {
        final String sql = "UPDATE user SET name = ?, email = ?, bank = ? WHERE user_id = ?;";
        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getBank(), user.getUserId()) > 0;
    }

    //If we decide not to allow negative balance
//    @Override
//    public boolean updateUser(User user) {
//        if (user.getBank().compareTo(BigDecimal.ZERO) < 0) {
//            throw new IllegalArgumentException("Bank balance cannot be negative.");
//        }
//        final String sql = "UPDATE user SET name = ?, email = ?, bank = ? WHERE user_id = ?;";
//        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getBank(), user.getUserId()) > 0;
//}
    @Override
    public boolean deleteUser(int userId) {
        final String sql = "DELETE FROM user WHERE user_id = ?;";
        return jdbcTemplate.update(sql, userId) > 0;
    }

}

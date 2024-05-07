package learn.gig_economy.data;

import learn.gig_economy.models.User;

import java.util.List;

public interface UserRepository {
    User addUser(User user);
    User findByEmail(String email);

    User findById(int userId);

    List<User> findAll();
    boolean updateUser(User user);
    boolean deleteUser(int userId);


}

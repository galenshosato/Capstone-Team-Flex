package learn.gig_economy.domain;

import learn.gig_economy.data.UserRepository;
import learn.gig_economy.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Result<User> addUser(User user) {
        Result<User> result = new Result<>();
        if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
            result.addMessage("User email cannot be null or empty", ResultType.INVALID);
            return result;
        }
        result.setPayload(repository.addUser(user));
        return result;
    }

    public Result<User> updateUser(User user) {
        Result<User> result = new Result<>();
        if (user == null || user.getUserId() <= 0) {
            result.addMessage("Invalid user ID", ResultType.INVALID);
            return result;
        }
        if (!repository.updateUser(user)) {
            result.addMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }
        result.setPayload(user);
        return result;
    }


    public Result<Void> deleteUser(int userId) {
        Result<Void> result = new Result<>();
        if (!repository.deleteUser(userId)) {
            result.addMessage("User not found or unable to delete", ResultType.NOT_FOUND);
            return result;
        }
        return result;
    }


}

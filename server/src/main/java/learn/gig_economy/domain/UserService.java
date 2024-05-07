package learn.gig_economy.domain;

import learn.gig_economy.data.UserRepository;
import learn.gig_economy.models.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Result<User> addUser(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }
        if (user.getUserId() != 0) {
            result.addMessage("userId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        user = repository.addUser(user);
        result.setPayload(user);
        return result;
    }

    public Result<User> findById(int userId) {
        Result<User> result = new Result<>();
        User user = repository.findById(userId);
        if (user == null) {
            result.addMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }
        result.setPayload(user);
        return result;
    }

    public Result<User> updateUser(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addMessage("userId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.updateUser(user)) {
            String msg = String.format("userId: %s, not found", user.getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
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

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();

        if (user == null) {
            result.addMessage("user cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(user.getEmail())) {
            result.addMessage("Email cannot be blank.", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getName())) {
            result.addMessage("Name cannot be blank.", ResultType.INVALID);
        }
        // if we decide balance cannot be negative (bank)
//        if (user.getBank() != null && user.getBank().compareTo(BigDecimal.ZERO) < 0) {
//            result.addMessage("Bank balance cannot be negative.", ResultType.INVALID);
//        }

        if (!result.getMessages().isEmpty()) {
            result.setType(ResultType.INVALID);
        }

        return result;
    }
}

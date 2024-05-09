package learn.gig_economy.domain;

import learn.gig_economy.data.UserRepository;
import learn.gig_economy.models.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    public User findByEmail(String email){
        return repository.findByEmail(email);
    }

    public User findById(int userId) { return repository.findById(userId);}
    public List<User> findAll() {
        return repository.findAll();
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

    public boolean deleteById(int userId) { return repository.deleteUser(userId);}

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

        if (!result.getMessages().isEmpty()) {
            result.setType(ResultType.INVALID);
        }

        return result;
    }
}

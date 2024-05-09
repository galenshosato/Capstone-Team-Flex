package learn.gig_economy.domain;

import learn.gig_economy.data.IncomeRepository;
import learn.gig_economy.data.UserRepository;
import learn.gig_economy.models.Income;
import learn.gig_economy.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository repository;
    private final UserRepository userRepository;

    public IncomeService(IncomeRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }
    @Transactional
    public Result<Income> addIncome(Income income) {
        Result<Income> result = validate(income);
        if (!result.isSuccess()) {
            return result;
        }
        if (income.getIncomeId() != 0) {
            result.addMessage("incomeId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        User user = userRepository.findById(income.getUserId());
        if (user == null) {
            result.addMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }

        BigDecimal newBankAmount = user.getBank().add(income.getAmount());
        user.setBank(newBankAmount);
        userRepository.updateUser(user);

        income = repository.addIncome(income);
        if (income == null) {
            result.addMessage("Failed to add income.", ResultType.INVALID);
            return result;
        }

        result.setPayload(income);
        return result;
    }

    public Income findById(int incomeId) { return repository.findById(incomeId);}

    public List<Income> findAll() {
        return repository.findAll();
    }

    public List<Income> findAllByUserId(int userId) {
        return repository.findAllByUserId(userId);
    }

    public List<Income> findIncomesByYear(int year, int userId) {
        return repository.findByYear(year, userId);
    }

    public List<Income> findIncomesByMonthAndYear(int month, int year, int userId) {
        return repository.findByMonthAndYear(month, year, userId);
    }
    @Transactional
    public Result<Income> updateIncome(Income income) {
        Result<Income> result = validate(income);
        if (!result.isSuccess()) {
            return result;
        }

        if (income.getIncomeId() <= 0) {
            result.addMessage("incomeId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        Income existingIncome = repository.findById(income.getIncomeId());
        if (existingIncome == null) {
            String msg = String.format("incomeId: %s, not found", income.getIncomeId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        BigDecimal amountDifference = income.getAmount().subtract(existingIncome.getAmount());

        User user = userRepository.findById(income.getUserId());
        if (user == null) {
            result.addMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }
        user.setBank(user.getBank().add(amountDifference));
        userRepository.updateUser(user);

        boolean updated = repository.updateIncome(income);
        if (!updated) {
            result.addMessage("Failed to update income", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(income);
        return result;
    }

    @Transactional
    public boolean deleteById(int incomeId) {
        Income income = repository.findById(incomeId);
        if (income == null) {
            return false;
        }
        User user = userRepository.findById(income.getUserId());
        user.setBank(user.getBank().subtract(income.getAmount()));
        userRepository.updateUser(user);

        return repository.deleteIncome(incomeId);
    }

    private Result<Income> validate(Income income) {
        Result<Income> result = new Result<>();

        if (income == null) {
            result.addMessage("Income cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(income.getName())) {
            result.addMessage("Name cannot be blank.", ResultType.INVALID);
        }

        if (income.getAmount() != null && income.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            result.addMessage("Amount balance cannot be negative.", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(income.getDescription())) {
            result.addMessage("Description cannot be blank.", ResultType.INVALID);
        }

        if (income.getDate() == null) {
            result.addMessage("Date cannot be null", ResultType.INVALID);
        }

        if (!result.getMessages().isEmpty()) {
            result.setType(ResultType.INVALID);
        }

        return result;
    }
}

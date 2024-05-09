package learn.gig_economy.domain;

import learn.gig_economy.data.ExpenseRepository;
import learn.gig_economy.data.UserRepository;
import learn.gig_economy.models.Expense;
import learn.gig_economy.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }
    @Transactional
    public Result<Expense> addExpense(Expense expense) {
        Result<Expense> result = validate(expense);
        if (!result.isSuccess()) {
            return result;
        }
        if (expense.getExpenseId() != 0) {
            result.addMessage("expenseId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        User user = userRepository.findById(expense.getUserId());
        if (user == null) {
            result.addMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }

        BigDecimal newBankAmount = user.getBank().subtract(expense.getAmount());
        if (newBankAmount.compareTo(BigDecimal.ZERO) < 0) {
            result.addMessage("Insufficient funds for this expense", ResultType.INVALID);
            return result;
        }
        user.setBank(newBankAmount);
        userRepository.updateUser(user);

        expense = repository.addExpense(expense);
        if (expense == null) {
            result.addMessage("Failed to add expense.", ResultType.INVALID);
            return result;
        }
        result.setPayload(expense);
        return result;
    }

    public Expense findById(int expenseId) { return repository.findById(expenseId);}

    public List<Expense> findAll() {
        return repository.findAll();
    }

    public List<Expense> findAllByUserId(int userId) {
        return repository.findAllByUserId(userId);
    }
    public List<Expense> findExpensesByYear(int year, int userId) {
        return repository.findByYear(year, userId);
    }

    public List<Expense> findExpensesByMonthAndYear(int month, int year, int userId) {
        return repository.findByMonthAndYear(month, year, userId);
    }
    @Transactional
    public Result<Expense> updateExpense(Expense expense) {
        Result<Expense> validation = validate(expense);
        if (!validation.isSuccess()) {
            return validation;
        }
        if (expense.getExpenseId() <= 0) {
            validation.addMessage("expenseId must be set for `update` operation", ResultType.INVALID);
            return validation;
        }

        Expense existingExpense = repository.findById(expense.getExpenseId());
        if (existingExpense == null) {
            validation.addMessage("Expense not found", ResultType.NOT_FOUND);
            return validation;
        }

        BigDecimal amountDifference = expense.getAmount().subtract(existingExpense.getAmount());

        User user = userRepository.findById(expense.getUserId());
        if (user == null) {
            validation.addMessage("User not found", ResultType.NOT_FOUND);
            return validation;
        }

        BigDecimal newBankAmount = user.getBank().subtract(amountDifference);
        if (newBankAmount.compareTo(BigDecimal.ZERO) < 0) {
            validation.addMessage("Insufficient funds to adjust the expense", ResultType.INVALID);
            return validation;
        }
        user.setBank(newBankAmount);
        userRepository.updateUser(user);

        boolean updated = repository.updateExpense(expense);
        if (!updated) {
            validation.addMessage("Expense not found or update failed", ResultType.NOT_FOUND);
            return validation;
        }

        validation.setPayload(expense);
        return validation;
    }

    @Transactional
    public boolean deleteById(int expenseId) {
        Expense expense = repository.findById(expenseId);
        if (expense == null) {
            return false;
        }
        User user = userRepository.findById(expense.getUserId());
        user.setBank(user.getBank().add(expense.getAmount()));
        userRepository.updateUser(user);

        return repository.deleteExpense(expenseId);
    }

    private Result<Expense> validate(Expense expense) {
        Result<Expense> result = new Result<>();

        if (expense == null) {
            result.addMessage("Expense cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(expense.getName())) {
            result.addMessage("Name cannot be blank.", ResultType.INVALID);
        }

        if (expense.getAmount() != null && expense.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            result.addMessage("Amount balance cannot be negative.", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(expense.getDescription())) {
            result.addMessage("Description cannot be blank.", ResultType.INVALID);
        }

        if (expense.getDate() == null) {
            result.addMessage("Date cannot be null", ResultType.INVALID);
        }

        if (!result.getMessages().isEmpty()) {
            result.setType(ResultType.INVALID);
        }

        return result;
    }

}

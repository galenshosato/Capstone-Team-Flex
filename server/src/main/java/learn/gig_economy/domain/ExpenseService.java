package learn.gig_economy.domain;

import learn.gig_economy.data.ExpenseRepository;
import learn.gig_economy.models.Expense;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Result<Expense> addExpense(Expense expense) {
        Result<Expense> result = validate(expense);
        if (!result.isSuccess()) {
            return result;
        }
        if (expense.getExpenseId() != 0) {
            result.addMessage("expenseId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        expense = repository.addExpense(expense);

        result.setPayload(expense);
        return result;
    }

    public Expense findById(int expenseId) { return repository.findById(expenseId);}

    public List<Expense> findAll() {
        return repository.findAll();
    }

    public Result<Expense> updateExpense(Expense expense) {
        Result<Expense> validation = validate(expense);
        if (!validation.isSuccess()) {
            return validation;
        }
        if (expense.getExpenseId() == 0) {
            validation.addMessage("expenseId must be set for `update` operation", ResultType.INVALID);
            return validation;
        }
        boolean updated = repository.updateExpense(expense);
        if (!updated) {
            validation.addMessage("Expense not found or update failed", ResultType.NOT_FOUND);
            return validation;
        }
        validation.setPayload(expense);
        return validation;
    }

    public boolean deleteById(int expenseId) {
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

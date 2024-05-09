package learn.gig_economy.domain;

import learn.gig_economy.data.IncomeRepository;
import learn.gig_economy.models.Income;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository repository;

    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    public Result<Income> addIncome(Income income) {
        Result<Income> result = validate(income);
        if (!result.isSuccess()) {
            return result;
        }
        if (income.getIncomeId() != 0) {
            result.addMessage("incomeId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

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

    public Result<Income> updateIncome(Income income) {
        Result<Income> result = validate(income);
        if (!result.isSuccess()) {
            return result;
        }

        if (income.getIncomeId() <= 0) {
            result.addMessage("incomeId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.updateIncome(income)) {
            String msg = String.format("incomeId: %s, not found", income.getIncomeId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        result.setPayload(income);
        return result;
    }

    public boolean deleteById(int incomeId) {return repository.deleteIncome(incomeId);}

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
//Change if ok to be blank
        if (Validations.isNullOrBlank(income.getDescription())) {
            result.addMessage("Description cannot be blank.", ResultType.INVALID);
        }
//add if we don't allow date before or after today:  && income.getDate().isAfter(LocalDate.now())
//        if (income.getDate() == null || income.getDate().isBefore(LocalDate.now())) {
//            result.addMessage("Date cannot be in the past.", ResultType.INVALID);
//        }
        if (income.getDate() == null) {
            result.addMessage("Date cannot be null", ResultType.INVALID);
        }

        if (!result.getMessages().isEmpty()) {
            result.setType(ResultType.INVALID);
        }

        return result;
    }
}

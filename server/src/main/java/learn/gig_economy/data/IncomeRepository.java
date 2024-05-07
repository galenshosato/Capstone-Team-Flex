package learn.gig_economy.data;

import learn.gig_economy.models.Income;

import java.util.List;

public interface IncomeRepository {


    Income addIncome(Income income);

    List<Income> findAll();

    boolean updateIncome(Income income);

    boolean deleteIncome(int incomeId);
}

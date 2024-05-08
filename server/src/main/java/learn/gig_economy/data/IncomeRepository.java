package learn.gig_economy.data;

import learn.gig_economy.models.Income;

import java.util.List;

public interface IncomeRepository {


    Income addIncome(Income income);

    Income findById(int incomeId);

    List<Income> findAll();

    List<Income> findAllByUserId(int userId);

    List<Income> findByYear(int year);

    List<Income> findByMonthAndYear(int month, int year);

    boolean updateIncome(Income income);

    boolean deleteIncome(int incomeId);
}

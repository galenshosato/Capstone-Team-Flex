package learn.gig_economy.data;

import learn.gig_economy.models.Expense;

import java.util.List;

public interface ExpenseRepository {


    Expense addExpense(Expense expense);

    Expense findById(int expenseId);

    List<Expense> findAll();

    List<Expense> findAllByUserId(int userId);

    List<Expense> findByYear(int year, int userId);

    List<Expense> findByMonthAndYear(int month, int year, int userId);

    boolean updateExpense(Expense expense);

    boolean deleteExpense(int expenseId);
}

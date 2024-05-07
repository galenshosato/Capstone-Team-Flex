package learn.gig_economy.data;

import learn.gig_economy.models.Expense;

import java.util.List;

public interface ExpenseRepository {


    Expense addExpense(Expense expense);

    List<Expense> findAll();

    boolean updateExpense(Expense expense);

    boolean deleteExpense(int expenseId);
}

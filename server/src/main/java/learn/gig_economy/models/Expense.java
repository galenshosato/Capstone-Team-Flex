package learn.gig_economy.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense {
    private int expenseId;
    private String name;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private int userId;
    private int goalId; // Optional, can be null

    public Expense() {}

    public Expense(int expenseId, String name, BigDecimal amount, String description, LocalDate date, int userId, int goalId) {
        this.expenseId = expenseId;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
        this.goalId = goalId;
    }

    public int getExpenseId() { return expenseId; }
    public void setExpenseId(int expenseId) { this.expenseId = expenseId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getGoalId() { return goalId; }
    public void setGoalId(int goalId) { this.goalId = goalId; }

}

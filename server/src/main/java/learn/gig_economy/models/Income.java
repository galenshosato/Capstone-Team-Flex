package learn.gig_economy.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Income {
    private int incomeId;
    private String name;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private int userId;

    public Income() {}

    public Income(int incomeId, String name, BigDecimal amount, String description, LocalDate date, int userId) {
        this.incomeId = incomeId;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    public int getIncomeId() { return incomeId; }
    public void setIncomeId(int incomeId) { this.incomeId = incomeId; }

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

}

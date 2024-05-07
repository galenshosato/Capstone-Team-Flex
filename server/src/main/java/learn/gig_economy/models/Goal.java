package learn.gig_economy.models;

import java.math.BigDecimal;

public class Goal {
    private int goalId;
    private String description;
    private BigDecimal percentage;
    private int userId;

    public Goal() {}

    public Goal(int goalId, String description, BigDecimal percentage, int userId) {
        this.goalId = goalId;
        this.description = description;
        this.percentage = percentage;
        this.userId = userId;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

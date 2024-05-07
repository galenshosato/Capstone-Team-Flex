package learn.gig_economy.models;

import java.math.BigDecimal;

public class User {
    private int userId;
    private String name;
    private String email;
    private BigDecimal bank;

    // Constructors, getters, and setters
    public User() {}

    public User(int userId, String name, String email, BigDecimal bank) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.bank = bank;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public BigDecimal getBank() { return bank; }
    public void setBank(BigDecimal bank) { this.bank = bank; }
}

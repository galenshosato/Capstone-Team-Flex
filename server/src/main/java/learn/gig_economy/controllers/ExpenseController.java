package learn.gig_economy.controllers;

import learn.gig_economy.domain.ExpenseService;
import learn.gig_economy.domain.Result;
import learn.gig_economy.models.Expense;
import learn.gig_economy.models.Income;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/{userId}/get/{month}/{year}")
    public List<Expense> findExpenseItemsByMonthAndYear(@PathVariable int userId, @PathVariable int month, @PathVariable int year) {
        return expenseService.findExpenseByMonthAndYear(userId, month, year);
    }


    @GetMapping("/{userId}/get/{year}")
    public List<Expense> findExpenseItemsByYear(@PathVariable int userId, @PathVariable int year) {
        return expenseService.findExpenseByYear(userId, year);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<Expense> findExpenseById(@PathVariable int expenseId) {
        Expense expense = expenseService.findById(expenseId);
        if (expense == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<Object> createExpense(@RequestBody Expense expense) {
        Result<Expense> result = expenseService.addExpense(expense);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<Object> updateExpense(@PathVariable int expenseId, @RequestBody Expense expense) {
        if (expenseId != expense.getExpenseId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Expense> result = expenseService.updateExpense(expense);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpenseById(@PathVariable int expenseId) {
        if (expenseService.deleteById(expenseId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

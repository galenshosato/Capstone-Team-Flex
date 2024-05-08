package learn.gig_economy.controllers;

import learn.gig_economy.domain.IncomeService;
import learn.gig_economy.domain.Result;
import learn.gig_economy.models.Income;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/income")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/{userId}/get/{month}/{year}")
    public List<Income> getIncomeItemsForMonthAndYear(@PathVariable int userId, @PathVariable int month, @PathVariable int year) {
        return incomeService.findIncomeByMonthAndYear(userId, month, year);
    }


    @GetMapping("/{userId}/get/{year}")
    public List<Income> getIncomeItems(@PathVariable int userId, @PathVariable int year) {
        return incomeService.findIncomeByYear(userId, year);
    }

    @GetMapping("/{incomeId}")
    public ResponseEntity<Income> findIncomeById(@PathVariable int incomeId) {
        Income income = incomeService.findById(incomeId);
        if (income == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(income);
    }

    @PostMapping
    public ResponseEntity<Object> addIncome(@RequestBody Income income) {
        Result<Income> result = incomeService.addIncome(income);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<Object> updateIncome(@PathVariable int incomeId, @RequestBody Income income) {
        if (incomeId != income.getIncomeId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Income> result = incomeService.updateIncome(income);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteIncomeById(@PathVariable int incomeId) {
        if (incomeService.deleteById(incomeId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

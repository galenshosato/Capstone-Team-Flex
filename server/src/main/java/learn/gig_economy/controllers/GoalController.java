package learn.gig_economy.controllers;

import learn.gig_economy.domain.GoalService;
import learn.gig_economy.domain.Result;
import learn.gig_economy.models.Goal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/goal")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public List<Goal> findAllGoals() {return goalService.findAll();}

    @GetMapping("/{goalId}")
    public ResponseEntity<Goal> findGoalById(@PathVariable int goalId) {
        Goal goal = goalService.findById(goalId);

        if (goal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(goal);
    }

    @PostMapping
    public ResponseEntity<Object> addGoal(@RequestBody Goal goal) {
        Result<Goal> result = goalService.addGoal(goal);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{goalId}")
    public ResponseEntity<Object> updateGoal(@PathVariable int goalId, @RequestBody Goal goal) {
        if (goalId != goal.getGoalId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Goal> result = goalService.updateGoal(goal);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoalById(@PathVariable int goalId) {
        if (goalService.deleteById(goalId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

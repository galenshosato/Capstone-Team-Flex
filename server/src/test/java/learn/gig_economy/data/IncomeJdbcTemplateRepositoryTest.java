package learn.gig_economy.data;

import learn.gig_economy.models.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class IncomeJdbcTemplateRepositoryTest {

    @Autowired
    IncomeJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldAddValidIncome() {
        Income income = new Income();
        income.setName("Consulting Fee");
        income.setAmount(new BigDecimal("750.00"));
        income.setDescription("Consulting services provided");
        income.setDate(LocalDate.now());
        income.setUserId(1);
        Income savedIncome = repository.addIncome(income);
        assertNotNull(savedIncome);
        assertTrue(savedIncome.getIncomeId() > 0);
    }

    @Test
    void shouldFindAllIncomes() {
        List<Income> incomes = repository.findAll();
        assertFalse(incomes.isEmpty());
    }

    @Test
    void shouldUpdateIncome() {
        List<Income> incomes = repository.findAll();
        assertTrue(!incomes.isEmpty());
        Income income = incomes.get(0);
        income.setAmount(new BigDecimal("550.00"));
        boolean updated = repository.updateIncome(income);
        assertTrue(updated);
    }

    @Test
    void shouldDeleteIncome() {
        List<Income> incomes = repository.findAll();
        assertTrue(!incomes.isEmpty());
        boolean deleted = repository.deleteIncome(incomes.get(0).getIncomeId());
        assertTrue(deleted);
    }

    @Test
    void shouldNotDeleteNonexistentIncome() {
        boolean deleted = repository.deleteIncome(9999);
        assertFalse(deleted);
    }

    @Test
    void shouldFindIncomeById() {
        int incomeId = 2;
        Income foundIncome = repository.findById(incomeId);
        assertNotNull(foundIncome);
        assertEquals(incomeId, foundIncome.getIncomeId());
    }

    @Test
    void shouldFindIncomesByYear() {
        List<Income> results = repository.findByYear(2024, 1);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());

    }

    @Test
    void shouldNotFindIncomesByYear() {
        List<Income> results = repository.findByYear(2029, 1);
        assertTrue(results.isEmpty());
        assertEquals(0, results.size());
    }

    @Test
    void shouldFindIncomesByMonthAndYear() {

        List<Income> results = repository.findByMonthAndYear(4, 2023, 3);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void shouldNotFindIncomesByMonthAndYear() {

        List<Income> results = repository.findByMonthAndYear(4, 2027, 7);
        assertTrue(results.isEmpty());
        assertEquals(0, results.size());
    }

}
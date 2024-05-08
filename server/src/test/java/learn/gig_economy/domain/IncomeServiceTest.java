package learn.gig_economy.domain;

import learn.gig_economy.data.IncomeRepository;
import learn.gig_economy.models.Income;
import learn.gig_economy.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class IncomeServiceTest {

    @Autowired
    IncomeService service;

    @MockBean
    IncomeRepository repository;



    @Test
    void shouldAddValidIncoming() {
        LocalDate date = LocalDate.of(2024, 6, 3);
        Income income = new Income(0, "John", new BigDecimal("100.00"),"description", date, 0);
        when(repository.addIncome(any(Income.class))).thenReturn(income);

        Result<Income> result = service.addIncome(income);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("description", result.getPayload().getDescription());
    }

    @Test
    void shouldAddValidIncome() {
       Income expected = makeIncome();
       Income arg = makeIncome();
       arg.setIncomeId(0);

        when(repository.addIncome(arg)).thenReturn(expected);
        Result<Income> result = service.addIncome(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());

    }

    @Test
    void shouldUpdateValidIncome() {
        Income originalIncome = new Income(1, "Freelance Work", new BigDecimal("200.00"), "Updated description", LocalDate.now(), 1);
        when(repository.updateIncome(any(Income.class))).thenReturn(true);
        when(repository.findById(1)).thenReturn(originalIncome);

        Result<Income> result = service.updateIncome(originalIncome);

        assertTrue(result.isSuccess());
        assertEquals("Updated description", result.getPayload().getDescription());
    }

    @Test
    void shouldNotUpdateNonexistentIncome() {
        Income income = new Income(999, "Nonexistent", new BigDecimal("100.00"), "Does not exist", LocalDate.now(), 1);
        when(repository.updateIncome(any(Income.class))).thenReturn(false);

        Result<Income> result = service.updateIncome(income);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("incomeId: 999, not found"));
    }

    @Test
    void shouldFindIncomeById() {
        Income income = new Income(1, "Cold calling", new BigDecimal("3000.00"), "Monthly salary", LocalDate.now(), 1);
        when(repository.findById(1)).thenReturn(income);

        Income result = service.findById(1);

        assertEquals(1, result.getIncomeId());
        assertNotNull(result);
        assertEquals("Monthly salary", result.getDescription());
    }

    @Test
    void shouldNotFindIncomeById() {
        when(repository.findById(anyInt())).thenReturn(null);

        Income result = service.findById(999);

        assertNull(result);
    }

    @Test
    void shouldDeleteIncome() {
        when(repository.deleteIncome(1)).thenReturn(true);

        boolean result = service.deleteById(1);

        assertTrue(result);
    }

    @Test
    void shouldNotDeleteNonexistentIncome() {
        when(repository.deleteIncome(anyInt())).thenReturn(false);

        boolean result = service.deleteById(999);

        assertFalse(result);
    }

    Income makeIncome() {
        Income income = new Income();
        income.setIncomeId(1);
        income.setName("tax return");
        income.setAmount(new BigDecimal("130.00"));
        income.setDescription("This is a description");
        income.setDate(LocalDate.of(2024, 5, 3));
        income.setUserId(0);
        return income;
    }

}
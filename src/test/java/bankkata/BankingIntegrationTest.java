package bankkata;

import bankkata.application.service.AccountApplicationService;
import bankkata.domain.model.AccountId;
import bankkata.domain.repository.AccountRepository;
import bankkata.domain.service.AccountService;
import bankkata.domain.service.StatementService;
import bankkata.infrastructure.clock.TestClock;
import bankkata.infrastructure.formatter.TextStatementFormatter;
import bankkata.infrastructure.repository.InMemoryAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the entire banking application.
 * Tests all layers working together.
 */
class BankingIntegrationTest {

    private final String accountId = "test123";
    private TestClock clock;
    private AccountApplicationService applicationService;

    @BeforeEach
    void setUp() {
        // Initialize components
        clock = new TestClock(LocalDate.of(2025, 5, 19), LocalTime.of(10, 0));
        AccountRepository accountRepository = new InMemoryAccountRepository();
        StatementService statementService = new TextStatementFormatter();
        AccountService accountService = new AccountService(accountRepository);
        applicationService = new AccountApplicationService(accountService, statementService);

        // Create test account
        accountRepository.save(new bankkata.domain.model.Account(new AccountId(accountId), clock));
    }

    @Test
    void shouldPerformBankingOperationsAndGenerateStatement() {
        // Given - Account is created in setUp()

        // When - Deposit, withdraw and get statement
        applicationService.deposit(accountId, 1000.0);

        // Change date for the next operation
        clock.setFixedDate(LocalDate.of(2025, 5, 20));
        clock.setFixedTime(LocalTime.of(14, 30));
        applicationService.deposit(accountId, 500.0);

        // Change date for the next operation
        clock.setFixedDate(LocalDate.of(2025, 5, 21));
        clock.setFixedTime(LocalTime.of(16, 15));
        applicationService.withdraw(accountId, 300.0);

        // Then - Verify results
        assertEquals(1200.0, applicationService.getBalance(accountId), 0.001);

        String statement = applicationService.getStatement(accountId).formattedStatement();

        // Verify statement content
        assertTrue(statement.contains("WITHDRAWAL"));
        assertTrue(statement.contains("21/05/2025"));
        assertTrue(statement.contains("300.00"));
        assertTrue(statement.contains("1200.00"));

        assertTrue(statement.contains("DEPOSIT"));
        assertTrue(statement.contains("20/05/2025"));
        assertTrue(statement.contains("500.00"));
        assertTrue(statement.contains("1500.00"));

        assertTrue(statement.contains("19/05/2025"));
        assertTrue(statement.contains("1000.00"));
    }

    @Test
    void shouldHandleExceptionsCorrectly() {
        // Given
        applicationService.deposit(accountId, 100.0);

        // Then - Verify exception is thrown for invalid operations
        Exception withdrawalException = assertThrows(Exception.class,
                () -> applicationService.withdraw(accountId, 200.0));
        assertTrue(withdrawalException.getMessage().contains("Failed to withdraw") ||
                withdrawalException.getMessage().contains("Insufficient funds"));

        Exception negativeDepositException = assertThrows(Exception.class,
                () -> applicationService.deposit(accountId, -50.0));
        assertTrue(negativeDepositException.getMessage().contains("Failed to deposit") ||
                negativeDepositException.getMessage().contains("positive"));
    }

    @Test
    void shouldHandleNonExistentAccount() {
        // When trying to operate on a non-existent account
        Exception exception = assertThrows(Exception.class, () -> applicationService.deposit("non-existent", 100.0));

        // Then
        assertTrue(exception.getMessage().contains("Failed to deposit") ||
                exception.getMessage().contains("not found"));
    }
}

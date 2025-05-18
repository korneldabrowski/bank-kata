package bankkata;

import bankkata.application.service.AccountApplicationService;
import bankkata.domain.model.AccountId;
import bankkata.domain.repository.AccountRepository;
import bankkata.domain.service.AccountService;
import bankkata.domain.service.StatementService;
import bankkata.infrastructure.clock.TestClock;
import bankkata.infrastructure.formatter.TextStatementFormatter;
import bankkata.infrastructure.repository.InMemoryAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Acceptance test that demonstrates all user stories working together.
 */
class AcceptanceTest {

    @Test
    void shouldHandleAccountOperationsAndPrintStatement() {
        // Given a client with a bank account
        TestClock clock = new TestClock(LocalDate.of(2025, 4, 10), LocalTime.of(10, 0));
        AccountRepository accountRepository = new InMemoryAccountRepository();
        StatementService statementService = new TextStatementFormatter();
        AccountService accountService = new AccountService(accountRepository);
        AccountApplicationService applicationService = new AccountApplicationService(accountService, statementService);

        // Create an account
        String accountId = "account123";
        accountRepository.save(new bankkata.domain.model.Account(new AccountId(accountId), clock));

        // When the client makes a deposit (US1)
        applicationService.deposit(accountId, 1000.0);

        // And then makes another deposit on a different day
        clock.setFixedDate(LocalDate.of(2025, 4, 13));
        clock.setFixedTime(LocalTime.of(14, 30));
        applicationService.deposit(accountId, 2000.0);

        // And then makes a withdrawal (US2)
        clock.setFixedDate(LocalDate.of(2025, 4, 14));
        clock.setFixedTime(LocalTime.of(16, 15));
        applicationService.withdraw(accountId, 500.0);

        // Then the statement should contain all operations (US3)
        String statement = applicationService.getStatement(accountId).formattedStatement();

        // Verify content without exact formatting
        Assertions.assertTrue(statement.contains("WITHDRAWAL"));
        Assertions.assertTrue(statement.contains("14/04/2025"));
        Assertions.assertTrue(statement.contains("500.00"));
        Assertions.assertTrue(statement.contains("2500.00"));

        Assertions.assertTrue(statement.contains("DEPOSIT"));
        Assertions.assertTrue(statement.contains("13/04/2025"));
        Assertions.assertTrue(statement.contains("2000.00"));
        Assertions.assertTrue(statement.contains("3000.00"));

        Assertions.assertTrue(statement.contains("10/04/2025"));
        Assertions.assertTrue(statement.contains("1000.00"));
    }

    @Test
    void shouldTrackBalanceCorrectly() {
        // Given a client with a bank account
        TestClock clock = new TestClock(LocalDate.of(2025, 4, 10), LocalTime.of(10, 0));
        AccountRepository accountRepository = new InMemoryAccountRepository();
        StatementService statementService = new TextStatementFormatter();
        AccountService accountService = new AccountService(accountRepository);
        AccountApplicationService applicationService = new AccountApplicationService(accountService, statementService);

        // Create an account
        String accountId = "account123";
        accountRepository.save(new bankkata.domain.model.Account(new AccountId(accountId), clock));

        applicationService.deposit(accountId, 1000.0);
        applicationService.deposit(accountId, 500.0);
        applicationService.withdraw(accountId, 300.0);
        applicationService.deposit(accountId, 200.0);
        applicationService.withdraw(accountId, 100.0);

        // Balance should be 1000 + 500 - 300 + 200 - 100 = 1300
        double expectedBalance = 1300.0;
        Assertions.assertEquals(expectedBalance, applicationService.getBalance(accountId));
    }

    @Test
    void shouldRejectNegativeDeposits() {
        // Given a client with a bank account
        TestClock clock = new TestClock(LocalDate.now(), LocalTime.now());
        AccountRepository accountRepository = new InMemoryAccountRepository();
        StatementService statementService = new TextStatementFormatter();
        AccountService accountService = new AccountService(accountRepository);
        AccountApplicationService applicationService = new AccountApplicationService(accountService, statementService);

        // Create an account
        String accountId = "account123";
        accountRepository.save(new bankkata.domain.model.Account(new AccountId(accountId), clock));

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            applicationService.deposit(accountId, -100.0);
        });

        Assertions.assertTrue(exception.getMessage().contains("positive") ||
                exception.getMessage().contains("Failed to deposit"));
    }

    @Test
    void shouldRejectWithdrawalExceedingBalance() {
        // Given a client with a bank account
        TestClock clock = new TestClock(LocalDate.now(), LocalTime.now());
        AccountRepository accountRepository = new InMemoryAccountRepository();
        StatementService statementService = new TextStatementFormatter();
        AccountService accountService = new AccountService(accountRepository);
        AccountApplicationService applicationService = new AccountApplicationService(accountService, statementService);

        // Create an account
        String accountId = "account123";
        accountRepository.save(new bankkata.domain.model.Account(new AccountId(accountId), clock));

        applicationService.deposit(accountId, 500.0);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            applicationService.withdraw(accountId, 600.0);
        });

        Assertions.assertTrue(exception.getMessage().contains("Insufficient") ||
                exception.getMessage().contains("Failed to withdraw"));
    }
}
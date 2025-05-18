package bankkata;

import bankkata.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

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
        Account account = new Account(clock);
        StatementPrinter printer = new StatementPrinter();

        // When the client makes a deposit (US1)
        account.deposit(1000.0);

        // And then makes another deposit on a different day
        clock.setFixedDate(LocalDate.of(2025, 4, 13));
        clock.setFixedTime(LocalTime.of(14, 30));
        account.deposit(2000.0);

        // And then makes a withdrawal (US2)
        clock.setFixedDate(LocalDate.of(2025, 4, 14));
        clock.setFixedTime(LocalTime.of(16, 15));
        account.withdraw(500.0);

        // Then the statement should contain all operations
        String statement = printer.print(account.getOperations());

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
        TestClock clock = new TestClock(LocalDate.of(2025, 4, 10), LocalTime.of(10, 0));
        Account account = new Account(clock);

        account.deposit(1000.0);
        account.deposit(500.0);
        account.withdraw(300.0);
        account.deposit(200.0);
        account.withdraw(100.0);

        // Balance should be 1000 + 500 - 300 + 200 - 100 = 1300
        double expectedBalance = 1300.0;
        Assertions.assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    void shouldRejectNegativeDeposits() {
        TestClock clock = new TestClock(LocalDate.now(), LocalTime.now());
        Account account = new Account(clock);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-100.0);
        });

        Assertions.assertTrue(exception.getMessage().contains("positive"));
    }

    @Test
    void shouldRejectWithdrawalExceedingBalance() {
        TestClock clock = new TestClock(LocalDate.now(), LocalTime.now());
        Account account = new Account(clock);

        account.deposit(500.0);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(600.0);
        });

        Assertions.assertTrue(exception.getMessage().contains("Insufficient"));
    }
}
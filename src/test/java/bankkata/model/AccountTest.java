package bankkata.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.LocalTime;

public class AccountTest {

    private Account account;
    private TestClock clock;

    @BeforeEach
    void setUp() {
        clock = new TestClock(LocalDate.of(2025, 4, 1));
        account = new Account(clock);
    }

    @Test
    void shouldStartWithZeroBalance() {
        Assertions.assertEquals(0.0, account.getBalance());
    }

    @Test
    void shouldIncreaseBalanceWhenDepositing() {
        account.deposit(100.0);
        Assertions.assertEquals(100.0, account.getBalance());

        account.deposit(50.0);
        Assertions.assertEquals(150.0, account.getBalance());
    }

    @Test
    void shouldDecreaseBalanceWhenWithdrawing() {
        account.deposit(100.0);
        account.withdraw(30.0);
        Assertions.assertEquals(70.0, account.getBalance());

        account.withdraw(20.0);
        Assertions.assertEquals(50.0, account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingWithInsufficientFunds() {
        account.deposit(50.0);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(100.0));
        Assertions.assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDepositingNegativeAmount() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(-100.0));
        Assertions.assertEquals("Deposit amount must be positive", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingNegativeAmount() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(-100.0));
        Assertions.assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    void shouldKeepHistoryOfOperations() {
        // Set specific times for better testing
        LocalTime morningTime = LocalTime.of(10, 0);
        clock.setFixedTime(morningTime);

        account.deposit(1000.0);

        // Change date and time
        LocalDate day2 = LocalDate.of(2025, 4, 2);
        LocalTime noonTime = LocalTime.of(12, 30);
        clock.setFixedDate(day2);
        clock.setFixedTime(noonTime);
        account.withdraw(100.0);

        // Change date and time again
        LocalDate day3 = LocalDate.of(2025, 4, 3);
        LocalTime eveningTime = LocalTime.of(18, 15);
        clock.setFixedDate(day3);
        clock.setFixedTime(eveningTime);
        account.deposit(50.0);

        var operations = account.getOperations();
        Assertions.assertEquals(3, operations.size());

        // Check first operation
        Assertions.assertEquals(OperationType.DEPOSIT, operations.get(0).getType());
        Assertions.assertEquals(1000.0, operations.get(0).getAmount());
        Assertions.assertEquals(LocalDate.of(2025, 4, 1), operations.get(0).getDate());
        Assertions.assertEquals(morningTime, operations.get(0).getTimestamp().toLocalTime());
        Assertions.assertEquals(1000.0, operations.get(0).getBalanceAfterOperation());

        // Check second operation
        Assertions.assertEquals(OperationType.WITHDRAWAL, operations.get(1).getType());
        Assertions.assertEquals(100.0, operations.get(1).getAmount());
        Assertions.assertEquals(day2, operations.get(1).getDate());
        Assertions.assertEquals(noonTime, operations.get(1).getTimestamp().toLocalTime());
        Assertions.assertEquals(900.0, operations.get(1).getBalanceAfterOperation());

        // Check third operation
        Assertions.assertEquals(OperationType.DEPOSIT, operations.get(2).getType());
        Assertions.assertEquals(50.0, operations.get(2).getAmount());
        Assertions.assertEquals(day3, operations.get(2).getDate());
        Assertions.assertEquals(eveningTime, operations.get(2).getTimestamp().toLocalTime());
        Assertions.assertEquals(950.0, operations.get(2).getBalanceAfterOperation());
    }
}
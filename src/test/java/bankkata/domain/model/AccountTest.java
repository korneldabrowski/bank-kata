package bankkata.domain.model;

import bankkata.infrastructure.clock.TestClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the Account entity.
 */
class AccountTest {

    private final AccountId accountId = new AccountId("test123");
    private TestClock clock;
    private Account account;

    @BeforeEach
    void setUp() {
        clock = new TestClock(LocalDate.of(2025, 5, 19), LocalTime.of(10, 0));
        account = new Account(accountId, clock);
    }

    @Test
    void shouldHaveZeroBalanceWhenCreated() {
        assertEquals(Money.ZERO.getAmount(), account.getBalance().getAmount());
    }

    @Test
    void shouldIncreaseBalanceWhenDeposit() {
        // When
        account.deposit(new Money(100.0));

        // Then
        assertEquals(new Money(100.0).getAmount(), account.getBalance().getAmount());
    }

    @Test
    void shouldDecreaseBalanceWhenWithdraw() {
        // Given
        account.deposit(new Money(100.0));

        // When
        account.withdraw(new Money(40.0));

        // Then
        assertEquals(new Money(60.0).getAmount(), account.getBalance().getAmount());
    }

    @Test
    void shouldRecordDepositOperation() {
        // When
        account.deposit(new Money(100.0));

        // Then
        List<Operation> operations = account.getOperations();
        assertEquals(1, operations.size());
        assertEquals(OperationType.DEPOSIT, operations.get(0).getType());
        assertEquals(new Money(100.0).getAmount(), operations.get(0).getAmount().getAmount());
        assertEquals(new Money(100.0).getAmount(), operations.get(0).getBalanceAfterOperation().getAmount());
    }

    @Test
    void shouldRecordWithdrawalOperation() {
        // Given
        account.deposit(new Money(100.0));

        // When
        account.withdraw(new Money(60.0));

        // Then
        List<Operation> operations = account.getOperations();
        assertEquals(2, operations.size());
        assertEquals(OperationType.WITHDRAWAL, operations.get(1).getType());
        assertEquals(new Money(60.0).getAmount(), operations.get(1).getAmount().getAmount());
        assertEquals(new Money(40.0).getAmount(), operations.get(1).getBalanceAfterOperation().getAmount());
    }

    @Test
    void shouldThrowExceptionWhenDepositingNegativeAmount() {
        // Given
        Money negativeAmount = new Money(-50.0);

        // Then
        assertThrows(IllegalArgumentException.class, () -> account.deposit(negativeAmount));
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingNegativeAmount() {
        // Given
        Money negativeAmount = new Money(-50.0);

        // Then
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(negativeAmount));
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingMoreThanBalance() {
        // Given
        account.deposit(new Money(100.0));
        Money excessiveAmount = new Money(150.0);

        // Then
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(excessiveAmount));
    }
}

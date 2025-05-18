package bankkata.domain.model;

import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a bank account that can perform deposit and withdrawal operations.
 * This is an entity - it has identity and mutable state.
 */
public class Account {
    @Getter
    private final AccountId id;

    @Getter
    private Money balance;

    private final List<Operation> operations;
    private final Clock clock;

    public Account(AccountId id, Clock clock) {
        this.id = id;
        this.clock = clock;
        this.balance = Money.ZERO;
        this.operations = new ArrayList<>();
    }

    /**
     * Deposits money into the account.
     * 
     * @param amount the amount to deposit
     * @throws IllegalArgumentException if amount is negative or zero
     */
    public void deposit(Money amount) {
        validatePositiveAmount(amount);

        balance = balance.add(amount);
        recordOperation(OperationType.DEPOSIT, amount);
    }

    /**
     * Withdraws money from the account.
     * 
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if amount is negative or zero, or if there
     *                                  are insufficient funds
     */
    public void withdraw(Money amount) {
        validatePositiveAmount(amount);
        validateSufficientFunds(amount);

        balance = balance.subtract(amount);
        recordOperation(OperationType.WITHDRAWAL, amount);
    }

    /**
     * Returns an unmodifiable list of all operations performed on the account.
     * 
     * @return list of operations
     */
    public List<Operation> getOperations() {
        return Collections.unmodifiableList(operations);
    }

    private void validatePositiveAmount(Money amount) {
        if (amount.isLessThanOrEqualToZero()) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private void validateSufficientFunds(Money amount) {
        if (amount.isGreaterThan(balance)) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    private void recordOperation(OperationType type, Money amount) {
        operations.add(new Operation(type, clock.now(), amount, balance));
    }
}

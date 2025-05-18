package bankkata.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a bank account that can perform deposit and withdrawal operations.
 */
public class Account {

    @Getter
    private double balance;
    private final List<Operation> operations = new ArrayList<>();
    private final Clock clock;

    public Account() {
        this(new DefaultClock());
    }

    public Account(Clock clock) {
        this.clock = clock;
    }

    /**
     * Deposits money into the account.
     * 
     * @param amount the amount to deposit
     * @throws IllegalArgumentException if amount is negative or zero
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        balance += amount;
        operations.add(new Operation(OperationType.DEPOSIT, clock.now(), amount, balance));
    }

    /**
     * Withdraws money from the account.
     * 
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if amount is negative or zero, or if there
     *                                  are insufficient funds
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        balance -= amount;
        operations.add(new Operation(OperationType.WITHDRAWAL, clock.now(), amount, balance));
    }

    /**
     * Returns an unmodifiable list of all operations performed on the account.
     * 
     * @return list of operations
     */
    public List<Operation> getOperations() {
        return Collections.unmodifiableList(operations);
    }
}
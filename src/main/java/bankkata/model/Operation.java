package bankkata.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Represents an operation performed on an account.
 */
@Getter
@RequiredArgsConstructor
public class Operation {

    private final OperationType type;
    private final ZonedDateTime timestamp;
    private final double amount;
    private final double balanceAfterOperation;

    /**
     * Constructor that accepts only the date (for backward compatibility).
     * 
     * @param type                  the operation type
     * @param date                  the date of the operation
     * @param amount                the amount of the operation
     * @param balanceAfterOperation the balance after the operation
     */
    public Operation(OperationType type, LocalDate date, double amount, double balanceAfterOperation) {
        this(type, date.atStartOfDay(java.time.ZoneId.systemDefault()), amount, balanceAfterOperation);
    }

    /**
     * Returns the date part of the timestamp.
     * 
     * @return the date of the operation
     */
    public LocalDate getDate() {
        return timestamp.toLocalDate();
    }
}
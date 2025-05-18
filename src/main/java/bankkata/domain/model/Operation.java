package bankkata.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Represents an operation performed on an account.
 * This is a value object - immutable and identified by its attributes.
 */
@Getter
@RequiredArgsConstructor
public class Operation {
    private final OperationType type;
    private final ZonedDateTime timestamp;
    private final Money amount;
    private final Money balanceAfterOperation;

    /**
     * Returns the date part of the timestamp.
     *
     * @return the date of the operation
     */
    public LocalDate getDate() {
        return timestamp.toLocalDate();
    }
}

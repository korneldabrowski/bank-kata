package bankkata.infrastructure.formatter;

import bankkata.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bankkata.infrastructure.clock.TestClock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the TextStatementFormatter.
 */
public class TextStatementFormatterTest {

    private TextStatementFormatter formatter;
    private TestClock clock;

    @BeforeEach
    void setUp() {
        formatter = new TextStatementFormatter();
        clock = new TestClock(LocalDate.of(2025, 5, 19), LocalTime.of(10, 0));
    }

    @Test
    void shouldGenerateEmptyStatementWhenNoOperations() {
        // When
        String statement = formatter.generateStatement(List.of());

        // Then
        assertTrue(statement.contains("OPERATION"));
        assertTrue(statement.contains("DATE"));
        assertTrue(statement.contains("TIME"));
        assertTrue(statement.contains("AMOUNT"));
        assertTrue(statement.contains("BALANCE"));
    }

    @Test
    void shouldGenerateStatementWithOperationsInReverseChronologicalOrder() {
        // Given
        Operation deposit1 = new Operation(
                OperationType.DEPOSIT,
                clock.now(),
                new Money(100.0),
                new Money(100.0));

        clock.setFixedDate(LocalDate.of(2025, 5, 20));
        Operation deposit2 = new Operation(
                OperationType.DEPOSIT,
                clock.now(),
                new Money(50.0),
                new Money(150.0));

        clock.setFixedDate(LocalDate.of(2025, 5, 21));
        Operation withdrawal = new Operation(
                OperationType.WITHDRAWAL,
                clock.now(),
                new Money(30.0),
                new Money(120.0));

        List<Operation> operations = Arrays.asList(deposit1, deposit2, withdrawal);

        // When
        String statement = formatter.generateStatement(operations);

        // Then
        String[] lines = statement.split(System.lineSeparator());
        assertTrue(lines.length >= 5); // Header, divider, and 3 operations

        // The operations should be in reverse order (newest first)
        assertTrue(lines[2].contains("WITHDRAWAL"));
        assertTrue(lines[2].contains("21/05/2025"));

        assertTrue(lines[3].contains("DEPOSIT"));
        assertTrue(lines[3].contains("20/05/2025"));

        assertTrue(lines[4].contains("DEPOSIT"));
        assertTrue(lines[4].contains("19/05/2025"));
    }
}

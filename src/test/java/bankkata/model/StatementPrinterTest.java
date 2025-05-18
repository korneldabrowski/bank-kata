package bankkata.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class StatementPrinterTest {

        private StatementPrinter statementPrinter;

        @BeforeEach
        void setUp() {
                statementPrinter = new StatementPrinter();
        }

        @Test
        void shouldFormatSingleDeposit() {
                LocalDate date = LocalDate.of(2023, 5, 15);
                LocalTime time = LocalTime.of(9, 30);
                Operation deposit = new Operation(
                                OperationType.DEPOSIT,
                                ZonedDateTime.of(date, time, ZoneId.systemDefault()),
                                500.0,
                                500.0);

                List<Operation> operations = Collections.singletonList(deposit);
                String statement = statementPrinter.print(operations);

                // Verify headers are present
                Assertions.assertTrue(statement.contains("OPERATION"));
                Assertions.assertTrue(statement.contains("DATE"));
                Assertions.assertTrue(statement.contains("TIME"));
                Assertions.assertTrue(statement.contains("AMOUNT"));
                Assertions.assertTrue(statement.contains("BALANCE"));

                // Verify operation data is present
                Assertions.assertTrue(statement.contains("DEPOSIT"));
                Assertions.assertTrue(statement.contains("15/05/2023"));
                Assertions.assertTrue(statement.contains("500.00"));
        }

        @Test
        void shouldFormatSingleWithdrawal() {
                LocalDate date = LocalDate.of(2023, 6, 20);
                LocalTime time = LocalTime.of(14, 45);
                Operation withdrawal = new Operation(
                                OperationType.WITHDRAWAL,
                                ZonedDateTime.of(date, time, ZoneId.systemDefault()),
                                200.0,
                                800.0);

                List<Operation> operations = Collections.singletonList(withdrawal);
                String statement = statementPrinter.print(operations);

                // Verify operation type is present
                Assertions.assertTrue(statement.contains("WITHDRAWAL"));

                // Verify date is present
                Assertions.assertTrue(statement.contains("20/06/2023"));

                // Verify amount and balance are present
                Assertions.assertTrue(statement.contains("200.00"));
                Assertions.assertTrue(statement.contains("800.00"));
        }

        @Test
        void shouldReverseChronologicalOrderOfOperations() {
                // Setup three operations with different dates
                Operation oldest = new Operation(
                                OperationType.DEPOSIT,
                                ZonedDateTime.of(2023, 1, 1, 10, 0, 0, 0, ZoneId.systemDefault()),
                                100.0,
                                100.0);

                Operation middle = new Operation(
                                OperationType.DEPOSIT,
                                ZonedDateTime.of(2023, 1, 2, 11, 0, 0, 0, ZoneId.systemDefault()),
                                200.0,
                                300.0);

                Operation newest = new Operation(
                                OperationType.WITHDRAWAL,
                                ZonedDateTime.of(2023, 1, 3, 12, 0, 0, 0, ZoneId.systemDefault()),
                                50.0,
                                250.0);

                // Add in chronological order
                List<Operation> operations = Arrays.asList(oldest, middle, newest);
                String statement = statementPrinter.print(operations);

                // Verify they appear in reverse chronological order
                Assertions.assertTrue(statement.indexOf("03/01/2023") < statement.indexOf("02/01/2023"));
                Assertions.assertTrue(statement.indexOf("02/01/2023") < statement.indexOf("01/01/2023"));
        }
}
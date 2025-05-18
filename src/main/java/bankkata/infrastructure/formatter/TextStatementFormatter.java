package bankkata.infrastructure.formatter;

import bankkata.domain.model.Operation;
import bankkata.domain.service.StatementService;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Implementation of the StatementService that formats statements as text.
 */
public class TextStatementFormatter implements StatementService {

    private static final String HEADER = "| OPERATION | DATE | TIME | AMOUNT | BALANCE |";
    private static final String DIVIDER = "|-----------|------------|------------|------------|------------|";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public String generateStatement(List<Operation> operations) {
        StringBuilder statement = new StringBuilder();
        statement.append(HEADER).append(System.lineSeparator());
        statement.append(DIVIDER).append(System.lineSeparator());

        // Display operations in reverse chronological order (newest first)
        for (int i = operations.size() - 1; i >= 0; i--) {
            Operation operation = operations.get(i);
            statement.append(formatOperation(operation)).append(System.lineSeparator());
        }

        return statement.toString();
    }

    private String formatOperation(Operation operation) {
        String type = operation.getType().toString();
        String date = operation.getTimestamp().format(DATE_FORMATTER);
        String time = operation.getTimestamp().format(TIME_FORMATTER);
        String amount = String.format("%.2f", operation.getAmount().getAmount());
        String balance = String.format("%.2f", operation.getBalanceAfterOperation().getAmount());

        return String.format("| %9s | %10s | %10s | %10s | %10s |",
                type, date, time, amount, balance);
    }
}

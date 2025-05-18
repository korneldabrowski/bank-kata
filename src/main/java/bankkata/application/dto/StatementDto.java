package bankkata.application.dto;

import java.util.List;

/**
 * Data Transfer Object for account statements.
 */
public record StatementDto(String formattedStatement, List<OperationDto> operations) {
}

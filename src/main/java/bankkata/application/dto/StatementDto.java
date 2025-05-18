package bankkata.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for account statements.
 */
public record StatementDto(String formattedStatement, List<OperationDto> operations) {
}

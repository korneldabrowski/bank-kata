package bankkata.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Data Transfer Object for operations.
 */
public record OperationDto(String type, ZonedDateTime timestamp, double amount, double balance) {
}

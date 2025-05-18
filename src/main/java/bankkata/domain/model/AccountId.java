package bankkata.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a unique identifier for an account.
 * Using a dedicated ID type instead of primitive types helps with type safety.
 */
public record AccountId(String value) {
}

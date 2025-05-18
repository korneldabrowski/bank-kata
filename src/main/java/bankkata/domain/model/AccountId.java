package bankkata.domain.model;


/**
 * Represents a unique identifier for an account.
 * Using a dedicated ID type instead of primitive types helps with type safety.
 */
public record AccountId(String value) {
}

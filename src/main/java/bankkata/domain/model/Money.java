package bankkata.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value object representing money in the application.
 * Using BigDecimal ensures precise calculations for financial operations.
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Money {
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal amount;

    public Money(double amount) {
        this(BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP));
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public boolean isGreaterThan(Money other) {
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isLessThanOrEqualToZero() {
        return this.amount.compareTo(BigDecimal.ZERO) <= 0;
    }

    @Override
    public String toString() {
        return amount.toPlainString();
    }
}

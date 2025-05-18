package bankkata.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for working with money and financial calculations.
 */
public final class MoneyUtil {

    private MoneyUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Formats a monetary amount with two decimal places.
     *
     * @param amount the amount to format
     * @return a formatted string representation
     */
    public static String format(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP).toString();
    }

    /**
     * Formats a monetary amount with two decimal places.
     *
     * @param amount the amount to format
     * @return a formatted string representation
     */
    public static String format(double amount) {
        return format(BigDecimal.valueOf(amount));
    }

    /**
     * Rounds a monetary amount to two decimal places.
     *
     * @param amount the amount to round
     * @return the rounded amount
     */
    public static BigDecimal round(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Converts a double to a BigDecimal with proper scaling.
     *
     * @param amount the amount to convert
     * @return a BigDecimal representation
     */
    public static BigDecimal toBigDecimal(double amount) {
        return round(BigDecimal.valueOf(amount));
    }
}

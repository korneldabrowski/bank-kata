package bankkata.util;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MoneyUtilTest {

    @Test
    void formatBigDecimal_ShouldFormatWithTwoDecimalPlaces() {
        // Given
        BigDecimal amount = new BigDecimal("123.456");

        // When
        String result = MoneyUtil.format(amount);

        // Then
        assertEquals("123.46", result);
    }

    @Test
    void formatBigDecimal_ShouldHandleZeroAmount() {
        // Given
        BigDecimal amount = BigDecimal.ZERO;

        // When
        String result = MoneyUtil.format(amount);

        // Then
        assertEquals("0.00", result);
    }

    @Test
    void formatBigDecimal_ShouldHandleNegativeAmount() {
        // Given
        BigDecimal amount = new BigDecimal("-50.257");

        // When
        String result = MoneyUtil.format(amount);

        // Then
        assertEquals("-50.26", result);
    }

    @Test
    void formatDouble_ShouldFormatWithTwoDecimalPlaces() {
        // Given
        double amount = 123.456;

        // When
        String result = MoneyUtil.format(amount);

        // Then
        assertEquals("123.46", result);
    }

    @Test
    void formatDouble_ShouldHandleZeroAmount() {
        // Given
        double amount = 0.0;

        // When
        String result = MoneyUtil.format(amount);

        // Then
        assertEquals("0.00", result);
    }

    @Test
    void formatDouble_ShouldHandleNegativeAmount() {
        // Given
        double amount = -50.257;

        // When
        String result = MoneyUtil.format(amount);

        // Then
        assertEquals("-50.26", result);
    }

    @Test
    void round_ShouldRoundToTwoDecimalPlaces() {
        // Given
        BigDecimal amount = new BigDecimal("123.456");

        // When
        BigDecimal result = MoneyUtil.round(amount);

        // Then
        assertEquals(new BigDecimal("123.46"), result);
    }

    @Test
    void round_ShouldHandleZeroAmount() {
        // Given
        BigDecimal amount = BigDecimal.ZERO;

        // When
        BigDecimal result = MoneyUtil.round(amount);

        // Then
        assertEquals(new BigDecimal("0.00"), result);
    }

    @Test
    void round_ShouldHandleNegativeAmount() {
        // Given
        BigDecimal amount = new BigDecimal("-50.257");

        // When
        BigDecimal result = MoneyUtil.round(amount);

        // Then
        assertEquals(new BigDecimal("-50.26"), result);
    }

    @Test
    void toBigDecimal_ShouldConvertAndRoundToTwoDecimalPlaces() {
        // Given
        double amount = 123.456;

        // When
        BigDecimal result = MoneyUtil.toBigDecimal(amount);

        // Then
        assertEquals(new BigDecimal("123.46"), result);
    }

    @Test
    void toBigDecimal_ShouldHandleZeroAmount() {
        // Given
        double amount = 0.0;

        // When
        BigDecimal result = MoneyUtil.toBigDecimal(amount);

        // Then
        assertEquals(new BigDecimal("0.00"), result);
    }

    @Test
    void toBigDecimal_ShouldHandleNegativeAmount() {
        // Given
        double amount = -50.257;

        // When
        BigDecimal result = MoneyUtil.toBigDecimal(amount);

        // Then
        assertEquals(new BigDecimal("-50.26"), result);
    }
}
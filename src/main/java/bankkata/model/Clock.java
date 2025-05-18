package bankkata.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * A clock interface that provides the current date and time.
 * This enables easier testing by allowing date and time mocking.
 * This interface is designed to align with Java 8's java.time.Clock.
 */
public interface Clock {

    /**
     * Returns the current instant.
     * 
     * @return the current instant
     */
    Instant instant();

    /**
     * Returns the current zone ID.
     * 
     * @return the zone ID
     */
    ZoneId getZone();

    /**
     * Returns today's date in the system timezone.
     * 
     * @return the current date
     */
    default LocalDate today() {
        return ZonedDateTime.ofInstant(instant(), getZone()).toLocalDate();
    }

    /**
     * Returns the current date and time as a ZonedDateTime.
     * 
     * @return the current date and time with timezone information
     */
    default ZonedDateTime now() {
        return ZonedDateTime.ofInstant(instant(), getZone());
    }
}
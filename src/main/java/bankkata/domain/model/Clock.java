package bankkata.domain.model;

import java.time.ZonedDateTime;

/**
 * A clock interface that provides the current date and time.
 * This enables easier testing by allowing date and time mocking.
 */
public interface Clock {
    /**
     * Returns the current date and time with timezone information.
     * 
     * @return the current date and time
     */
    ZonedDateTime now();
}

package bankkata.infrastructure.clock;

import bankkata.domain.model.Clock;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Default implementation of the Clock interface that provides the current
 * system time.
 */
public class DefaultClock implements Clock {

    @Override
    public ZonedDateTime now() {
        return ZonedDateTime.now();
    }
}

package bankkata.model;

import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;

/**
 * Default implementation of the Clock interface that provides the system's
 * current date and time by delegating to Java's standard Clock.
 */
@NoArgsConstructor
public class DefaultClock implements Clock {

    private final java.time.Clock systemClock = java.time.Clock.systemDefaultZone();

    @Override
    public Instant instant() {
        return systemClock.instant();
    }

    @Override
    public ZoneId getZone() {
        return systemClock.getZone();
    }
}
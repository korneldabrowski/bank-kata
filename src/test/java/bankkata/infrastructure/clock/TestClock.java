package bankkata.infrastructure.clock;

import bankkata.domain.model.Clock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * A mock clock implementation for testing purposes.
 * Allows setting fixed dates and times for predictable test outcomes.
 */
public class TestClock implements Clock {
    private LocalDate fixedDate;
    private LocalTime fixedTime;
    private ZoneId zoneId;

    public TestClock(LocalDate fixedDate, LocalTime fixedTime) {
        this.fixedDate = fixedDate;
        this.fixedTime = fixedTime;
        this.zoneId = ZoneId.systemDefault();
    }

    public TestClock() {
        this(LocalDate.now(), LocalTime.now());
    }

    @Override
    public ZonedDateTime now() {
        return ZonedDateTime.of(fixedDate, fixedTime, zoneId);
    }

    public void setFixedDate(LocalDate fixedDate) {
        this.fixedDate = fixedDate;
    }

    public void setFixedTime(LocalTime fixedTime) {
        this.fixedTime = fixedTime;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }
}

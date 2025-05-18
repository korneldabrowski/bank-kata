package bankkata.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * A test implementation of the Clock interface that returns fixed date and
 * time. This is useful for tests that need deterministic time values.
 */
public class TestClock implements Clock {

    private ZonedDateTime fixedDateTime;

    public TestClock(LocalDate fixedDate) {
        this.fixedDateTime = ZonedDateTime.of(fixedDate, LocalTime.MIDNIGHT, ZoneId.systemDefault());
    }

    public TestClock(LocalDate fixedDate, LocalTime fixedTime) {
        this.fixedDateTime = ZonedDateTime.of(fixedDate, fixedTime, ZoneId.systemDefault());
    }

    public TestClock(LocalDate fixedDate, LocalTime fixedTime, ZoneId zoneId) {
        this.fixedDateTime = ZonedDateTime.of(fixedDate, fixedTime, zoneId);
    }

    public TestClock(ZonedDateTime fixedDateTime) {
        this.fixedDateTime = fixedDateTime;
    }

    public void setFixedDate(LocalDate fixedDate) {
        this.fixedDateTime = ZonedDateTime.of(fixedDate,
                this.fixedDateTime.toLocalTime(),
                this.fixedDateTime.getZone());
    }

    public void setFixedTime(LocalTime fixedTime) {
        this.fixedDateTime = ZonedDateTime.of(this.fixedDateTime.toLocalDate(),
                fixedTime,
                this.fixedDateTime.getZone());
    }

    public void setZoneId(ZoneId zoneId) {
        this.fixedDateTime = this.fixedDateTime.withZoneSameLocal(zoneId);
    }

    public void setFixedDateTime(ZonedDateTime fixedDateTime) {
        this.fixedDateTime = fixedDateTime;
    }

    @Override
    public Instant instant() {
        return fixedDateTime.toInstant();
    }

    @Override
    public ZoneId getZone() {
        return fixedDateTime.getZone();
    }
}
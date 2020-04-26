package com.myhospital.appointment.util;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public final class WorkingHourAdjuster implements TemporalAdjuster {
    private final int hour;

    public WorkingHourAdjuster(int hour) {
        this.hour = hour;
    }

    public static WorkingHourAdjuster startHour(int hour) {
        return new WorkingHourAdjuster(hour);
    }

    public static WorkingHourAdjuster endHour(int hour) {
        return new WorkingHourAdjuster(hour);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        if (!(temporal instanceof LocalDateTime)) {
            throw new DateTimeException("Cannot adjust using: "
                    + temporal.getClass().getName());
        }

        return LocalDateTime.of(((LocalDateTime) temporal).toLocalDate(),
                LocalTime.of(hour, 0, 0, 0));
    }
}

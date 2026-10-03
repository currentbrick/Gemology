package com.currentbrick.gemology.entity.gem;

import java.time.LocalDate;
import java.time.MonthDay;

public record GemAvailability(MonthDay start, MonthDay end) {

    public boolean isAvailable(LocalDate date) {
        MonthDay current = MonthDay.from(date);

        if (!start.isAfter(end)) {
            return !current.isBefore(start)
                    && !current.isAfter(end);
        }

        return !current.isBefore(start)
                || !current.isAfter(end);
    }
}
package com.codecool.brewery;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

class HelperBrewer extends Brewer{
    Set<DayOfWeek> workDays;

    HelperBrewer(String name, int yearsOfExperience, Set<DayOfWeek> workDays) {
        super(name, yearsOfExperience);
        this.workDays = workDays;
    }

    @Override
    boolean isAvailableOn(DayOfWeek day) {
        return workDays.contains(day);
    }

    @Override
    boolean isAvailableOn(LocalDate date) {
        return workDays.contains(date.getDayOfWeek());
    }
}

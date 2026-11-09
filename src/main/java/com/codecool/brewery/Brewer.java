package com.codecool.brewery;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Represents a brewer working at the brewery.
 * You need to extend this class to support different types of brewers.
 */
abstract class Brewer {
    String name;
    int yearsOfExperience;

    Brewer(String name, int yearsOfExperience) {}

    // TODO: Add method to check if brewer is available on a specific day
    // TODO: Consider how different types of brewers have different schedules
    // TODO: Consider isAvailableOn may accept parameters for day checks
    abstract boolean isAvailableOn(DayOfWeek day);
    abstract boolean isAvailableOn(LocalDate date);

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getName() {
        return name;
    }
}

package com.codecool.brewery;

import java.time.DayOfWeek;
import java.time.LocalDate;

class MasterBrewer extends Brewer{

    MasterBrewer(String name, int yearsOfExperience) {
        super(name, yearsOfExperience);
    }

    @Override
    boolean isAvailableOn(DayOfWeek day) {
        return true;
    }

    @Override
    boolean isAvailableOn(LocalDate date) {
        return true;
    }
}

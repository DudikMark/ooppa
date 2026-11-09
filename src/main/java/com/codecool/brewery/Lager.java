package com.codecool.brewery;

import java.time.LocalDate;

class Lager extends Beer {

    Lager(String name, double alcoholContent, LocalDate productionDate, double storageTemperature) {
        super(name, alcoholContent, productionDate, 21, storageTemperature);
    }

    @Override
    boolean canBeStoredAt(double temp) {
        return temp < 5;
    }
}

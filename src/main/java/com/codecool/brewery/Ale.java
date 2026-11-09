package com.codecool.brewery;

import java.time.LocalDate;

class Ale extends Beer {
    Ale(String name, double alcoholContent, LocalDate productionDate, double storageTemperature) {
        super(name, alcoholContent, productionDate, 14, storageTemperature);
    }

    @Override
    boolean canBeStoredAt(double temp) {
        return temp >= 20.0 && temp <= 25.0;
    }
}

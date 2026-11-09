package com.codecool.brewery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a beer produced by the brewery.
 * This is a basic implementation - you need to extend this to support different beer types.
 */
abstract class Beer {
    String name;
    double alcoholContent;
    LocalDate productionDate;
    int fermentationDays;
    double storageTemperature;

    Beer(String name, double alcoholContent, LocalDate productionDate, int fermentationDays, double storageTemperature) {
        if (alcoholContent < 0.0 || alcoholContent > 100.0) {
            throw new IllegalArgumentException("Alcohol content must be 0-100");
        }
        if (!canBeStoredAt(storageTemperature)) {
            throw new IllegalArgumentException("Invalid storageTemperature");
        }
        this.name = name;
        this.alcoholContent = alcoholContent;
        this.productionDate = productionDate;
        this.fermentationDays = fermentationDays;
        this.storageTemperature = storageTemperature;
    }

    // TODO: Add method to calculate days until beer is ready for sale
    // TODO: Consider how to handle different fermentation times for different beer types
    long getDaysUntilReady() {
        LocalDate readyDate = productionDate.plusDays(fermentationDays);
        LocalDate today = LocalDate.now();
        long daysRemaining = ChronoUnit.DAYS.between(today, readyDate);
        return Math.max(0, daysRemaining);
    }

    // TODO: Add method to get storage temperature requirement
    // TODO: Consider canBeStoredAt method may accept parameters for temperature checks
    abstract boolean canBeStoredAt(double temp);

    // TODO: Add method to get fermentation days
    int getFermentationDays() {
        return fermentationDays;
    }

    String getName() {
        return name;
    }

    double getAlcoholContent() {
        return alcoholContent;
    }

    LocalDate getProductionDate() {
        return productionDate;
    }
}

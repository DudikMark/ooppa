package com.codecool.brewery;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a brewery that manages beer production and staff.
 * Many methods are incomplete and need to be implemented.
 */
class Brewery {
    String name;
    List<Brewer> brewers;
    List<Beer> beerStock;

    Brewery(String name) {
        this.name = name;
        this.brewers = new ArrayList<>();
        this.beerStock = new ArrayList<>();
    }

    void addBrewer(Brewer brewer) {
        // TODO: Implement proper null checking and validation
        // TODO: add brewer to brewers list
        if (brewer == null) {
            throw new IllegalArgumentException("Brewer cannot be null");
        }
        if (brewers.contains(brewer)) {
            throw new IllegalStateException("Brewer already exists in the brewery");
        }
        if (brewer instanceof MasterBrewer) {
            boolean hasMaster = brewers.stream().anyMatch(b -> b instanceof MasterBrewer);
            if(hasMaster) {
                throw new IllegalStateException("Brewery already has MasterBrewer");
            }
        }
        brewers.add(brewer);
    }

    void addBeerToStock(Beer beer) {
        // TODO: Implement proper null checking and validation
        // TODO: add beer to stock
        if (beer == null) {
            throw new IllegalArgumentException("beer cannot be null");
        }
        beerStock.add(beer);
    }

    /**
     * Retrieves a specific quantity of beer from stock.
     * TODO: Implement this method with proper error handling
     * - What happens if the beer doesn't exist?
     * - What happens if there's not enough quantity?
     * - Should this modify the stock?
     */
    void retrieveBeerFromStock(String beerName, int quantity) {
        // TODO: Implement
        if (beerName == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid beer name or quantity");
        }
        List<Beer> matchingBeers = beerStock.stream()
                .filter(b -> b.getName().equalsIgnoreCase(beerName))
                .toList();

        if(matchingBeers.isEmpty()) {
            throw new NoSuchElementException("Beer not found: " + beerName);
        }

        if(matchingBeers.size() < quantity) {
            throw new IllegalStateException("Requested: " + quantity + "stock of: " + beerName + ", but only found: " + matchingBeers.size());
        }

        for (int i = 0; i < quantity; i++) {
            beerStock.remove(matchingBeers.get(i));
        }
    }

    /**
     * Calculates the total production capacity across specific days.
     * Each day is independent - beer can be produced in a single day.
     * TODO: Implement this method
     * - Find the master brewer
     * - Count available helpers on each specified day
     * - Return the sum of helper counts across all days
     */
    public int calculateProductionCapacity(List<String> dayNames) {
        // TODO: Implement
        List<DayOfWeek> days = dayNames.stream().map(this::parseDayOfWeek).toList();
        boolean hasMasterBrewer = brewers.stream()
                .anyMatch(brewer -> brewer instanceof MasterBrewer);

        if (!hasMasterBrewer) {
            return 0;
        }

        int totalCapacity = 0;
        for (DayOfWeek day : days) {
            totalCapacity += countAvailableHelpers(day);
        }

        return totalCapacity;
    }

    /**
     * Finds beers that are past their prime (older than 180 days).
     * TODO: Implement this method using Stream API
     * - Filter beers by age
     * - Return collection of beer names
     */
    public List<String> findMaturedBeers() {
        // TODO: Implement
        LocalDate today = LocalDate.now();
        return beerStock.stream()
                .filter(beer -> ChronoUnit.DAYS.between(beer.getProductionDate(), today) > 180)
                .map(Beer::getName)
                .toList();
    }

    /**
     * Finds the optimal days for production within a given range.
     * Returns days with maximum helper availability.
     */
    public List<String> findOptimalProductionDays(String startDayName, String endDayName) {
        DayOfWeek startDay = parseDayOfWeek(startDayName);
        DayOfWeek endDay = parseDayOfWeek(endDayName);

        // Get all days in range
        List<DayOfWeek> daysInRange = getDaysInRange(startDay, endDay);

        if (daysInRange.isEmpty()) {
            return Collections.emptyList();
        }

        // Map each day to its helper count
        Map<DayOfWeek, Integer> helperCountByDay = daysInRange.stream()
                .collect(Collectors.toMap(
                        day -> day,
                        this::countAvailableHelpers
                ));

        // Find maximum helper count
        int maxHelpers = helperCountByDay.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        // Return days with maximum helper count
        return helperCountByDay.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHelpers)
                .map(Map.Entry::getKey)
                .sorted()
                .map(DayOfWeek::toString)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to parse day names.
     */
    private DayOfWeek parseDayOfWeek(String dayName) {
        try {
            return DayOfWeek.valueOf(dayName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day name: " + dayName, e);
        }
    }

    /**
     * Helper method to get days in a range.
     */
    private List<DayOfWeek> getDaysInRange(DayOfWeek start, DayOfWeek end) {
        List<DayOfWeek> days = new ArrayList<>();
        DayOfWeek current = start;

        // Handle wrap-around week
        while (true) {
            days.add(current);
            if (current == end) {
                break;
            }
            current = current.plus(1);
            // Prevent infinite loop
            if (days.size() > 7) {
                break;
            }
        }

        return days;
    }

    /**
     * Counts available helpers on a specific day.
     */
    // TODO: Implement countAvailableHelpers method
    private int countAvailableHelpers(DayOfWeek day) {
        return (int) brewers.stream()
                .filter(brewer -> brewer instanceof HelperBrewer)
                .filter(helper -> helper.isAvailableOn(day))
                .count();
    }
}

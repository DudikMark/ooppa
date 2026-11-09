package com.codecool.brewery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BreweryTest {

    private Brewery brewery;
    private Brewer masterBrewer;
    private Brewer helper1;
    private Brewer helper2;
    private Brewer helper3;

    @BeforeEach
    void setUp() {
        brewery = new Brewery("Test Brewery");

        // Set up brewers
        // TODO: master brewer: "Master John", 15 years experience
        // TODO: helper brewer: "Helper Alice", 3 years experience, available Mon, Tue, Wed
        // TODO: helper brewer: "Helper Bob", 2 years experience, available Mon, Thu, Fri
        // TODO: helper brewer: "Helper Charlie", 4 years experience, available Tue, Wed, Thu
        masterBrewer = new MasterBrewer("Master John", 15);
        helper1 = new HelperBrewer("Helper Alice", 3, Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        helper2 = new HelperBrewer("Helper Bob", 2, Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        helper3 = new HelperBrewer("Helper Charlie", 4, Set.of(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));

        // TODO: add master and helpers to brewery
        brewery.addBrewer(masterBrewer);
        brewery.addBrewer(helper1);
        brewery.addBrewer(helper2);
        brewery.addBrewer(helper3);
    }

    @Test
    @DisplayName("Simple Test: Production capacity on single day equals available helpers")
    void testProductionCapacityOnSingleDay() {
        // Monday: helper1 and helper2 are available (2 helpers)
        int capacity = brewery.calculateProductionCapacity(List.of("monday"));
        assertEquals(2, capacity, "Production capacity should equal number of helpers on Monday");
    }

    @Test
    @DisplayName("Production capacity on multiple days returns sum of helpers")
    void testProductionCapacityOnMultipleDays() {
        // TODO: implement test for multiple days
        // Monday: helper1 and helper2 are available (2 helpers)
        // Tuesday: helper1 and helper3 are available (2 helpers)
        // Friday:  helper2 are available (1 helper)
        int capacity = brewery.calculateProductionCapacity(List.of("monday", "tuesday", "friday"));
        assertEquals(5, capacity, "Production capacity should equal number of helpers on Monday, Tuesday, Friday");
    }

    @Test
    @DisplayName("Production capacity throws exception on invalid day name")
    void testProductionCapacityWithInvalidDay() {
        // TODO: implement test for invalid day name
        assertThrows(IllegalArgumentException.class, () -> {
            brewery.calculateProductionCapacity(List.of("nextday"));
        });
    }

    @Test
    @DisplayName("Find expired beers returns beers older than 180 days")
    void testFindExpiredBeers() {
        // Add fresh beer
        // TODO: create and add beer: "Fresh", 5.0%, produced 100 days ago
        Beer beer1 = new Lager("Fresh", 5.0, LocalDate.now().minusDays(100), 2);
        brewery.addBeerToStock(beer1);
        // Add matured beers
        // TODO: create and add beer: "Old Ale", 6.0%, produced 200 days ago
        // TODO: create and add beer: "Old Lager", 5.5%, produced 190 days ago
        Beer beer2 = new Ale("Old Ale", 6.0, LocalDate.now().minusDays(200), 21.0);
        Beer beer3 = new Lager("Old Lager", 5.5, LocalDate.now().minusDays(190), 2.0);
        brewery.addBeerToStock(beer2);
        brewery.addBeerToStock(beer3);

        List<String> expired = brewery.findMaturedBeers();

        assertEquals(2, expired.size());
        assertTrue(expired.contains("Old Ale"));
        assertTrue(expired.contains("Old Lager"));
        assertFalse(expired.contains("Fresh"));
    }

    @Test
    @DisplayName("Find optimal production days returns days with most helpers")
    void testFindOptimalProductionDays() {
        // TODO: implement test for finding optimal production days for the given setup
        List<String> expected = List.of("WEDNESDAY", "THURSDAY");
        List<String> optimalDays = brewery.findOptimalProductionDays("WEDNESDAY", "FRIDAY");

        assertEquals(expected, optimalDays);
    }

    @Test
    @DisplayName("Beer type has correct fermentation days")
    void testBeerFermentationDays() {
        // TODO: implement test for fermentation days
        // TODO: create Lager: "Test Lager", 5.0%, today; fermentation time shall be 21 days
        // TODO: create Ale: "Test Ale", 5.5%, today; fermentation time shall be 14 days
        Beer beer1 = new Lager("Test Lager", 5.0, LocalDate.now(), 2.0);
        Beer beer2 = new Ale("Test Ale", 5.5, LocalDate.now(), 21.0);

        assertEquals(21, beer1.getFermentationDays());
        assertEquals(14, beer2.getFermentationDays());
    }

    @Test
    @DisplayName("Beer type has correct storage temperature")
    void testBeerStorageTemperature() {
        // TODO: implement test for storage temperature
        // TODO: create Lager: "Test Lager", 5.0%, today
        // TODO: create Ale: "Test Ale", 5.5%, today
        Beer beer1 = new Lager("Test Lager", 5.0, LocalDate.now(), 2.0);
        Beer beer2 = new Ale("Test Ale", 5.5, LocalDate.now(), 21.0);

        assertTrue(beer1.canBeStoredAt(2.0), "Lager storage temperature should be colder");
        assertTrue(beer2.canBeStoredAt(21.0), "Ale storage temperature should be room temperature");
    }

    @Test
    @DisplayName("Helper brewer availability is correct")
    void testHelperBrewerAvailability() {
        // TODO: implement test for helper brewer availability

        assertTrue(helper1.isAvailableOn(DayOfWeek.MONDAY), "Helper Alice should be available on Monday");
        assertTrue(helper2.isAvailableOn(DayOfWeek.MONDAY), "Helper Bob should be available on Monday");
        assertFalse(helper3.isAvailableOn(DayOfWeek.MONDAY), "Helper Charlie should not be available on Monday");
    }
}

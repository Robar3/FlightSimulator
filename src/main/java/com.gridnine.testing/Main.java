package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> arr = FlightBuilder.createFlights();
        FlightFilter flightFilter = new FlightFilter();
        printFlight(flightFilter.filterFlight(arr, true, false, false));
        printFlight(flightFilter.filterFlight(arr, false, true, false));
        printFlight(flightFilter.filterFlight(arr, false, false, true));
        printFlight(flightFilter.filterFlight(arr, true, true, true));
    }

    private static void printFlight(List<Flight> arr) {
        arr.forEach(a -> {
            System.out.println("Начало рейса");
            a.getSegments().forEach(s -> {
                System.out.println(s.getDepartureDate() + " вылет");
                System.out.println(s.getArrivalDate() + " посадка");
            });
            System.out.println("Завершение рейса");
        });
        System.out.println("------------------------------------------------");
        System.out.println("\n\n");
    }
}

package com.gridnine.testing;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.gridnine.testing.FlightBuilder.createFlight;


class FlightFilterTest {


    FlightFilter flightFilter = new FlightFilter();
    FlightBuilder flightBuilder = new FlightBuilder();
    List<Flight> arrFlights =flightBuilder.createFlights();
    LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);

     List<Flight> filterDepartureBeforeCurDateList() {
        return Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight that departs before it arrives
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }
     List<Flight> filterDepartureBeforeArrivalList() {

        return Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }
    List<Flight> filterMore2HoursOnLandList() {

        return Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight that departs before it arrives
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)));
    }
    List<Flight> allFilterListList() {

        return Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)));
    }
    @Test
    public void filterDepartureBeforeCurDateTest() {
        assertTwoFlightArrays(flightFilter.filterFlight(arrFlights, true,false,false),filterDepartureBeforeCurDateList());

    }
    @Test
    public void filterDepartureBeforeArrivalTest() {
        assertTwoFlightArrays(flightFilter.filterFlight(arrFlights, false,true,false),filterDepartureBeforeArrivalList());
    }
    @Test
    public void filterMore2HoursOnLandTest() {
        assertTwoFlightArrays(flightFilter.filterFlight(arrFlights, false,false,true),filterMore2HoursOnLandList());
    }
    @Test
    public void allFilterTest() {
        assertTwoFlightArrays(flightFilter.filterFlight(arrFlights, true,true,true),allFilterListList());
    }

    public void assertTwoFlightArrays(List<Flight> actualList,List<Flight> expectedList){
        for (int i = 0; i < expectedList.size(); i++) {
            List<Segment> actualSegmentList = actualList.get(i).getSegments();
            List<Segment> expectedSegmentList = actualList.get(i).getSegments();
            for (int j = 0; j < actualSegmentList.size(); j++) {
                Assertions.assertTrue(actualSegmentList.get(j).getDepartureDate().isEqual(expectedSegmentList.get(j).getDepartureDate()));
                Assertions.assertTrue(actualSegmentList.get(j).getArrivalDate().isEqual(expectedSegmentList.get(j).getArrivalDate()));
            }
        }
    }
}
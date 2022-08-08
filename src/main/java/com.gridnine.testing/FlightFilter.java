package com.gridnine.testing;


import java.time.LocalDateTime;
import java.util.*;

public class FlightFilter {
   public List<Flight> filterFlight(List<Flight> allFlight,Boolean departureBeforeCurDate,
                                                   Boolean departureBeforeArrival,Boolean more2HoursOnLand ){
       LinkedList<Flight> res= new LinkedList<>(allFlight);
       allFlight.forEach(f->{
           List<Segment> segments=f.getSegments();
           for (int i = 0; i < segments.size(); i++) {
               if (departureBeforeCurDate){
                   if (segments.get(i).getDepartureDate().isBefore(LocalDateTime.now().plusDays(3).minusMinutes(1))){
                       res.remove(f);
                   }
               }
               if (departureBeforeArrival){
                   if (segments.get(i).getDepartureDate().isAfter(segments.get(i).getArrivalDate())) {
                       res.remove(f);
                   }
               }
               if (more2HoursOnLand&&i>=1){
                   if (segments.get(i-1).getArrivalDate().plusHours(2).minusSeconds(1).isBefore(segments.get(i).getDepartureDate())){
                       res.remove(f);
                   }
               }
           }
       });
       return res;
   }
}

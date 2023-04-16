package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
    private  static final double DUREE_GRATUIT = 0.5;

    public  void calculateFare(Ticket ticket, boolean discount){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

    long inHour = ticket.getInTime().getTime(); 
    long outHour = ticket.getOutTime().getTime();

    //TODO: Some tests are failing here. Need to check if this logic is correct
    double duration = outHour - inHour;
    duration = (duration/(1000*60*60));

    if (duration < DUREE_GRATUIT){ //duration less than or equal to 30 minutes
      ticket.setPrice(0);
    }
    else{

    switch (ticket.getParkingSpot().getParkingType()){
        case CAR: {
           
              if(discount==true){
                ticket.setPrice(0.95 * Fare.CAR_RATE_PER_HOUR);
              }
              else{
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
              }
          
          break;
        }
        case BIKE: {
      
            if(discount == true){
              ticket.setPrice(0.95 * Fare.BIKE_RATE_PER_HOUR);
            }
            else{
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
            }
          break;
        }
        default: throw new IllegalArgumentException("Unkown Parking Type");
    }
 }
}
  public void calculateFare(Ticket ticket){
    calculateFare(ticket, false);
  }
}
package PricingRules;

import Models.Reservation;

import java.time.temporal.ChronoUnit;

public class EarlyBooking implements PricingRule{
    @Override
    public double apply(Reservation reservation, double totalTTC) {

        double discount = 0 ;


         long daysBeforeCheckIn = ChronoUnit.DAYS.between(
                 reservation.getCreatedAt() ,
                 reservation.getCheckIn()
         );

         if(daysBeforeCheckIn >= 30){
             discount = totalTTC * 5 / 100;
             totalTTC = totalTTC - discount;
         }

         if(daysBeforeCheckIn <= 3){
             discount = totalTTC * 10/ 100;
             totalTTC = totalTTC + discount;
         }

        return totalTTC;
    }
}

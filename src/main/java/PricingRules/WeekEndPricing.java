package PricingRules;

import Models.Reservation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class WeekEndPricing implements PricingRule{
    @Override
    public double apply(Reservation reservation, double totalTTC) {

        DayOfWeek sunday = DayOfWeek.SUNDAY;
        DayOfWeek saturday = DayOfWeek.SATURDAY;

        double nightPrice = reservation.getRoom().getNightPrice();
        LocalDate currentDate = reservation.getCheckIn();
        boolean isWeekEnd = false;

        while (currentDate.isBefore(reservation.getCheckOut())) {
            DayOfWeek day = currentDate.getDayOfWeek();
            if (day == sunday || day == saturday) {
                isWeekEnd = true;
            }
            currentDate = currentDate.plusDays(1);
        }
        if(isWeekEnd){
            double fees = nightPrice * 15 / 100 ;
            totalTTC += fees;
        }
        return totalTTC;
    }
}

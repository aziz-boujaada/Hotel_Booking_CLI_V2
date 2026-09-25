package PricingRules;

import Models.Reservation;

import java.time.LocalDate;
import java.time.Month;

public class LowSeasonsPricing implements PricingRule{

    @Override
    public double apply(Reservation reservation, double totalTTC) {


        Month february = Month.FEBRUARY;
        Month january = Month.JANUARY;
        Month november = Month.NOVEMBER;

        double nightPrice = reservation.getRoom().getNightPrice();
        LocalDate currentDate = reservation.getCheckIn();
        long highSeasonNights = 0;

        while (currentDate.isBefore(reservation.getCheckOut())) {
            Month month = currentDate.getMonth();
            if (month == january || month == february || month == november) {
                highSeasonNights++;
            }
            currentDate = currentDate.plusDays(1);
        }

        double discount = nightPrice * 15 * highSeasonNights / 100;
        totalTTC = totalTTC - discount;
        return totalTTC;
    }
}

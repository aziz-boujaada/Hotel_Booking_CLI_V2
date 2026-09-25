package PricingRules;

import Models.Reservation;

import java.time.LocalDate;
import java.time.Month;

public class HighSeasionsPricing implements PricingRule {

    @Override
    public double apply(Reservation reservation, double totalTTC) {
        Month july = Month.JULY;
        Month august = Month.AUGUST;

        double nightPrice = reservation.getRoom().getNightPrice();
        LocalDate currentDate = reservation.getCheckIn();
        long highSeasonNights = 0;

        while (currentDate.isBefore(reservation.getCheckOut())) {
            Month month = currentDate.getMonth();
            if (month == july || month == august) {
                highSeasonNights++;
            }
           currentDate = currentDate.plusDays(1);
        }

        double fees = nightPrice * 30 * highSeasonNights / 100;
        totalTTC = totalTTC + fees;

        return totalTTC;
    }

}

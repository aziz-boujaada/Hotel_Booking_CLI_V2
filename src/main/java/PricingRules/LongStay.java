package PricingRules;

import Models.Reservation;

public class LongStay implements PricingRule {
    @Override
    public double apply(Reservation reservation, double totalTTC) {

        double discount = 0;


        if (reservation.getNights() >= 7 && reservation.getNights() <= 14) {

            discount = totalTTC * 10 / 100;
            totalTTC -= discount;

        } else if (reservation.getNights() > 14) {
            discount = totalTTC * 15 / 100;
            totalTTC -= discount;
        }
        return totalTTC;
    }
}

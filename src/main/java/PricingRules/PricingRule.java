package PricingRules;

import Models.Reservation;

public interface PricingRule {
    double apply(Reservation reservation , double totalTTC);
}

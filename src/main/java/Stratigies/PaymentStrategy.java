package Stratigies;

import Models.Payment;
import Models.Reservation;

public interface PaymentStrategy {
    Payment createPayment(Reservation reservation);
}

package Services;

import Enums.PaymentMethod;
import Enums.PaymentStatus;
import Models.Payment;
import Models.Reservation;

import java.time.LocalDateTime;

public class PaymentService {

    public Payment createPayment(Reservation reservation , PaymentMethod paymentMethod){
        double totalTTC = calculateTotalTTC(reservation.getTotal());
        Payment payment = new Payment(null ,reservation , totalTTC , paymentMethod , PaymentStatus.PENDING , LocalDateTime.now());

        return payment ;
    }

    public double calculateTotalTTC(double totalHt){
          double tva = totalHt * 20 / 100 ;
          return totalHt + tva;
    }
}

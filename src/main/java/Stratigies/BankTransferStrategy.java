package Stratigies;

import Enums.PaymentMethod;
import Enums.PaymentStatus;
import Models.Payment;
import Models.Reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public class BankTransferStrategy implements PaymentStrategy {
    @Override
    public Payment createPayment(Reservation reservation, double amount) {
        return new Payment("PAY-" + UUID.randomUUID(), reservation, amount,
                PaymentMethod.BANK_TRANSFER, PaymentStatus.PENDING, LocalDateTime.now());
    }
}

package Repositories;

import Models.Payment;
import Models.Reservation;

import java.sql.Connection;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Connection connection , Payment payment);
    Optional<Payment> findByReservationId(Connection connection, Reservation reservation);
}

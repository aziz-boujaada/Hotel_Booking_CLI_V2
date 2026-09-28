package Repositories.impl;

import Enums.PaymentMethod;
import Enums.PaymentStatus;
import Models.Payment;
import Models.Reservation;
import Repositories.PaymentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public class PaymentJdbc implements PaymentRepository {

    @Override
    public Optional<Payment> findByReservationId(Connection connection, Reservation reservation) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection is required");
        }
        if (reservation == null || reservation.getReservationID() == null) {
            throw new IllegalArgumentException("Reservation is required");
        }

        String sql = """
                SELECT payment_id, amount_payed, payment_method, payment_status, payment_date
                FROM payments
                WHERE reservation_id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reservation.getReservationID());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                Timestamp paymentDate = resultSet.getTimestamp("payment_date");
                return Optional.of(new Payment(
                        resultSet.getString("payment_id"),
                        reservation,
                        resultSet.getDouble("amount_payed"),
                        PaymentMethod.valueOf(resultSet.getString("payment_method")),
                        PaymentStatus.valueOf(resultSet.getString("payment_status")),
                        paymentDate.toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find payment for reservation", e);
        }
    }

    @Override
    public Payment save(Connection connection, Payment payment) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection is required");
        }
        if (payment == null || payment.getReservation() == null) {
            throw new IllegalArgumentException("Payment and reservation are required");
        }
        if (payment.getPaymentMethod() == null || payment.getPaymentStatus() == null || payment.getPaymentDate() == null) {
            throw new IllegalArgumentException("Payment method, status, and date are required");
        }
        if (payment.getPaymentId() == null || payment.getPaymentId().isBlank()) {
            payment.setPaymentId("PAY-" + UUID.randomUUID());
        }

        String sql = """
                INSERT INTO payments(payment_id, reservation_id, amount_payed, payment_method, payment_status, payment_date)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, payment.getPaymentId());
            statement.setString(2, payment.getReservation().getReservationID());
            statement.setDouble(3, payment.getAmountPayed());
            statement.setString(4, payment.getPaymentMethod().name());
            statement.setString(5, payment.getPaymentStatus().name());
            statement.setTimestamp(6, Timestamp.valueOf(payment.getPaymentDate()));
            statement.executeUpdate();
            return payment;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save payment", e);
        }
    }
}

package Repositories;

import Models.Payment;

import java.sql.Connection;

public interface PaymentRepository {
    Payment save(Connection connection , Payment payment);
}

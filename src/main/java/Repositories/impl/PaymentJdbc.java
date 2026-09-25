package Repositories.impl;

import Models.Payment;
import Repositories.PaymentRepository;

import java.sql.Connection;

public class PaymentJdbc implements PaymentRepository {

    @Override
    public Payment save(Connection connection, Payment payment) {
        return null;
    }
}

package Repositories.impl;

import Config.DatabaseConfig;
import Models.Payment;
import Models.Reservation;
import Repositories.PaymentRepository;
import Repositories.ReservationRepository;
import Repositories.ReservationTransactions;

import java.sql.Connection;
import java.sql.SQLException;

public class ReservationTransactionsJdbc implements ReservationTransactions {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final DatabaseConfig databaseConfig;

    public ReservationTransactionsJdbc(ReservationRepository reservationRepository, PaymentRepository paymentRepository, DatabaseConfig databaseConfig) {
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
        this.databaseConfig = databaseConfig;
    }


    @Override
    public void executeTransaction(Reservation reservation, Payment payment) {
        try (Connection connection = databaseConfig.getConnection();) {
            try {
               connection.setAutoCommit(false);

               reservationRepository.save(connection , reservation);
               paymentRepository.save(connection , payment);

               connection.commit();
            } catch (Exception e) {
                try {
                    connection.rollback();
                }catch (SQLException rollbackException ){
                    e.addSuppressed(rollbackException);
                }
                throw  new RuntimeException("reservation transaction failed");
            }

        } catch (Exception e) {
            throw new RuntimeException("Connection Failed", e);
        }
    }
}

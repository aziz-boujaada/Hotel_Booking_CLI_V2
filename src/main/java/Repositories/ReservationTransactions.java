package Repositories;

import Models.Payment;
import Models.Reservation;

public interface ReservationTransactions {
     void executeTransaction(Reservation reservation , Payment payment) ;


}
